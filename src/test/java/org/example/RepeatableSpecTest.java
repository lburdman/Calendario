package org.example;

import org.junit.Test;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RepeatableSpecTest {

    @Test
    public void testNoRepeatable() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        e.setRepeatableSpec(null);

        LocalDate startBetween = beginEvent.toLocalDate().minusDays(1); //2
        LocalDate endBetween = startBetween.plusDays(5); //7

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testDailyRepeatEndDate() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Integer interval = 1;
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        LocalDate endDate = beginEvent.toLocalDate().plusDays(2); //Until 5
        DailyRepeat daily = new DailyRepeat(interval, e, endDate);
        e.setRepeatableSpec(daily);

        LocalDate startBetween = beginEvent.toLocalDate().minusDays(1); //2
        LocalDate endBetween = startBetween.plusDays(5); //7

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(3, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusDays(2));
        assertEquals(3, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusDays(3));
        assertEquals(2, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween.plusDays(1), startBetween.plusDays(1));
        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testDailyRepeatQty() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Integer interval = 1;
        Integer qty = 3;
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        DailyRepeat daily = new DailyRepeat(interval, e, qty);
        e.setRepeatableSpec(daily);

        LocalDate startBetween = beginEvent.toLocalDate().minusDays(1); //2
        LocalDate endBetween = startBetween.plusDays(5); //7

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(3, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusDays(3));
        assertEquals(2, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween.plusDays(2), startBetween.plusDays(2));
        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testDailyRepeatInfinite() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Integer interval = 1;
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        DailyRepeat daily = new DailyRepeat(interval, e);
        e.setRepeatableSpec(daily);

        LocalDate startBetween = beginEvent.toLocalDate().minusDays(1); //2
        LocalDate endBetween = startBetween.plusDays(5); //7

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(1, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusDays(3));
        assertEquals(1, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, startBetween.plusDays(5));
        assertEquals(1, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, startBetween.plusDays(2));
        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testMonthlyRepeatEndDate() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        LocalDate endDate = beginEvent.toLocalDate().plusMonths(4).plusDays(3);
        MonthlyRepeat monthly = new MonthlyRepeat(e, endDate);
        e.setRepeatableSpec(monthly);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusMonths(5);

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(5, calendarItems.size()); // There are 5 events, the original one and 4 copies

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusMonths(2));

        assertEquals(4, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusMonths(2).minusDays(4));

        assertEquals(3, calendarItems.size());
    }

    @Test
    public void testMonthlyRepeatQty() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        Integer reps = 4; //months: 4, 5, 6, 7
        MonthlyRepeat monthly = new MonthlyRepeat(e, reps);
        e.setRepeatableSpec(monthly);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusMonths(5); //Month:9

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(4, calendarItems.size()); // There are 4 events, the original one and 3 copies (quntity of reps)

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusMonths(3));

        assertEquals(3, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusMonths(5).minusDays(4));

        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testMonthlyRepeatInfinit() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        //Integer reps = 4; //months: 4, 5, 6, 7
        MonthlyRepeat monthly = new MonthlyRepeat(e);
        e.setRepeatableSpec(monthly);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusMonths(5); //Month:9

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(1, calendarItems.size()); // There are 4 events, the original one and 3 copies (quantity of reps)

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusMonths(3));

        assertEquals(1, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusMonths(5).minusDays(4));

        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testAnnuallyRepeatEndDate() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        LocalDate endDate = beginEvent.toLocalDate().plusYears(4).plusDays(3); //2027
        AnnuallyRepeat annually = new AnnuallyRepeat(e, endDate);
        e.setRepeatableSpec(annually);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusYears(5); //2028

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(5, calendarItems.size()); // There are 5 events, the original one and 4 copies

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusYears(2)); //2026

        assertEquals(4, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusYears(2).minusDays(4));//2026 no include

        assertEquals(3, calendarItems.size());
    }

    @Test
    public void testAnnuallyRepeatQty() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        Integer reps = 4; //years: 2023, 2024, 2025, 2026
        AnnuallyRepeat annually = new AnnuallyRepeat(e, reps);
        e.setRepeatableSpec(annually);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusYears(5); //2028

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(4, calendarItems.size()); // There are 4 events, the original one and 3 copies (quntity of reps)

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusYears(3)); //2025

        assertEquals(3, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusYears(5).minusDays(4));//2023 no include

        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testAnnuallyInfinite() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        //Integer reps = 4; //years: 2023, 2024, 2025, 2026
        AnnuallyRepeat annually = new AnnuallyRepeat(e);
        e.setRepeatableSpec(annually);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusYears(5); //2028

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems = c.listEventsBetween(startBetween, endBetween);

        assertEquals(1, calendarItems.size()); // There are 4 events, the original one and 3 copies (quntity of reps)

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusYears(3)); //2025

        assertEquals(1, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusYears(5).minusDays(4));//2023 no include

        assertEquals(1, calendarItems.size());
    }

    @Test
    public void testWeeklyRepeatEndDate() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        LocalDate endDate = beginEvent.toLocalDate().plusWeeks(4).plusDays(3); //05/04 (MM/DD)
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        daysOfWeek.add(DayOfWeek.MONDAY);
        daysOfWeek.add(DayOfWeek.WEDNESDAY);
        WeeklyRepeat weekly = new WeeklyRepeat(daysOfWeek, e, endDate);
        e.setRepeatableSpec(weekly);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusWeeks(5); //05/08

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(10, calendarItems.size()); // There are 10 events

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusWeeks(2)); //04/24

        assertEquals(7, calendarItems.size());

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusWeeks(2).minusDays(1));//04/23

        assertEquals(6, calendarItems.size());
    }

    @Test
    public void testWeeklyRepeatQty() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        Integer reps = 4;
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        daysOfWeek.add(DayOfWeek.MONDAY);
        daysOfWeek.add(DayOfWeek.WEDNESDAY);
        WeeklyRepeat weekly = new WeeklyRepeat(daysOfWeek, e, reps);
        e.setRepeatableSpec(weekly);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusWeeks(5); //05/08

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(4, calendarItems.size()); // There are 4 events

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusWeeks(4));//04/10

        assertEquals(3, calendarItems.size());
    }

    @Test
    public void testWeeklyRepeatInfinit() {
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        //Integer reps = 4;
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        daysOfWeek.add(DayOfWeek.MONDAY);
        daysOfWeek.add(DayOfWeek.WEDNESDAY);
        WeeklyRepeat weekly = new WeeklyRepeat(daysOfWeek, e);
        e.setRepeatableSpec(weekly);

        LocalDate startBetween = beginEvent.toLocalDate();
        LocalDate endBetween = startBetween.plusWeeks(5); //05/08

        Calendar c = new Calendar();
        c.addItem(e);
        List<CalendarItem> calendarItems =  c.listEventsBetween(startBetween, endBetween);

        assertEquals(1, calendarItems.size()); // There are 4 events

        calendarItems = c.listEventsBetween(startBetween, endBetween.minusWeeks(4));//04/10

        assertEquals(1, calendarItems.size());
    }
}
