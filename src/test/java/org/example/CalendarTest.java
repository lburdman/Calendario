package org.example;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarTest {
    
    @Test
    public void testCalendarWithItems() {
        List<CalendarItem> calendarItems = new ArrayList<>();
        CalendarItem task = new Task(LocalDateTime.of(2023, 8, 11, 10,0));
        calendarItems.add(task);
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        calendarItems.add(e);

        Calendar calendar = new Calendar(calendarItems);

        assertEquals(calendarItems, calendar.getItems());
    }

    @Test
    public void testCalendarNoItems() {
        List<CalendarItem> emptyItems = new ArrayList<>();
        Calendar emptyCalendar = new Calendar();

        assertEquals(emptyItems, emptyCalendar.getItems());
    }

    @Test
    public void testAddItem() {
        Calendar emptyCalendar = new Calendar();
        CalendarItem task = new Task(LocalDateTime.of(2023, 6, 22, 12, 0));
        List<CalendarItem> onlyTask = new ArrayList<>();
        onlyTask.add(task);
        emptyCalendar.addItem(task);

        assertEquals(onlyTask, emptyCalendar.getItems());
    }

    @Test
    public void testRemoveItems() {
        //CalendarItem task = new Task(LocalDateTime.now().plusDays(6));
        CalendarItem task = new Task(LocalDateTime.of(2023, 6, 2, 15, 0));
        List<CalendarItem> onlyTask = new ArrayList<>();
        Calendar calendar = new Calendar(onlyTask);
        calendar.removeItem(task);

        assertTrue(calendar.getItems().isEmpty());
    }

    @Test
    public void testModifyCalendar() {
        List<CalendarItem> calendarItems = new ArrayList<>();
        CalendarItem task = new Task(LocalDateTime.of(2023, 5, 30, 13, 0));
        calendarItems.add(task);
        Calendar calendar = new Calendar(calendarItems);

        assertEquals(calendarItems, calendar.getItems());

        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        calendarItems.clear();
        calendarItems.add(e);

        calendar.setItems(calendarItems);

        assertEquals(calendarItems, calendar.getItems());
    }

    @Test
    public void testAddWholeDayTask() {
        Calendar calendar = new Calendar();
        WholeDay wholeDay = new WholeDay("Estudiar Algoritmos","Estudiar polimorfismo", LocalDate.of(2023, 6, 3));
        calendar.addItem(wholeDay);
        var wholeDayTask = calendar.getItems(0);
        assertEquals(wholeDay, wholeDayTask);
    }

    @Test
    public void testAddExpirationDateTask() {
        Calendar calendar = new Calendar();
        ExpirationDate expirationDate = new ExpirationDate("Estudiar Algoritmos","Estudiar polimorfismo", LocalDateTime.of(2023, 6, 3,11, 0));
        calendar.addItem(expirationDate);
        var expirationDateTask = calendar.getItems(0);
        assertEquals(expirationDate, expirationDateTask);
    }

    @Test
    public void testAddAlarm(){

        Calendar calendar = new Calendar();
        WholeDay wholeDay = new WholeDay("Estudiar Algoritmos","Estudiar polimorfismo", LocalDate.of(2023, 6, 3));
        calendar.addItem(wholeDay);

        Alarm notification = new Notification(0, LocalDateTime.of(2023, 6, 3, 16, 0));

        assertTrue(calendar.addAlarmItem(0, notification));
        assertFalse(calendar.addAlarmItem(2, notification));

    }

    @Test
    public void testRemoveAlarm(){

        Calendar calendar = new Calendar();
        WholeDay wholeDay = new WholeDay("Estudiar Algoritmos","Estudiar polimorfismo", LocalDate.of(2023, 6, 3));
        calendar.addItem(wholeDay);

        Alarm notification = new Notification(0, LocalDateTime.of(2023, 6, 3, 16, 0));

        assertTrue(calendar.addAlarmItem(0, notification));
        assertFalse(calendar.addAlarmItem(2, notification));



    }
}
