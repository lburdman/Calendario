package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Event;
import org.example.view.EventDialog;
import org.example.view.WeekView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class WeekController {
    private Calendar calendar;
    private WeekView weekView;
    private LocalDate startDate;

    public WeekController(Calendar calendar, WeekView weekView) {
        this.calendar = calendar;
        this.weekView = weekView;
        this.startDate = LocalDate.now().with(DayOfWeek.MONDAY); // Set to the start of the current week (Monday)
        initialize();
        updateEventsInView();
    }

    private void updateEventsInView() {
        LocalDate endDate = startDate.plusDays(6);
        List<Event> events = calendar.listEventsBetween(startDate, endDate);
        weekView.updateGridWithEvents(events, startDate);
    }

    private void initialize() {
        weekView.getPrevWeekButton().setOnAction(event -> {
            changeDate(-1);
            updateEventsInView();
        });
        weekView.getNextWeekButton().setOnAction(event -> {
            changeDate(1);
            updateEventsInView();
        });
        weekView.getAddEventButton().setOnAction(event -> {
            showEventDialog();
            updateEventsInView();
        });
        /*weekView.getViewSelector().valueProperty().addListener((obs, oldValue, newValue) -> {
            switch (newValue) {
                case "Day view":
                    // Change view -> day
                    break;
                case "Week view":
                    break;
                case "Month view":
                    // Change view -> month
                    break;
            }
        });*/
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

    private void changeDate(int weeks) {
        startDate = weeks > 0 ? startDate.plusWeeks(weeks) : startDate.minusWeeks(-weeks);
        weekView.setWeekLabel(startDate);
    }
}

