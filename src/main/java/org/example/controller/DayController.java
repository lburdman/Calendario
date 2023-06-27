package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Event;
import org.example.model.Task;
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
        updateCalendarItemInView();
    }

    public void updateCalendarItemInView(){
        List<Event> events = calendar.listEventsBetween(currentDate, currentDate);
        List<Task> tasks = calendar.listTasksBetween(currentDate, currentDate);
        dayView.updateGridWithCalendarItem(events, tasks, currentDate);

    }

    private void initialize() {
        dayView.getPrevDayButton().setOnAction(event -> {
            currentDate = currentDate.minusDays(1);
            dayView.setDateLabel(currentDate);
            updateCalendarItemInView();
        });
        dayView.getNextDayButton().setOnAction(event -> {
            currentDate = currentDate.plusDays(1);
            dayView.setDateLabel(currentDate);
            updateCalendarItemInView();
        });
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }
}

