package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task extends CalendarItem {
    protected LocalDateTime expDate;
    private boolean complete;

    public Task(String title, String description, LocalDateTime expDate) {
        super(title, description, null);
        if(expDate.isAfter(LocalDateTime.now())) {
            this.expDate = expDate;
        } else {
            throw new RuntimeException("Invalid date");
        }
        this.complete = false;
    }

    public Task(LocalDateTime expDate) {
        super(null, null, null);
        if(expDate.isAfter(LocalDateTime.now())) {
            this.expDate = expDate;
        } else {
            throw new RuntimeException("Invalid date");
        }
        this.complete = false;
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
/*
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expDate);
    }
*/
    @Override
    public boolean isCalendarItemBetween(LocalDate startDate, LocalDate endDate) {
        return expDate.toLocalDate().equals(startDate);
    }
}
