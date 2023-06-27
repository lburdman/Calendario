package org.example.view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.controller.MainController;
import org.example.model.Event;
import org.example.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class MonthView extends BorderPane {
    private final Label monthLabel;
    private final Button prevMonthButton;
    private final Button nextMonthButton;
    private final GridPane gridPane;
    private final MainController mainController;
    private static final int NUM_DAYS_IN_WEEK = 7;
    private static final String[] DAYS_OF_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public MonthView(MainController mainController) {
        monthLabel = new Label("");
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        setMonthLabel(firstDayOfMonth);

        prevMonthButton = new Button("<");
        nextMonthButton = new Button(">");

        HBox monthNavigation = new HBox(5);
        monthNavigation.setAlignment(Pos.CENTER);
        monthNavigation.getChildren().addAll(prevMonthButton, monthLabel, nextMonthButton);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(5));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        updateGrid(firstDayOfMonth);

        this.setTop(monthNavigation);
        this.setCenter(gridPane);
        this.mainController = mainController;
    }

    public void updateGridWithCalendarItem(List<Event> events, List<Task> tasks, LocalDate currentDate){
        updateGridWithEvents(events, currentDate);
        updateGridWithTasks(tasks, currentDate);
    }

    public void updateGrid(LocalDate firstDayOfMonth) {
        gridPane.getChildren().clear();
        gridPane.setMinWidth(200);
        int startColumn = firstDayOfMonth.getDayOfWeek().getValue();
        int row = 1;

        for (int dayIndex = 0; dayIndex < DAYS_OF_WEEK.length; dayIndex++) {
            Label dayLabel = new Label(DAYS_OF_WEEK[dayIndex]);
            //dayLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            gridPane.add(dayLabel, dayIndex, 0);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
        }

        for (int day = 1; day <= firstDayOfMonth.lengthOfMonth(); day++) {
            Label dayLabel = new Label(String.valueOf(day));
            VBox dayCellContainer = new VBox();

            Pane eventPane = new Pane();
            eventPane.setPrefSize(80, 40);
            eventPane.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");

            Pane taskPane = new Pane();
            taskPane.setPrefSize(80, 40);
            taskPane.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");

            dayCellContainer.getChildren().addAll(eventPane, taskPane);

            /*Pane dayCell = new Pane(); // Empty cell

            //dayLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            dayCell.setPrefWidth(80);
            dayCell.setPrefHeight(80);
            dayCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");*/

            gridPane.add(dayLabel, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, row);
            gridPane.add(dayCellContainer, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, row + 1);

            if ((day + startColumn - 1) % NUM_DAYS_IN_WEEK == 0) {
                row += 2; // Increase the row by 2 to leave space between weeks
            }

            if (firstDayOfMonth.withDayOfMonth(day).equals(LocalDate.now())) {
                dayLabel.setStyle("-fx-background-color: #7FB3D5 ; -fx-font-weight: bold; -fx-background-radius: 40%;");
            }

            GridPane.setHalignment(dayLabel, HPos.CENTER);
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

    public void setMonthLabel(LocalDate startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL" + " - " + "yyyy");
        String formattedStartDate = startDate.format(formatter);
        monthLabel.setText(formattedStartDate);
    }

    public Button getPrevMonthButton() {
        return prevMonthButton;
    }
    public Button getNextMonthButton() {
        return nextMonthButton;
    }
    public Label getMonthLabel(){
        return monthLabel;
    }

    public void updateGridWithEvents(List<Event> events, LocalDate startDate) {
        clearGrid();
        double spacing = 2.0;

        // Sort the events by start time
        events.sort(Comparator.comparing(Event::getStartDateTime));

        Map<Integer, List<Event>> eventsPerDay = new HashMap<>();
        Map<Event, Integer> eventRelativePosition = new HashMap<>();

        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        // First pass: calculate the number of overlapping events for each day
        for (Event event : events) {
            LocalDateTime eventStart = event.getStartDateTime();
            LocalDateTime eventEnd = event.getEndDateTime();

            int startDay = eventStart.toLocalDate().isBefore(startDate) ? 1 : eventStart.toLocalDate().getDayOfMonth();
            int endDay = eventEnd.toLocalDate().isAfter(endDate) ? eventEnd.toLocalDate().lengthOfMonth() : eventEnd.toLocalDate().getDayOfMonth();

            for (int day = startDay; day <= endDay; day++) {
                eventsPerDay.computeIfAbsent(day, k -> new ArrayList<>()).add(event);
            }

            eventRelativePosition.put(event, eventsPerDay.get(startDay).indexOf(event));
        }

        // Second pass: draw the events with the calculated widths and positions
        for (Event event : events) {
            LocalDate eventStart = event.getStartDateTime().toLocalDate();
            LocalDate eventEnd = event.getEndDateTime().toLocalDate();

            int startDay = eventStart.isBefore(startDate) ? 1 : eventStart.getDayOfMonth();
            int endDay = eventEnd.isAfter(endDate) ? eventEnd.lengthOfMonth() : eventEnd.getDayOfMonth();

            int maxOverlap = 1;
            for (int day = startDay; day <= endDay; day++) {
                maxOverlap = Math.max(maxOverlap, eventsPerDay.get(day).size());
            }

            int startColumn = mainController.getMonthController().getStartDate().getDayOfWeek().getValue();
            int row = (startDay + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
            int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;

            VBox dayCellContainer = (VBox) getNodeFromGridPane(gridPane, (startDay + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            Pane eventsPane = (Pane) dayCellContainer.getChildren().get(0);

            //Node cellNode = getNodeFromGridPane(gridPane, (startDay + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            double totalWidth = eventsPane.getWidth() - (maxOverlap + 1) * spacing;
            double eventWidth = totalWidth / maxOverlap;

            double positionX = eventRelativePosition.get(event) * (eventWidth + spacing);

            for (int day = startDay; day <= endDay; day++) {
                drawEvent(event, day, eventWidth, positionX,startColumn);
            }

        }
    }

    private void clearGrid() {
        int startColumn = mainController.getMonthController().getStartDate().getDayOfWeek().getValue();
        int lastDayOfMonth = mainController.getMonthController().getStartDate().lengthOfMonth();

        for (int day = 1; day <= lastDayOfMonth; day++) {
            int row = (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
            int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;
            VBox dayCell = (VBox) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            Pane eventsPane = (Pane) dayCell.getChildren().get(0);
            Pane tasksPane = (Pane) dayCell.getChildren().get(1);

            eventsPane.getChildren().clear();
            tasksPane.getChildren().clear();
            eventsPane.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray; -fx-pref-width: 200px; -fx-pref-height: 40px;");
            tasksPane.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray; -fx-pref-width: 200px; -fx-pref-height: 40px;");
        }
    }

    private void drawEvent(Event event, int day, double eventWidth, double positionX, int startColumn) {
        int row = (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
        int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;

        VBox dayCell = (VBox) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
        Pane eventsPane = (Pane) dayCell.getChildren().get(0);

        RectangleEvent eventRect = new RectangleEvent(event);
        eventRect.setWidth(eventWidth);
        eventRect.setHeight(eventsPane.getHeight());
        eventRect.setFill(Color.GREEN);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(eventRect);

        // Add text to the first rectangle
        if (day == event.getStartDateTime().toLocalDate().getDayOfMonth()) {
            Text eventText = new Text(event.getTitle());
            stackPane.getChildren().add(eventText);
        }

        stackPane.setLayoutX(positionX);
        eventsPane.getChildren().add(stackPane);

        eventRect.setOnMouseClicked(event1 -> EventRectangleView.showDetails(eventRect, eventsPane, mainController));
    }


    public void updateGridWithTasks(List<Task> tasks, LocalDate startDate) {
        double spacing = 2.0;

        // Sort the events by start time
        tasks.sort(Comparator.comparing(Task::getExpDate));

        Map<Integer, List<Task>> tasksPerDay = new HashMap<>();
        Map<Task, Integer> taskRelativePosition = new HashMap<>();

        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        // First pass: calculate the number of overlapping events for each day
        for (Task task : tasks) {
            LocalDateTime taskStart = LocalDateTime.now();
            LocalDateTime taskEnd = task.getExpDate();

            int startDay = taskStart.toLocalDate().isBefore(startDate) ? 1 : taskStart.toLocalDate().getDayOfMonth();
            int endDay = taskEnd.toLocalDate().isAfter(endDate) ? taskEnd.toLocalDate().lengthOfMonth() : taskEnd.toLocalDate().getDayOfMonth();

            for (int day = startDay; day <= endDay; day++) {
                tasksPerDay.computeIfAbsent(day, k -> new ArrayList<>()).add(task);
            }

            taskRelativePosition.put(task, tasksPerDay.get(startDay).indexOf(task));
        }

        // Second pass: draw the events with the calculated widths and positions
        for (Task task : tasks) {
            LocalDate taskStart = LocalDate.now();
            LocalDate taskEnd = task.getExpDate().toLocalDate();

            int startDay = taskStart.isBefore(startDate) ? 1 : taskStart.getDayOfMonth();
            int endDay = taskEnd.isAfter(endDate) ? taskEnd.lengthOfMonth() : taskEnd.getDayOfMonth();

            int maxOverlap = 1;
            for (int day = startDay; day <= endDay; day++) {
                maxOverlap = Math.max(maxOverlap, tasksPerDay.get(day).size());
            }

            int startColumn = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();
            int row = (startDay + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
            int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;

            VBox dayCellContainer = (VBox) getNodeFromGridPane(gridPane, (startDay + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            Pane tasksPane = (Pane) dayCellContainer.getChildren().get(1);
            //Node cellNode = getNodeFromGridPane(gridPane, (startDay + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);

            double totalWidth = tasksPane.getWidth() - (maxOverlap + 1) * spacing;
            double taskWidth = totalWidth / maxOverlap;

            double positionX = taskRelativePosition.get(task) * (taskWidth + spacing);

            for (int day = startDay; day <= endDay; day++) {
                drawTask(task, day, taskWidth, positionX,startColumn);
            }
        }
    }

    private void drawTask(Task task, int day, double taskWidth, double positionX, int startColumn) {
        int row = (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
        int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;

        VBox dayCell = (VBox) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
        Pane tasksPane = (Pane) dayCell.getChildren().get(1);

        RectangleTask taskRect = new RectangleTask(task);
        taskRect.setWidth(taskWidth);
        taskRect.setHeight(tasksPane.getHeight());
        taskRect.setFill(Color.VIOLET);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(taskRect);

        // Add text to the first rectangle
        if (day == LocalDate.now().getDayOfMonth()) {
            Text taskText = new Text(task.getTitle());
            taskText.setFont(new Font(9));
            stackPane.getChildren().add(taskText);
        }

        stackPane.setLayoutX(positionX);

        tasksPane.getChildren().add(stackPane);

        taskRect.setOnMouseClicked(event1 -> TaskRectangleView
                .showDetails(taskRect, tasksPane, mainController));
    }
}