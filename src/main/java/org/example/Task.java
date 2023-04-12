package org.example;

import java.time.LocalDateTime;
import java.util.List;

public class Task {
    String title;
    String description;
    LocalDateTime expDate;
    boolean complete = false;
    List<Alarm> Alarms;

    public Task(String title, String description, LocalDateTime expDate) {
        this.title = title;
        this.description = description;
        this.expDate = expDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getExpDate() {
        return expDate;
    }

    public void setAsCompleted() {
        this.complete = true;
    }
    public boolean isCompleted() {
        return complete;
    }
}


