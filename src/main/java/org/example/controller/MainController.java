package org.example.controller;

import javafx.stage.Stage;
import org.example.model.Calendar;
import org.example.model.Event;
import org.example.model.Task;
import org.example.view.EventDialog;
import org.example.view.MainView;
import org.example.view.TaskDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

public class MainController {
    private MainView mainView;
    private final Stage stage;
    private DayController dayController;
    private WeekController weekController;
    private MonthController monthController;
    private final Integer DAY_WIDTH = 300;
    private final Calendar calendar;

    public MainController(Stage stage) {
        this.stage = stage;
        this.calendar = new Calendar(); // Then it will be loaded

    }

    public void initialize() {
        this.dayController = new DayController(calendar, mainView.getDayView());
        this.weekController = new WeekController(calendar, mainView.getWeekView());
        this.monthController = new MonthController(calendar, mainView.getMonthView());
        mainView.getViewSelector().valueProperty().addListener((obs, oldValue, newValue) -> {
            switch (newValue) {
                case "Day view":
                    mainView.setCenter(mainView.getDayView());
                    stage.setWidth(DAY_WIDTH);
                    stage.setHeight(DAY_WIDTH * 2.2);
                    updateEventsInView();
                    updateTaskInView();
                    break;
                case "Week view":
                    mainView.setCenter(mainView.getWeekView());
                    stage.setWidth(DAY_WIDTH * 4.2);
                    stage.setHeight(DAY_WIDTH * 2.2);
                    updateEventsInView();
                    updateTaskInView();
                    break;
                case "Month view":
                    mainView.setCenter(mainView.getMonthView());
                    stage.setWidth(DAY_WIDTH * 3);
                    stage.setHeight(DAY_WIDTH * 2.5);
                    //mainView.getMonthView().updateGrid(monthController.getStartDate());
                    updateEventsInView();
                    updateTaskInView();
                    break;
            }
        });
        mainView.getAddEventButton().setOnAction(event -> {
            showEventDialog();
            updateEventsInView();
        });

        mainView.getAddTaskButton().setOnAction(event -> {
            showTaskDialog();
            updateTaskInView();
        });

    }

    private void showEventDialog() {
        EventDialog eventDialog = new EventDialog();
        Map<String, Object> eventData = eventDialog.displayAndGetEventData();

        if (eventData != null) {
            String title = (String) eventData.get("title");
            String description = (String) eventData.get("description");
            LocalDateTime startDateTime = (LocalDateTime) eventData.get("startDateTime");
            LocalDateTime endDateTime = (LocalDateTime) eventData.get("endDateTime");

            calendar.createEvent(title, description, startDateTime, endDateTime);
        }
    }

    private void updateEventsInView() {
        String currentView = mainView.getViewSelector().getValue();

        List<Event> events;

        switch (currentView) {
            case "Day view":
                LocalDate currentDate = dayController.getCurrentDate();
                events = calendar.listEventsBetween(currentDate, currentDate);
                mainView.getDayView().updateGridWithEvents(events, currentDate);
                break;
            case "Week view":
                LocalDate startWeekDay = weekController.getStartDate();
                events = calendar.listEventsBetween(startWeekDay, startWeekDay.plusDays(6));
                mainView.getWeekView().updateGridWithEvents(events, startWeekDay);
                break;
            case "Month view":
                LocalDate startMonthDay = monthController.getStartDate();
                events = calendar.listEventsBetween(startMonthDay, startMonthDay.with(TemporalAdjusters.lastDayOfMonth()));
                mainView.getMonthView().updateGridWithEvents(events, startMonthDay);
                break;
        }
    }

    public DayController getDayController() {
        return dayController;
    }

    public WeekController getWeekController() {
        return weekController;
    }

    public MonthController getMonthController() {
        return monthController;
    }

    public void removeEvent(Event event) {
        calendar.removeEvent(event.getId());
        updateEventsInView();
    }

    public void removeTask(Task task) {
        calendar.removeTask(task.getId());
        updateTaskInView();
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    private void showTaskDialog() {
        TaskDialog taskDialog = new TaskDialog();
        Map<String, Object> taskData = taskDialog.displayAndGetTaskData();

        if (taskData != null) {
            String title = (String) taskData.get("title");
            String description = (String) taskData.get("description");
            LocalDateTime expDate = (LocalDateTime) taskData.get("expDate");

            calendar.createTask(title, description, expDate);
        }
    }

    private void updateTaskInView() {
        String currentView = mainView.getViewSelector().getValue();

        List<Task> tasks;

        switch (currentView) {
            case "Day view":
                LocalDate currentDate = dayController.getCurrentDate();
                tasks = calendar.listTasksBetween(currentDate, currentDate);
                mainView.getDayView().updateGridWithTasks(tasks, currentDate);
                break;
            case "Week view":
                LocalDate startWeekDay = weekController.getStartDate();
                tasks = calendar.listTasksBetween(startWeekDay, startWeekDay.plusDays(6));
                mainView.getWeekView().updateGridWithTasks(tasks, startWeekDay);
                break;
            case "Month view":
                LocalDate startMonthDay = monthController.getStartDate();
                tasks = calendar.listTasksBetween(startMonthDay, startMonthDay.with(TemporalAdjusters.lastDayOfMonth()));
                mainView.getMonthView().updateGridWithTasks(tasks, startMonthDay);
                break;
        }
    }
}