package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeeklyRepeat extends RepeatableSpec {
    private List<DayOfWeek> daysOfWeek;

    public WeeklyRepeat(List<DayOfWeek> daysOfWeek, Event event, LocalDate endDate) {
        super(event.getStartDateTime(), event.getEndDateTime(), endDate, null);
        this.daysOfWeek = daysOfWeek;
    }
    
    public WeeklyRepeat(List<DayOfWeek> daysOfWeek, Event event, Integer qtyReps) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, qtyReps);
        this.daysOfWeek = daysOfWeek;
    }

    public WeeklyRepeat(List<DayOfWeek> daysOfWeek, Event event) {
        super(event.getStartDateTime(), event.getEndDateTime(), null, null);
        this.daysOfWeek = daysOfWeek;
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DayOfWeek> dayOfWeek) { 
        this.daysOfWeek = dayOfWeek; 
    }

    @Override
    public List<Event> getEventRepetitions(Event e) {
        List<Event> result = new ArrayList<>();
        RepeatableSpec rs = e.getRepeatableSpec();

        if(rs.getEndDate() != null) {
            result = listWeeklyRepetitionsForEndDate(e, rs.getEndDate());
        } else if(rs.getQtyReps() != null) {
            result = listWeeklyRepetitionsForQty(e, rs.getQtyReps());
        }
        return result;
    }

    private List<Event> listWeeklyRepetitionsForQty(Event e, Integer qtyReps) {
        LocalDate nextDate = e.getStartDateTime().toLocalDate().plusDays(1);
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();
        int count = 0;
        int repsCount = 1;

        while(repsCount < qtyReps) {
            count++;
            if(this.daysOfWeek.contains(nextDate.getDayOfWeek())) {
                Event clonedEvent = e.clone(startDateTime.plusDays(count), endDateTime.plusDays(count));
                result.add(clonedEvent);
                repsCount++;
            }
            nextDate = nextDate.plusDays(1);
        }
        return result;
    }

    private List<Event> listWeeklyRepetitionsForEndDate(Event e, LocalDate endDate) {
        LocalDate nextDate = e.getStartDateTime().toLocalDate().plusDays(1);
        LocalDateTime startDateTime = e.getStartDateTime();
        LocalDateTime endDateTime = e.getEndDateTime();
        List<Event> result = new ArrayList<>();
        int count = 0;

        while(!nextDate.isAfter(endDate)) {
            count++;
            if(this.daysOfWeek.contains(nextDate.getDayOfWeek())) {
                Event clonedEvent = e.clone(startDateTime.plusDays(count), startDateTime.plusDays(count));
                result.add(clonedEvent);
            }
            nextDate = nextDate.plusDays(1);
        }
        return result;
    }

    /*@Override
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
    }*/

}
