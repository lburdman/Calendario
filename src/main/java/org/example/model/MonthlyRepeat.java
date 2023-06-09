package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonthlyRepeat extends RepeatableSpec {
    public MonthlyRepeat(Event event, LocalDate endDate) {
        super(event.getStartDateTime(), event.getEndDateTime(), endDate, null);
    }

    public MonthlyRepeat(Event event, Integer qtyReps) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, qtyReps);
    }
    
    public MonthlyRepeat(Event event) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, null);
    }

    @Override
    public List<Event> getEventRepetitions(Event e) {
        List<Event> result = new ArrayList<>();
        RepeatableSpec rs = e.getRepeatableSpec();

        if(rs.getEndDate() != null) {
            result = listMonthlyRepetitionsForEndDate(e, rs.getEndDate());
        } else if(rs.getQtyReps() != null) {
            result = listMonthlyRepetitionsForQty(e, rs.getQtyReps());
        }
        return result;
    }

    private List<Event> listMonthlyRepetitionsForQty(Event e, Integer qtyReps) {
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();
        int reps = 1;

        while(reps < qtyReps) {
            Event clonedEvent = e.clone(startDateTime.plusMonths(1), endDateTime.plusMonths(1));
            result.add(clonedEvent);
            reps++;
        }
        return result;
    }

    private List<Event> listMonthlyRepetitionsForEndDate(Event e, LocalDate endDate) {
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();

        while(!startDateTime.toLocalDate().isAfter(endDate)) {
            startDateTime = startDateTime.plusMonths(1);
            Event clonedEvent = e.clone(startDateTime, endDateTime.plusMonths(1));
            result.add(clonedEvent);
        }
        return result;
    }
}
