package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Event extends CalendarItem {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private RepeatableSpec repeatableSpec = null;
    private UUID parentId = null;

    public Event() {

    }

    public Event(String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(title, description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Event(String title, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(title, null);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public RepeatableSpec getRepeatableSpec() {
        return repeatableSpec;
    }
    public void setRepeatableSpec(RepeatableSpec repeatableSpec) {
        this.repeatableSpec = repeatableSpec;
    }
    public UUID getParentId() {
        return parentId;
    }
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
    @JsonIgnore
    public boolean isRepeatable() {
        return repeatableSpec != null;
    }
    public Event clone(LocalDateTime newStartDateTime, LocalDateTime newEndDateTime) {
        Event clonedEvent = new Event(this.getTitle(), this.getDescription(), newStartDateTime, newEndDateTime);
        clonedEvent.setParentId(this.getId());

        return clonedEvent;
    }

    @Override
    public boolean isCalendarItemBetween(LocalDate startDate, LocalDate endDate) {
        LocalDate start = startDateTime.toLocalDate();
        LocalDate end = endDateTime.toLocalDate();

        return (start.isBefore(endDate) && end.isAfter(startDate)) || start.equals(startDate) || end.equals(endDate); //WOW
    }

    @Override
    public String toString() {
        return "Event{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", repeatableSpec=" + repeatableSpec +
                '}';
    }
}