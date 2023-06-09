package org.example.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

public abstract class RepeatableSpec {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final LocalDate endDate;
    private final Integer qtyReps;

    public abstract List<Event> getEventRepetitions(Event e);

    public RepeatableSpec(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate endDate, Integer qtyReps) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.endDate = endDate;
        this.qtyReps = qtyReps;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getQtyReps() {
        return qtyReps;
    }
}
