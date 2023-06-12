package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.example.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DayView extends BorderPane {
    private Label dateLabel;
    private Button prevDayButton;
    private Button nextDayButton;
    private GridPane gridPane;
    private Button addEventButton;
    private Button addTaskButton;
    private ComboBox<String> viewSelector;

    public DayView() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d (EEEE), yyyy");

        dateLabel = new Label(LocalDate.now().format(dateFormatter));
        prevDayButton = new Button("<");
        nextDayButton = new Button(">");

        HBox dateNavigation = new HBox(1);
        dateNavigation.setAlignment(Pos.CENTER);
        dateNavigation.getChildren().addAll(prevDayButton, dateLabel, nextDayButton);

        viewSelector = new ComboBox<>();
        viewSelector.getItems().addAll("Day view", "Week view", "Month view");
        viewSelector.setValue("Day view");

        VBox topContainer = new VBox(5);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(viewSelector, dateNavigation);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        makeGrid();

        addEventButton = new Button("Add Event");
        addTaskButton = new Button("Add Task");
        HBox buttonsBox = new HBox(10); // 10px de espacio entre botones
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(addEventButton, addTaskButton);

        this.setTop(topContainer);
        this.setCenter(gridPane);
        this.setBottom(buttonsBox);
    }

    public void updateGridWithEvents(List<Event> events, LocalDate currentDate) {
        clearGrid();
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
                Label hourCell = (Label) getNodeFromGridPane(gridPane, 1, hour+1);
                if (hourCell != null) {
                    hourCell.setStyle("-fx-background-color: #ADD8E6; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
                    if (hour == startHour) {
                        hourCell.setText(event.getTitle());
                    }
                }
            }
        }
    }

    private void clearGrid() {
        for (int hour = 0; hour < 24; hour++) {
            Label hourCell = (Label) getNodeFromGridPane(gridPane, 1, hour + 1);
            if (hourCell != null) {
                hourCell.setText("                                "); // Empty cell
                hourCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
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
        for (int hour = 0; hour < 24; hour++) {
            Label hourLabel = new Label(String.format("%02d:00", hour));
            Label hourCell = new Label("                                "); // Empty cell
            hourLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            hourCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
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

    public ComboBox<String> getViewSelector() {
        return viewSelector;
    }

    public void setDateLabel(LocalDate date) {
        dateLabel.setText(date.format(DateTimeFormatter.ofPattern("d MMMM (EEEE), yyyy")));
    }
}

