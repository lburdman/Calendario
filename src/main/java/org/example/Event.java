package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Event extends CalendarItem {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private RepeatableSpec repeatableSpec;


    public Event(String title, String description, List<Alarm> alarms, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(title, description, alarms);
        this.startDateTime = (startDateTime != null) ? startDateTime : LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
        this.endDateTime = (endDateTime != null && endDateTime.isAfter(startDateTime)) ? endDateTime : this.startDateTime.plusHours(1);
        this.repeatableSpec = repeatableSpec;
    }

    public Event(String title, List<Alarm> alarms, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(title, null, alarms);
        this.startDateTime = (startDateTime != null) ? startDateTime : LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
        this.endDateTime = (endDateTime != null && endDateTime.isAfter(startDateTime)) ? endDateTime : this.startDateTime.plusHours(1);
        this.repeatableSpec = repeatableSpec;
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

    public Event cloneEvent(LocalDateTime newBeginDateTime, LocalDateTime newEndDateTime) {
        return new Event(getTitle(), getDescription(), getAlarms(), newBeginDateTime, newEndDateTime);
    }

    public boolean isRepeatable() {
        return repeatableSpec != null;
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
