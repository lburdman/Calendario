package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.RepetitionType.WEEKLY;

public class WeeklyRepeat extends RepeatableSpec {
    private List<DayOfWeek> daysOfWeek;
    private final LocalDate endDate;
    private final Integer qtyReps;

    public WeeklyRepeat(List<DayOfWeek> daysOfWeek, Event event, LocalDate endDate) {
        super(WEEKLY, event);
        this.daysOfWeek = daysOfWeek;
        this.endDate = endDate;
        this.qtyReps = null;
    }
    
    public WeeklyRepeat(List<DayOfWeek> daysOfWeek, Event event, Integer qtyReps) {
        super(WEEKLY, event);
        this.daysOfWeek = daysOfWeek;
        this.endDate = null;
        this.qtyReps = qtyReps;
    }

    public WeeklyRepeat(List<DayOfWeek> daysOfWeek, Event event) {
        super(WEEKLY, event);
        this.daysOfWeek = daysOfWeek;
        this.endDate = null;
        this.qtyReps = null;
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DayOfWeek> dayOfWeek) { 
        this.daysOfWeek = dayOfWeek; 
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
        LocalDate nextDate = e.getStartDateTime().toLocalDate().plusDays(1);
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<CalendarItem> result = new ArrayList<>();
        int count = 0;
        int repsCount = 1;

        while(repsCount < qtyReps) {
            count++;
            if(daysOfWeek.contains(nextDate.getDayOfWeek())) {
                result.add(e.cloneEvent(startDateTime.plusDays(count), endDateTime.plusDays(count)));
                repsCount++;
            }
            nextDate = nextDate.plusDays(1);
        }
        return result;
    }

    private List<CalendarItem> listRepetitionsForEndDate() {
        Event e = getEvent();
        LocalDate nextDate = e.getStartDateTime().toLocalDate().plusDays(1);
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<CalendarItem> result = new ArrayList<>();
        int count = 0;

        while(!nextDate.isAfter(endDate)) {
            count++;
            if(daysOfWeek.contains(nextDate.getDayOfWeek())) {
                result.add(e.cloneEvent(startDateTime.plusDays(count), endDateTime.plusDays(count)));
            }
            nextDate = nextDate.plusDays(1);
        }
        return result;
    }

}
