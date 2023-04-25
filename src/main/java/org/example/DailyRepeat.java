package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.RepetitionType.DAILY;

public class DailyRepeat extends RepeatableSpec {
    private Integer interval;
    private final LocalDate endDate;
    private final Integer qtyReps;

    public DailyRepeat(Integer interval, Event event, LocalDate endDate) {
        super(DAILY, event);
        this.interval = (interval != null) ? interval : 1;
        this.endDate = endDate;
        this.qtyReps = null;
    }
    public DailyRepeat(Integer interval, Event event, Integer qtyReps) {
        super(DAILY, event);
        this.interval = (interval != null) ? interval : 1;
        this.endDate = null;
        this.qtyReps = qtyReps;
    }

    public DailyRepeat(Integer interval, Event event) {
        super(DAILY, event);
        this.interval = (interval != null) ? interval : 1;
        this.endDate = null;
        this.qtyReps = null;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
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
        LocalDateTime startDateTime = e.getStartDateTime().plusDays(interval);
        LocalDateTime endDateTime = e.getEndDateTime().plusDays(interval);
        List<CalendarItem> result = new ArrayList<>();
        int reps = 1;

        while(reps < qtyReps) {
            result.add(e.cloneEvent(startDateTime, endDateTime));
            startDateTime = startDateTime.plusDays(interval);
            endDateTime = endDateTime.plusDays(interval);
            reps++;
        }
        return result;
    }

    private List<CalendarItem> listRepetitionsForEndDate() {
        Event e = getEvent();
        LocalDateTime startDateTime = e.getStartDateTime().plusDays(interval);
        LocalDateTime endDateTime = e.getEndDateTime().plusDays(interval);
        List<CalendarItem> result = new ArrayList<>();

        while(!startDateTime.toLocalDate().isAfter(endDate)) {
            result.add(e.cloneEvent(startDateTime, endDateTime));
            startDateTime = startDateTime.plusDays(interval);
            endDateTime = endDateTime.plusDays(interval);
        }
        return result;
    }
}
