package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Event;
import org.example.model.Task;
import org.example.view.MonthView;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class MonthController {
    private final Calendar calendar;
    private final MonthView monthView;
    private LocalDate startDate;

    public MonthController(Calendar calendar, MonthView monthView) {
        this.calendar = calendar;
        this.monthView = monthView;
        this.startDate = LocalDate.now().withDayOfMonth(1);
        initialize();
        updateCalendarItemInView(startDate);
    }

    public void updateCalendarItemInView(LocalDate startDate){
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        List<Event> events = calendar.listEventsBetween(startDate, endDate);
        List<Task> tasks = calendar.listTasksBetween(startDate, endDate);
        monthView.updateGridWithCalendarItem(events, tasks, startDate);
    }

    private void initialize() {
        monthView.getPrevMonthButton().setOnAction(event -> {
            startDate = startDate.minusMonths(1);
            monthView.setMonthLabel(startDate);
            monthView.updateGrid(startDate);
            updateCalendarItemInView(startDate);
        });
        monthView.getNextMonthButton().setOnAction(event -> {
            startDate = startDate.plusMonths(1);
            monthView.setMonthLabel(startDate);
            monthView.updateGrid(startDate);
            updateCalendarItemInView(startDate);
        });
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
