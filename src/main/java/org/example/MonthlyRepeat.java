package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.RepetitionType.MONTHLY;

public class MonthlyRepeat extends RepeatableSpec {
    private final LocalDate endDate;
    private final Integer qtyReps;
    
    public MonthlyRepeat(Event event, LocalDate endDate) {
        super(MONTHLY, event);
        this.endDate = endDate;
        this.qtyReps = null;
    }

    public MonthlyRepeat(Event event, Integer qtyReps) {
        super(MONTHLY, event);
        this.endDate = null;
        this.qtyReps = qtyReps;
    }
    
    public MonthlyRepeat(Event event) {
        super(MONTHLY, event);
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
        LocalDateTime startDateTime = e.getStartDateTime().plusMonths(1);
        LocalDateTime endDateTime = e.getEndDateTime().plusMonths(1);
        List<CalendarItem> result = new ArrayList<>();
        int reps = 1;

        while(reps < qtyReps) {
            result.add(e.cloneEvent(startDateTime, endDateTime));
            startDateTime = startDateTime.plusMonths(1);
            endDateTime = endDateTime.plusMonths(1);
            reps++;
        }
        return result;
    }

    private List<CalendarItem> listRepetitionsForEndDate() {
        Event e = getEvent();
        LocalDateTime startDateTime = e.getStartDateTime().plusMonths(1);
        LocalDateTime endDateTime = e.getEndDateTime().plusMonths(1);
        List<CalendarItem> result = new ArrayList<>();

        while(!startDateTime.toLocalDate().isAfter(endDate)) {
            result.add(e.cloneEvent(startDateTime, endDateTime));
            startDateTime = startDateTime.plusMonths(1);
            endDateTime = endDateTime.plusMonths(1);
        }
        return result;
    }
}
