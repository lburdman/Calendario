package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task extends CalendarItem {
    protected LocalDateTime expDate;
    private boolean complete;

    public Task(String title, String description, LocalDateTime expDate) {
        super(title, description);
        this.expDate = expDate;
        this.complete = false;
    }

    public Task(LocalDateTime expDate) {
        super(null, null);
        this.expDate = expDate;
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

    @Override
    public boolean isCalendarItemBetween(LocalDate startDate, LocalDate endDate) {
        return expDate.toLocalDate().equals(startDate);
    }

    public void setDueDate(LocalDateTime dueDate) {
    }
}