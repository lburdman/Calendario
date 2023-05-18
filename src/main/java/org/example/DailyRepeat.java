package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DailyRepeat extends RepeatableSpec {
    private Integer interval;

    public DailyRepeat(Integer interval, Event event, LocalDate endDate) {
        super(event.getStartDateTime(), event.getEndDateTime(), endDate, null);
        this.interval = (interval != null) ? interval : 1;
    }
    public DailyRepeat(Integer interval, Event event, Integer qtyReps) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, qtyReps);
        this.interval = (interval != null) ? interval : 1;
    }

    public DailyRepeat(Integer interval, Event event) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, null);
        this.interval = (interval != null) ? interval : 1;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Override
    public List<Event> getEventRepetitions(Event e) {
        List<Event> result = new ArrayList<>();
        RepeatableSpec rs = e.getRepeatableSpec();

        if(rs.getEndDate() != null) {
            result = listDailyRepetitionsForEndDate(e, rs.getEndDate());
        } else if(rs.getQtyReps() != null) {
            result = listDailyRepetitionsForQty(e, rs.getQtyReps());
        }
        return result;
    }

    private List<Event> listDailyRepetitionsForQty(Event e, Integer qtyReps) {
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();
        int reps = 1;

        while(reps < qtyReps) {
            Event clonedEvent = e.clone(startDateTime.plusDays(this.interval), endDateTime.plusDays(this.interval));
            result.add(clonedEvent);
            reps++;
        }
        return result;
    }

    private List<Event> listDailyRepetitionsForEndDate(Event e, LocalDate endDate) {
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();

        while(!startDateTime.toLocalDate().isAfter(endDate)) {
            startDateTime = startDateTime.plusDays(this.interval);
            Event clonedEvent = e.clone(startDateTime, endDateTime.plusDays(this.interval));
            result.add(clonedEvent);
        }

        return result;
    }
}
