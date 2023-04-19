package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private String description;
    private LocalDateTime expDate;
    private boolean complete;
    private ArrayList<Alarm> alarms;

    public Task(String title, String description, LocalDateTime expDate) {
        if(expDate.isAfter(LocalDateTime.now())) {
            this.expDate = expDate;
        } else {
            throw new RuntimeException("Invalid expiration date");
        }
        this.title = title.isBlank() ? "No title" : title;
        this.description = description.isBlank() ? "No description" : description;
        this.complete = false;
        this.alarms = new ArrayList<>();
    }

    public Task(LocalDateTime expDate) {
        if(expDate.isAfter(LocalDateTime.now())) {
            this.expDate = expDate;
        } else {
            throw new RuntimeException("Invalid Expiration Date");
        }
        this.title = "No Title";
        this.description = "No Description";
        this.complete = false;
        this.alarms = new ArrayList<>();
    }

    private Task() {
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title.isBlank() ? "sin titulo" : title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description.isBlank() ? "sin descripcion" : description;
    }

    public LocalDateTime getExpDate() {

        return expDate;
    }

    public void setExpDate(LocalDateTime expDate) {

        this.expDate = expDate;
    }

    public void setAsCompleted() {

        this.complete = true;
    }

    public boolean isCompleted() {

        return complete;
    }

    public boolean isExpired() {

        return LocalDateTime.now().isAfter(expDate);
    }

    public List<Alarm> getAlarms() {

        return alarms;
    }

    public void addAlarm(Alarm Alarm) {

        this.alarms.add(Alarm);
    }

    public void deleteAlarm(Alarm Alarm) {

        this.alarms.remove(Alarm);
    }

    public void deleteAllAlarms() {

        this.alarms.clear();
    }
}


