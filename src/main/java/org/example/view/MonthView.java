package org.example.view;

import com.fasterxml.jackson.core.JsonParser;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

        HBox monthNavigation = new HBox(50);
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

    public void updateGrid(LocalDate firstDayOfMonth) {

        // Clear the existing grid
        gridPane.getChildren().clear();

        // Set the minimum width of the grid
        gridPane.setMinWidth(200);

        // Create the labels for the days of the week
        for (int dayIndex = 0; dayIndex < DAYS_OF_WEEK.length; dayIndex++) {
            Label dayLabel = new Label(DAYS_OF_WEEK[dayIndex]);
            //dayLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            gridPane.add(dayLabel, dayIndex, 0);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
        }

        // Calculate the start column based on the day of the week
        int startColumn = firstDayOfMonth.getDayOfWeek().getValue();

        // Initialize the row counter
        int row = 1;

        // Create the labels for each day of the month
        for (int day = 1; day <= firstDayOfMonth.lengthOfMonth(); day++) {
            Label dayLabel = new Label(String.valueOf(day));
            Pane dayCell = new Pane(); // Empty cell

            //dayLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            dayCell.setPrefWidth(80);
            dayCell.setPrefHeight(80);
            dayCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");

            // Add the day label and cell to the grid
            gridPane.add(dayLabel, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, row);
            gridPane.add(dayCell, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, row + 1);

            // Increment the row if necessary
            if ((day + startColumn - 1) % NUM_DAYS_IN_WEEK == 0) {
                row += 2; // Increase the row by 2 to leave space between weeks
            }

            if (firstDayOfMonth.withDayOfMonth(day).equals(LocalDate.now())) {
                dayLabel.setStyle("-fx-background-color: #7FB3D5 ; -fx-font-weight: bold; -fx-background-radius: 40%;");
            }

            GridPane.setHalignment(dayLabel, HPos.CENTER);
        }

    }
/*
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

            int startColumn = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();
            int row =  (startDay + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
            int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;

            double totalWidth = ((Pane) getNodeFromGridPane(gridPane, (startDay + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell)).getWidth() - (maxOverlap + 1) * spacing;
            double eventWidth = totalWidth / maxOverlap;


            double positionX = eventRelativePosition.get(event) * (eventWidth + spacing);

            for (int day = startDay; day <= endDay; day++) {
                drawEvent(event, day, eventWidth, positionX);
            }
        }
    }


    private void drawEvent(Event event, int day, double eventWidth, double positionX) {

        int startColumn = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();
        int row =  (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
        int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;
        Pane dayCell = (Pane) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK,  rowCell);

        RectangleEvent eventRect = new RectangleEvent(event);
        eventRect.setWidth(eventWidth);
        if (dayCell != null) {
            eventRect.setHeight(dayCell.getHeight());
        }

        eventRect.setFill(Color.ORCHID);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(eventRect);

        // Add text to the first rectangle
        if (day == event.getStartDateTime().toLocalDate().getDayOfMonth()) {
            Text eventText = new Text(event.getTitle());
            stackPane.getChildren().add(eventText);
        }

        stackPane.setLayoutX(positionX);

        if (dayCell != null) {
            dayCell.getChildren().add(stackPane);
        }

        eventRect.setOnMouseClicked(event1 -> EventRectangleView
                .showDetails(eventRect, dayCell, mainController));


    }

    private void clearGrid() {
        int startColumn = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();

        for (int day = 1; day < LocalDate.now().lengthOfMonth(); day++) {
            int row =  (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
            int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;
            Pane dayCell = (Pane) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            if (dayCell != null) {
                dayCell.getChildren().clear();
                dayCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray; -fx-pref-width: 200px;");
            }
        }
    }

 */


    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public void setMonthLabel(LocalDate startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL" + " - " + "YYYY");
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

    public void updateGridWithTasks(List<Task> tasks, LocalDate startDate) {
        clearGrid();

        double spacing = 2.0;

        // Sort the events by start time
        //tasks.sort(Comparator.comparing(Task::getStartDateTime));

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

            Node cellNode = getNodeFromGridPane(gridPane, (startDay + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            if (cellNode instanceof Pane) {
                double totalWidth = ((Pane) cellNode).getWidth() - (maxOverlap + 1) * spacing;
                double taskWidth = totalWidth / maxOverlap;

                double positionX = taskRelativePosition.get(task) * (taskWidth + spacing);

                for (int day = startDay; day <= endDay; day++) {
                    drawTask(task, day, taskWidth, positionX,startColumn);
                }
            }
        }
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

            int startColumn = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();
            int row = (startDay + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
            int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;

            Node cellNode = getNodeFromGridPane(gridPane, (startDay + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            if (cellNode instanceof Pane) {
                double totalWidth = ((Pane) cellNode).getWidth() - (maxOverlap + 1) * spacing;
                double eventWidth = totalWidth / maxOverlap;

                double positionX = eventRelativePosition.get(event) * (eventWidth + spacing);

                for (int day = startDay; day <= endDay; day++) {
                    drawEvent(event, day, eventWidth, positionX,startColumn);
                }
            }
        }
    }

    private void clearGrid() {
        int startColumn = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();
        int lastDayOfMonth = LocalDate.now().lengthOfMonth();

        for (int day = 1; day <= lastDayOfMonth; day++) {
            int row = (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
            int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;
            Pane dayCell = (Pane) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);
            if (dayCell != null) {
                dayCell.getChildren().clear();
                dayCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray; -fx-pref-width: 200px;");
            }
        }
    }

    private void drawEvent(Event event, int day, double eventWidth, double positionX, int startColumn) {
        int row = (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
        int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;
        Pane dayCell = (Pane) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);

        RectangleEvent eventRect = new RectangleEvent(event);
        eventRect.setWidth(eventWidth);
        if (dayCell != null) {
            eventRect.setHeight(dayCell.getHeight());
        }

        eventRect.setFill(Color.ORCHID);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(eventRect);

        // Add text to the first rectangle
        if (day == event.getStartDateTime().toLocalDate().getDayOfMonth()) {
            Text eventText = new Text(event.getTitle());
            stackPane.getChildren().add(eventText);
        }

        stackPane.setLayoutX(positionX);

        if (dayCell != null) {
            dayCell.getChildren().add(stackPane);
        }

        eventRect.setOnMouseClicked(event1 -> EventRectangleView.showDetails(eventRect, dayCell, mainController));
    }

    private void drawTask(Task task, int day, double taskWidth, double positionX, int startColumn) {
        int row = (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1;
        int rowCell = (row >= 2 && row <= 6) ? row * 2 : row + 1;
        Pane dayCell = (Pane) getNodeFromGridPane(gridPane, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, rowCell);

        RectangleTask taskRect = new RectangleTask(task);
        taskRect.setWidth(taskWidth);
        if (dayCell != null) {
            taskRect.setHeight(dayCell.getHeight());
        }

        taskRect.setFill(Color.GREENYELLOW);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(taskRect);

        // Add text to the first rectangle
        if (day == LocalDate.now().getDayOfMonth()) {
            Text taskText = new Text(task.getTitle());
            stackPane.getChildren().add(taskText);
        }

        stackPane.setLayoutX(positionX);

        if (dayCell != null) {
            dayCell.getChildren().add(stackPane);
        }

        taskRect.setOnMouseClicked(event1 -> TaskRectangleView.showDetails(taskRect, dayCell, mainController));
    }


}
