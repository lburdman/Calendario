package org.example;

import java.time.LocalDate;
import java.util.*;

public abstract class CalendarItem {
    private String title;
    private String description;
    private List<Alarm> alarms;
    private final UUID id = UUID.randomUUID();

    public CalendarItem(String title, String description) {
        this.title = (title != null) ?  title : "no title";
        this.description = (description != null) ? description : "no description";
        this.alarms = new ArrayList<>();
    }

    public abstract boolean isCalendarItemBetween(LocalDate startDate, LocalDate endDate);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.isBlank() ? "no title" : title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.isBlank() ? "no description" : description;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    public void deleteAllAlarms() {
        this.alarms.clear();
    }

    public UUID getId() {
        return id;
    }
}
