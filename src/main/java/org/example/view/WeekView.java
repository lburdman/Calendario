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
import org.example.controller.MainController;
import org.example.model.Event;
import org.example.model.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class WeekView extends BorderPane {
    private final Label weekLabel;
    private final Button prevWeekButton;
    private final Button nextWeekButton;
    private final GridPane gridPane;
    private final MainController mainController;

    private static final String[] DAYS_OF_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public WeekView(MainController mainController) {
        weekLabel = new Label("");
        LocalDate startDate = LocalDate.now().with(DayOfWeek.MONDAY);
        //LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        setWeekLabel(startDate);

        prevWeekButton = new Button("<");
        nextWeekButton = new Button(">");

        HBox dateNavigation = new HBox(1);
        dateNavigation.setAlignment(Pos.CENTER);
        dateNavigation.getChildren().addAll(prevWeekButton, weekLabel, nextWeekButton);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        makeGrid();

        this.setTop(dateNavigation);
        this.setCenter(gridPane);
        this.mainController = mainController;
    }

    public void updateGridWithCalendarItem(List<Event> events, List<Task> tasks, LocalDate currentDate){
        clearGrid();
        updateGridWithEvents(events, currentDate);
        updateGridWithTasks(tasks, currentDate);
    };

    public void updateGridWithEvents(List<Event> events, LocalDate startDate) {
        //clearGrid();
        double spacing = 2.0;

        // Sort the events by start time
        events.sort(Comparator.comparing(Event::getStartDateTime));

        Map<Integer, List<Event>> eventsPerDayAndHour = new HashMap<>();
        Map<Event, Integer> eventRelativePosition = new HashMap<>();

        LocalDate endDate = startDate.plusDays(6);

        // First pass: calculate the number of overlapping events for each day and hour
        for (Event event : events) {
            LocalDate eventStart = event.getStartDateTime().toLocalDate();
            LocalDate eventEnd = event.getEndDateTime().toLocalDate();

            int startHour = event.getStartDateTime().toLocalTime().getHour();
            int endHour = event.getEndDateTime().toLocalTime().getHour();

            int startDay = eventStart.isBefore(startDate) ? 0 : eventStart.getDayOfWeek().getValue() % 7;
            int endDay = eventEnd.isAfter(endDate) ? 6 : eventEnd.getDayOfWeek().getValue() % 7;

            for (int day = startDay; day <= endDay; day++) {
                int currentStartHour = (day == startDay) ? startHour : 0;
                int currentEndHour = (day == endDay) ? endHour : 23;

                for (int hour = currentStartHour; hour <= currentEndHour; hour++) {
                    int key = day * 24 + hour;
                    eventsPerDayAndHour.computeIfAbsent(key, k -> new ArrayList<>()).add(event);
                }
            }

            int firstKey = startDay * 24 + startHour;
            eventRelativePosition.put(event, eventsPerDayAndHour.get(firstKey).indexOf(event));
            //eventRelativePosition.put(event, eventsPerDayAndHour.computeIfAbsent(firstKey, k -> new ArrayList<>()).indexOf(event));

        }

        // Second pass: draw the events with the calculated widths and positions
        for (Event event : events) {
            int startHour = event.getStartDateTime().toLocalTime().getHour();
            int endHour = event.getEndDateTime().toLocalTime().getHour();
            int startDay = event.getStartDateTime().toLocalDate().getDayOfWeek().getValue() % 7 ;
            int endDay = event.getEndDateTime().toLocalDate().getDayOfWeek().getValue() % 7 ;

            int maxOverlap = 1;
            for (int day = startDay; day <= endDay; day++) {
                int currentStartHour = (day == startDay) ? startHour : 0;
                int currentEndHour = (day == endDay) ? endHour : 23;

                for (int hour = currentStartHour; hour <= currentEndHour; hour++) {
                    int key = day * 24 + hour;
                    maxOverlap = Math.max(maxOverlap, eventsPerDayAndHour.get(key).size());
                }
            }

            double totalWidth = ((Pane) getNodeFromGridPane(gridPane, startDay + 1, startHour + 1 )).getWidth() - (maxOverlap + 1) * spacing;
            double eventWidth = totalWidth / maxOverlap;

            double positionX = eventRelativePosition.get(event) * (eventWidth + spacing);

            // Draw the event in the grid
            for (int day = startDay; day <= endDay; day++) {
                int currentStartHour = (day == startDay) ? startHour : 0;
                int currentEndHour = (day == endDay) ? endHour : 23;

                for (int hour = currentStartHour; hour <= currentEndHour; hour++) {
                    drawEvent(event, day, hour, eventWidth, positionX);
                }
            }
        }
    }


    private void drawEvent(Event event, int day, int hour, double eventWidth, double positionX) {
        Pane dayHourCell = (Pane) getNodeFromGridPane(gridPane, day +1 , hour +1 );

        RectangleEvent eventRect = new RectangleEvent(event);
        eventRect.setWidth(eventWidth);
        eventRect.setHeight(dayHourCell.getHeight());
        eventRect.setFill(Color.GREENYELLOW);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(eventRect);

        // If it's the first hour of the event, add the event title
        if (hour == event.getStartDateTime().toLocalTime().getHour() && day == event.getStartDateTime().toLocalDate().getDayOfWeek().getValue() % 7) {
            Text eventText = new Text(event.getTitle());
            stackPane.getChildren().add(eventText);
        }

        stackPane.setLayoutX(positionX);
        dayHourCell.getChildren().add(stackPane);

        eventRect.setOnMouseClicked(event1 -> EventRectangleView
                .showDetails(eventRect, dayHourCell, mainController));
    }


    /*public void updateGridWithEvents(List<Event> events, LocalDate startDate) {
        clearGrid();
        double spacing = 2.0;

        events.sort(Comparator.comparing(Event::getStartDateTime));

        Map<Integer, List<Event>> eventsPerDayAndHour = new HashMap<>();
        Map<Event, Double> eventWidths = new HashMap<>();
        Map<Event, Double> eventPositions = new HashMap<>();

        LocalDate endDate = startDate.plusDays(6);

        for (Event event : events) {
            LocalDate eventStart = event.getStartDateTime().toLocalDate();
            LocalDate eventEnd = event.getEndDateTime().toLocalDate();

            int startHour = event.getStartDateTime().toLocalTime().getHour();
            int endHour = event.getEndDateTime().toLocalTime().getHour();

            int startDay = eventStart.isBefore(startDate) ? 0 : eventStart.getDayOfWeek().getValue() % 7;
            int endDay = eventEnd.isAfter(endDate) ? 6 : eventEnd.getDayOfWeek().getValue() % 7;

            for (int day = startDay; day <= endDay; day++) {
                for (int hour = startHour; hour <= endHour; hour++) {
                    int key = day * 24 + hour;
                    eventsPerDayAndHour.computeIfAbsent(key, k -> new ArrayList<>()).add(event);
                }
            }
        }

        for (Event event : events) {
            int startHour = event.getStartDateTime().toLocalTime().getHour();
            int endHour = event.getEndDateTime().toLocalTime().getHour();
            int startDay = event.getStartDateTime().toLocalDate().getDayOfWeek().getValue() % 7;
            int endDay = event.getEndDateTime().toLocalDate().getDayOfWeek().getValue() % 7;

            int maxOverlap = 1;
            for (int day = startDay; day <= endDay; day++) {
                for (int hour = startHour; hour <= endHour; hour++) {
                    int key = day * 24 + hour;
                    maxOverlap = Math.max(maxOverlap, eventsPerDayAndHour.get(key).size());
                }
            }

            double totalWidth = ((Pane) getNodeFromGridPane(gridPane, startDay + 1, startHour + 1)).getWidth() - (maxOverlap + 1) * spacing;
            double eventWidth = totalWidth / maxOverlap;
            eventWidths.put(event, eventWidth);

            double positionX;

            for (int day = startDay; day <= endDay; day++) {
                for (int hour = startHour; hour <= endHour; hour++) {
                    int key = day * 24 + hour;
                    List<Event> eventsInThisHour = eventsPerDayAndHour.get(key);
                    Pane hourCell = (Pane) getNodeFromGridPane(gridPane, day + 1, hour + 1);

                    if (hour == startHour && day == startDay) {
                        positionX = eventsInThisHour.indexOf(event) * (eventWidth + spacing);
                        eventPositions.put(event, positionX);
                    }

                    Rectangle eventRect = new Rectangle();
                    eventRect.setWidth(eventWidth);
                    eventRect.setHeight(hourCell.getHeight());
                    eventRect.setFill(Color.GREENYELLOW);

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(eventRect);

                    if (hour == startHour && day == startDay) {
                        Text eventText = new Text(event.getTitle());
                        stackPane.getChildren().add(eventText);
                    }

                    stackPane.setLayoutX(eventPositions.get(event));
                    hourCell.getChildren().add(stackPane);
                }
            }
        }
    }*/

    private void clearGrid() {
        for (int day = 0; day < 7; day++) {
            for (int hour = 0; hour < 24; hour++) {
                Pane hourCell = (Pane) getNodeFromGridPane(gridPane, day + 1, hour + 1);
                if (hourCell != null) {
                    hourCell.getChildren().clear();
                    hourCell.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray; -fx-pref-width: 160px;");
                }
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

        for (int dayIndex = 0; dayIndex < DAYS_OF_WEEK.length; dayIndex++) {
            Label dayLabel = new Label(DAYS_OF_WEEK[dayIndex]);
            dayLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            gridPane.add(dayLabel, dayIndex + 1, 0);
        }

        for (int hour = 0; hour < 24; hour++) {
            Label hourLabel = new Label(String.format("%02d:00", hour));
            hourLabel.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
            gridPane.add(hourLabel, 0, hour + 1);

            for (int dayIndex = 0; dayIndex < DAYS_OF_WEEK.length; dayIndex++) {
                Pane cellPane = new Pane();
                cellPane.setPrefSize(100, 30);
                cellPane.setStyle("-fx-background-color: white; -fx-border-color: gray;");
                gridPane.add(cellPane, dayIndex + 1, hour + 1);
            }
        }
    }

    public Button getPrevWeekButton() {
        return prevWeekButton;
    }

    public Button getNextWeekButton() {
        return nextWeekButton;
    }

    public void setWeekLabel(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        weekLabel.setText("Week from " + formattedStartDate + " to " + formattedEndDate);
    }

    public void updateGridWithTasks(List<Task> tasks, LocalDate startDate) {
        //clearGrid();
        double spacing = 2.0;

        // Sort the events by start time
        //tasks.sort(Comparator.comparing(Task::getExpDate));

        Map<Integer, List<Task>> taskPerDayAndHour = new HashMap<>();
        Map<Task, Integer> taskRelativePosition = new HashMap<>();

        LocalDate endDate = startDate.plusDays(6);

        // First pass: calculate the number of overlapping events for each day and hour
        for (Task task : tasks) {
            LocalDate taskStart = LocalDate.now();
            LocalDate taskEnd = task.getExpDate().toLocalDate();

            int startHour = LocalDateTime.now().toLocalTime().getHour();
            int endHour = task.getExpDate().toLocalTime().getHour();

            int startDay = taskStart.isBefore(startDate) ? 0 : taskStart.getDayOfWeek().getValue() % 7;
            int endDay = taskEnd.isAfter(endDate) ? 6 : taskEnd.getDayOfWeek().getValue() % 7;

            for (int day = startDay; day <= endDay; day++) {
                int currentStartHour = (day == startDay) ? startHour : 0;
                int currentEndHour = (day == endDay) ? endHour : 23;

                for (int hour = currentStartHour; hour <= currentEndHour; hour++) {
                    int key = day * 24 + hour;
                    taskPerDayAndHour.computeIfAbsent(key, k -> new ArrayList<>()).add(task);
                }
            }

            int firstKey = startDay * 24 + startHour;
            taskRelativePosition.put(task, taskPerDayAndHour.get(firstKey).indexOf(task));
        }

        // Second pass: draw the events with the calculated widths and positions
        for (Task task : tasks) {
            int startHour = LocalDateTime.now().toLocalTime().getHour();
            int endHour = task.getExpDate().toLocalTime().getHour();
            int startDay = LocalDateTime.now().toLocalDate().getDayOfWeek().getValue() % 7;
            int endDay = task.getExpDate().toLocalDate().getDayOfWeek().getValue() % 7;

            int maxOverlap = 1;
            for (int day = startDay; day <= endDay; day++) {
                int currentStartHour = (day == startDay) ? startHour : 0;
                int currentEndHour = (day == endDay) ? endHour : 23;

                for (int hour = currentStartHour; hour <= currentEndHour; hour++) {
                    int key = day * 24 + hour;
                    maxOverlap = Math.max(maxOverlap, taskPerDayAndHour.get(key).size());
                }
            }

            double totalWidth = ((Pane) getNodeFromGridPane(gridPane, startDay + 1, startHour + 1)).getWidth() - (maxOverlap + 1) * spacing;
            double taskWidth = totalWidth / maxOverlap;

            double positionX = taskRelativePosition.get(task) * (taskWidth + spacing);

            // Draw the event in the grid
            for (int day = startDay; day <= endDay; day++) {
                int currentStartHour = (day == startDay) ? startHour : 0;
                int currentEndHour = (day == endDay) ? endHour : 23;

                for (int hour = currentStartHour; hour <= currentEndHour; hour++) {
                    drawTask(task, day, hour, taskWidth, positionX);
                }
            }
        }

    }


    private void drawTask(Task task, int day, int hour, double taskWidth, double positionX) {
        Pane dayHourCell = (Pane) getNodeFromGridPane(gridPane, day + 1, hour + 1);

        Rectangle taskRect = new Rectangle();
        taskRect.setWidth(taskWidth);
        taskRect.setHeight(dayHourCell.getHeight());
        taskRect.setFill(Color.ORCHID);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(taskRect);

        // If it's the first hour of the event, add the event title
        if (hour == LocalDateTime.now().toLocalTime().getHour() && day == LocalDateTime.now().toLocalDate().getDayOfWeek().getValue() % 7) {
            Text taskText = new Text(task.getTitle());
            stackPane.getChildren().add(taskText);
        }

        stackPane.setLayoutX(positionX);
        dayHourCell.getChildren().add(stackPane);
    }
}

