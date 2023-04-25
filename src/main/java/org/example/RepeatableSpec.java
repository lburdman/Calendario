package org.example;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

public abstract class RepeatableSpec {
    private RepetitionType repetitionType;
    private Event event;

    public RepeatableSpec(RepetitionType repetitionType, Event event) {
        this.repetitionType = repetitionType;
        this.event = event;
    }

    public RepetitionType getRepetitionType() {
        return repetitionType;
    }

    public void setRepetitionType(RepetitionType repetitionType) {
        this.repetitionType = repetitionType;
    }

    public Event getEvent() {
        return event;
    }

    public abstract List<CalendarItem> listEventRepetitions();

}
