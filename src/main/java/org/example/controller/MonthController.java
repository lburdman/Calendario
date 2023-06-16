package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Event;
import org.example.model.Task;
import org.example.view.MonthView;
import org.example.view.WeekView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        updateEventsInView();
        updateTasksInView();
    }

    private void updateEventsInView() {
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Event> events = calendar.listEventsBetween(startDate, endDate);
        monthView.updateGridWithEvents(events, startDate);
    }

    private void updateTasksInView() {
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Task> tasks = calendar.listTasksBetween(startDate, endDate);
        monthView.updateGridWithTasks(tasks, startDate);
    }

    private void initialize() {
        monthView.getPrevMonthButton().setOnAction(event -> {
            startDate = startDate.minusMonths(1);
            monthView.setMonthLabel(startDate);
            monthView.updateGrid(startDate);
            updateEventsInView();
            updateTasksInView();

        });
        monthView.getNextMonthButton().setOnAction(event -> {
            startDate = startDate.plusMonths(1);
            monthView.setMonthLabel(startDate);
            monthView.updateGrid(startDate);
            updateEventsInView();
            updateTasksInView();
        });

    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
