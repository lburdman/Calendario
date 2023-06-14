package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Event;
import org.example.view.DayView;

import java.time.LocalDate;
import java.util.List;

public class DayController {
    private final Calendar calendar;
    private final DayView dayView;
    private LocalDate currentDate;

    public DayController(Calendar calendar, DayView dayView) {
        this.calendar = calendar;
        this.dayView = dayView;
        this.currentDate = LocalDate.now();
        initialize();
        updateEventsInView();
    }

    private void updateEventsInView() {
        List<Event> events = calendar.listEventsBetween(currentDate, currentDate);
        dayView.updateGridWithEvents(events, currentDate);
    }

    private void initialize() {
        dayView.getPrevDayButton().setOnAction(event -> {
            currentDate = currentDate.minusDays(1);
            dayView.setDateLabel(currentDate);
            updateEventsInView();
        });
        dayView.getNextDayButton().setOnAction(event -> {
            currentDate = currentDate.plusDays(1);
            dayView.setDateLabel(currentDate);
            updateEventsInView();
        });
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }
}

