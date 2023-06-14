package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Event;
import org.example.view.WeekView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class WeekController {
    private final Calendar calendar;
    private final WeekView weekView;
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
            startDate = startDate.minusWeeks(1);
            weekView.setWeekLabel(startDate);
            updateEventsInView();
        });
        weekView.getNextWeekButton().setOnAction(event -> {
            startDate = startDate.plusWeeks(1);
            weekView.setWeekLabel(startDate);
            updateEventsInView();
        });
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}

