package org.example.controller;

import org.example.model.Calendar;
import javafx.util.Pair;
import org.example.view.EventDialog;

import java.time.LocalDateTime;
import java.util.Map;

public class EventController {
    private Calendar calendar;

    public EventController(Calendar calendar) {
        this.calendar = calendar;
    }

    public void addEvent() {
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
}
