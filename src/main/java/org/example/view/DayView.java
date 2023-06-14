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
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DayView extends BorderPane {
    private Label dateLabel;
    private Button prevDayButton;
    private Button nextDayButton;
    private GridPane gridPane;
    private Button addEventButton;
    private Button addTaskButton;

    public DayView() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d (EEEE), yyyy");

        dateLabel = new Label(LocalDate.now().format(dateFormatter));
        prevDayButton = new Button("<");
        nextDayButton = new Button(">");

        HBox dateNavigation = new HBox(1);
        dateNavigation.setAlignment(Pos.CENTER);
        dateNavigation.getChildren().addAll(prevDayButton, dateLabel, nextDayButton);

        /*viewSelector = new ComboBox<>();
        viewSelector.getItems().addAll("Day view", "Week view", "Month view");
        viewSelector.setValue("Day view");

        VBox topContainer = new VBox(5);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(viewSelector, dateNavigation);
*/
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        makeGrid();

        addEventButton = new Button("Add Event");
        addTaskButton = new Button("Add Task");
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(addEventButton, addTaskButton);

        this.setTop(dateNavigation);
        this.setCenter(gridPane);
        this.setBottom(buttonsBox);
    }

    public void updateGridWithEvents(List<Event> events, LocalDate currentDate) {
        clearGrid();
        double spacing = 2.0;

        events.sort(Comparator.comparing(Event::getStartDateTime));

        Map<Integer, List<Event>> eventsPerHour = new HashMap<>();
        Map<Event, Double> eventWidths = new HashMap<>();
        Map<Event, Double> eventPositions = new HashMap<>();

        for (Event event : events) {
            LocalDate eventStart = event.getStartDateTime().toLocalDate();
            LocalDate eventEnd = event.getEndDateTime().toLocalDate();

            int startHour = event.getStartDateTime().toLocalTime().getHour();
            int endHour = event.getEndDateTime().toLocalTime().getHour();

            if (eventStart.isBefore(currentDate)) {
                startHour = 0;
            }

            if (eventEnd.isAfter(currentDate)) {
                endHour = 23;
            }

            for (int hour = startHour; hour <= endHour; hour++) {
                eventsPerHour.computeIfAbsent(hour, k -> new ArrayList<>()).add(event);
            }
        }

        for (Event event : events) {
            int startHour = event.getStartDateTime().toLocalTime().getHour();
            int endHour = event.getEndDateTime().toLocalTime().getHour();

            int maxOverlap = 1;
            for (int hour = startHour; hour <= endHour; hour++) {
                maxOverlap = Math.max(maxOverlap, eventsPerHour.get(hour).size());
            }

            double totalWidth = ((Pane) getNodeFromGridPane(gridPane, 1, startHour + 1)).getWidth() - (maxOverlap + 1) * spacing;
            double eventWidth = totalWidth / maxOverlap;

            eventWidths.put(event, eventWidth);

            double positionX;

            for (int hour = startHour; hour <= endHour; hour++) {
                List<Event> eventsInThisHour = eventsPerHour.get(hour);
                Pane hourCell = (Pane) getNodeFromGridPane(gridPane, 1, hour + 1);

                if (hour == startHour) {
                    positionX = eventsInThisHour.indexOf(event) * (eventWidth + spacing);
                    eventPositions.put(event, positionX);
                }

                Rectangle eventRect = new Rectangle();
                eventRect.setWidth(eventWidth);
                eventRect.setHeight(hourCell.getHeight());
                eventRect.setFill(Color.GREENYELLOW);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(eventRect);

                if (hour == startHour) {
                    Text eventText = new Text(event.getTitle());
                    stackPane.getChildren().add(eventText);
                }

                stackPane.setLayoutX(eventPositions.get(event));
                hourCell.getChildren().add(stackPane);
            }
        }
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

            //hourCell.setMinWidth(100);
            //hourCell.setMaxWidth(150);
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

    public Button getAddEventButton() {
        return addEventButton;
    }

    public Button getAddTaskButton() {
        return addTaskButton;
    }

    public void setDateLabel(LocalDate date) {
        dateLabel.setText(date.format(DateTimeFormatter.ofPattern("d MMMM (EEEE), yyyy")));
    }
}

