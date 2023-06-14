package org.example.controller;

import javafx.stage.Stage;
import org.example.model.Calendar;
import org.example.model.Event;
import org.example.view.EventDialog;
import org.example.view.MainView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MainController {
    private final MainView mainView;
    private final Stage stage;
    private final DayController dayController;
    private final WeekController weekController;
    private final Integer DAY_WIDTH = 300;
    private final Calendar calendar;

    public MainController(MainView mainView, Stage stage) {
        this.mainView = mainView;
        this.stage = stage;
        this.calendar = new Calendar(); // Then it will be loaded
        this.dayController = new DayController(calendar, mainView.getDayView());
        this.weekController = new WeekController(calendar, mainView.getWeekView());
        initialize();
    }

    private void initialize() {
        mainView.getViewSelector().valueProperty().addListener((obs, oldValue, newValue) -> {
            switch (newValue) {
                case "Day view":
                    mainView.setCenter(mainView.getDayView());
                    stage.setWidth(DAY_WIDTH);
                    stage.setHeight(DAY_WIDTH * 2.2);
                    updateEventsInView();
                    break;
                case "Week view":
                    mainView.setCenter(mainView.getWeekView());
                    stage.setWidth(DAY_WIDTH * 4.2);
                    stage.setHeight(DAY_WIDTH * 2.2);
                    updateEventsInView();
                    break;
                case "Month view":
                    //mainView.setCenter(mainView.getMonthView());
                    //updateEventsInView();
                    break;
            }
        });
        mainView.getAddEventButton().setOnAction(event -> {
            showEventDialog();
            updateEventsInView();
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

                break;
        }
    }
}

