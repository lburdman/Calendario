package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.RepetitionType.ANNUALLY;

public class AnnuallyRepeat extends RepeatableSpec {
    private final LocalDate endDate;
    private final Integer qtyReps;

    public AnnuallyRepeat(Event event, LocalDate endDate) {
        super(ANNUALLY, event);
        this.endDate = endDate;
        this.qtyReps = null;
    }
    public AnnuallyRepeat(Event event, Integer qtyReps) {
        super(ANNUALLY, event);
        this.endDate = null;
        this.qtyReps = qtyReps;
    }

    public AnnuallyRepeat(Event event) {
        super(ANNUALLY, event);
        this.endDate = null;
        this.qtyReps = null;
    }
    @Override
    public List<CalendarItem> listEventRepetitions() {
        List<CalendarItem> result = new ArrayList<>();

        if(endDate != null) {
            result = listRepetitionsForEndDate();
        } else if(qtyReps != null) {
            result = listRepetitionsForQty();
        }
        return result;
    }

    private List<CalendarItem> listRepetitionsForQty() {
        Event e = getEvent();
        LocalDateTime startDateTime = e.getStartDateTime().plusYears(1);
        LocalDateTime endDateTime = e.getEndDateTime().plusYears(1);
        List<CalendarItem> result = new ArrayList<>();
        int reps = 1;

        while(reps < qtyReps) {
            result.add(e.cloneEvent(startDateTime, endDateTime));
            startDateTime = startDateTime.plusYears(1);
            endDateTime = endDateTime.plusYears(1);
            reps++;
        }
        return result;
    }

    private List<CalendarItem> listRepetitionsForEndDate() {
        Event e = getEvent();
        LocalDateTime startDateTime = e.getStartDateTime().plusYears(1);
        LocalDateTime endDateTime = e.getEndDateTime().plusYears(1);
        List<CalendarItem> result = new ArrayList<>();

        while(!startDateTime.toLocalDate().isAfter(endDate)) {
            result.add(e.cloneEvent(startDateTime, endDateTime));
            startDateTime = startDateTime.plusYears(1);
            endDateTime = endDateTime.plusYears(1);
        }
        return result;
    }
}
