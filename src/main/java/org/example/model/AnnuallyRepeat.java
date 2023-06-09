package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnnuallyRepeat extends RepeatableSpec {
    public AnnuallyRepeat(Event event, LocalDate endDate) {
        super(event.getStartDateTime(), event.getEndDateTime(), endDate, null);
    }
    public AnnuallyRepeat(Event event, Integer qtyReps) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, qtyReps);
    }

    public AnnuallyRepeat(Event event) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, null);
    }

    @Override
    public List<Event> getEventRepetitions(Event e) {
        List<Event> result = new ArrayList<>();
        RepeatableSpec rs = e.getRepeatableSpec();

        if(rs.getEndDate() != null) {
            result = listAnnuallyRepetitionsForEndDate(e, rs.getEndDate());
        } else if(rs.getQtyReps() != null) {
            result = listAnnuallyRepetitionsForQty(e, rs.getQtyReps());
        }
        return result;
    }

    private List<Event> listAnnuallyRepetitionsForQty(Event e, Integer qtyReps) {
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();
        int reps = 1;

        while(reps < qtyReps) {
            Event clonedEvent = e.clone(startDateTime.plusYears(1), endDateTime.plusYears(1));
            result.add(clonedEvent);
            reps++;
        }
        return result;
    }

    private List<Event> listAnnuallyRepetitionsForEndDate(Event e, LocalDate endDate) {
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();

        while(!startDateTime.toLocalDate().isAfter(endDate)) {
            startDateTime = startDateTime.plusYears(1);
            Event clonedEvent = e.clone(startDateTime, endDateTime.plusYears(1));
            result.add(clonedEvent);
        }
        return result;
    }
}
