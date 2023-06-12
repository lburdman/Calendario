package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Event;
import org.example.view.DayView;
import org.example.view.EventDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class DayController {
    private Calendar calendar;
    private DayView dayView;
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
            changeDate(-1);
            updateEventsInView();
        });
        dayView.getNextDayButton().setOnAction(event -> {
            changeDate(1);
            updateEventsInView();
        });
        dayView.getAddEventButton().setOnAction(event -> {
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

    private void changeDate(int days) {
        currentDate = currentDate.plusDays(days);
        dayView.setDateLabel(currentDate);
    }

    // Aquí puedes agregar más lógica para actualizar el modelo y/o cambiar la vista
}

