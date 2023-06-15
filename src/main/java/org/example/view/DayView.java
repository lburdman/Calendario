package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DayView extends BorderPane {
    private final Label dateLabel;
    private final Button prevDayButton;
    private final Button nextDayButton;
    private final GridPane gridPane;

    public DayView() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d (EEEE), yyyy");

        dateLabel = new Label(LocalDate.now().format(dateFormatter));
        prevDayButton = new Button("<");
        nextDayButton = new Button(">");

        HBox dateNavigation = new HBox(1);
        dateNavigation.setAlignment(Pos.CENTER);
        dateNavigation.getChildren().addAll(prevDayButton, dateLabel, nextDayButton);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        makeGrid();

        this.setTop(dateNavigation);
        this.setCenter(gridPane);
    }

    public void updateGridWithEvents(List<Event> events, LocalDate currentDate) {
        clearGrid();
        double spacing = 2.0;

        // Sort the events by start time
        events.sort(Comparator.comparing(Event::getStartDateTime));

        Map<Integer, List<Event>> eventsPerHour = new HashMap<>();

        // First pass: calculate the number of overlapping events for each hour
        for (Event event : events) {
            LocalDateTime eventStart = event.getStartDateTime();
            LocalDateTime eventEnd = event.getEndDateTime();

            int startHour = eventStart.toLocalDate().isBefore(currentDate) ? 0 : eventStart.toLocalTime().getHour();
            int endHour = eventEnd.toLocalDate().isAfter(currentDate) ? 23 : eventEnd.toLocalTime().getHour();

            for (int hour = startHour; hour <= endHour; hour++) {
                eventsPerHour.computeIfAbsent(hour, k -> new ArrayList<>()).add(event);
            }
        }

        // Second pass: draw the events with the calculated widths and positions
        for (Event event : events) {
            LocalDateTime eventStart = event.getStartDateTime();
            LocalDateTime eventEnd = event.getEndDateTime();

            int startHour = eventStart.toLocalDate().isBefore(currentDate) ? 0 : eventStart.toLocalTime().getHour();
            int endHour = eventEnd.toLocalDate().isAfter(currentDate) ? 23 : eventEnd.toLocalTime().getHour();

            int maxOverlap = 1;
            for (int hour = startHour; hour <= endHour; hour++) {
                maxOverlap = Math.max(maxOverlap, eventsPerHour.get(hour).size());
            }

            double totalWidth = ((Pane) getNodeFromGridPane(gridPane, 1, startHour + 1)).getWidth() - (maxOverlap + 1) * spacing;
            double eventWidth = totalWidth / maxOverlap;

            for (int hour = startHour; hour <= endHour; hour++) {
                double positionX = eventsPerHour.get(hour).indexOf(event) * (eventWidth + 2.0);
                drawEvent(event, hour, eventWidth, positionX);
            }
        }
    }


    private void drawEvent(Event event, int hour, double eventWidth, double positionX) {
        Pane hourCell = (Pane) getNodeFromGridPane(gridPane, 1, hour + 1);

        Rectangle eventRect = new Rectangle();
        eventRect.setWidth(eventWidth);
        eventRect.setHeight(hourCell.getHeight());
        eventRect.setFill(Color.GREENYELLOW);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(eventRect);

        // Add text to the first rectangle
        if (hour == event.getStartDateTime().toLocalTime().getHour()) {
            Text eventText = new Text(event.getTitle());
            stackPane.getChildren().add(eventText);
        }
        stackPane.setLayoutX(positionX);

        hourCell.getChildren().add(stackPane);
    }



    private void clearGrid() {
        for (int hour = 0; hour < 24; hour++) {
            Pane hourCell = (Pane) getNodeFromGridPane(gridPane, 1, hour + 1);
            if (hourCell != null) {
                hourCell.getChildren().clear();
                hourCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray; -fx-pref-width: 200px;");
            }
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void makeGrid() {
        gridPane.setMinWidth(200);
        for (int hour = 0; hour < 24; hour++) {
            Label hourLabel = new Label(String.format("%02d:00", hour));
            Pane hourCell = new Pane(); // Empty cell

            hourLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            hourCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");

            hourCell.setPrefWidth(gridPane.getWidth() - 5);

            gridPane.add(hourLabel, 0, hour + 1);
            gridPane.add(hourCell, 1, hour + 1);
        }
    }

    public Button getPrevDayButton() {
        return prevDayButton;
    }

    public Button getNextDayButton() {
        return nextDayButton;
    }

    public void setDateLabel(LocalDate date) {
        dateLabel.setText(date.format(DateTimeFormatter.ofPattern("d MMMM (EEEE), yyyy")));
    }
}

