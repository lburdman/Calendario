package org.example;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarTest {
    
    @Test
    public void testCalendarWithItems() {
        List<CalendarItem> calendarItems = new ArrayList<>();
        CalendarItem task = new Task(LocalDateTime.now().plusDays(6));
        calendarItems.add(task);
        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", null, beginEvent, endEvent);
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
        CalendarItem task = new Task(LocalDateTime.now().plusDays(6));
        List<CalendarItem> onlyTask = new ArrayList<>();
        onlyTask.add(task);
        emptyCalendar.addItem(task);

        assertEquals(onlyTask, emptyCalendar.getItems());
    }

    @Test
    public void testRemoveItems() {
        CalendarItem task = new Task(LocalDateTime.now().plusDays(6));
        List<CalendarItem> onlyTask = new ArrayList<>();
        Calendar calendar = new Calendar(onlyTask);
        calendar.removeItem(task);

        assertTrue(calendar.getItems().isEmpty());
    }

    @Test
    public void testModifyCalendar() {
        List<CalendarItem> calendarItems = new ArrayList<>();
        CalendarItem task = new Task(LocalDateTime.now().plusDays(6));
        calendarItems.add(task);
        Calendar calendar = new Calendar(calendarItems);

        assertEquals(calendarItems, calendar.getItems());

        LocalDateTime beginEvent = LocalDateTime.of(2023, 4, 3, 18, 0);
        LocalDateTime endEvent = beginEvent.plusHours(3);
        Event e = new Event("Pasear al perro", "Salir afuera y pasear al perro", null, beginEvent, endEvent);
        calendarItems.clear();
        calendarItems.add(e);

        calendar.setItems(calendarItems);

        assertEquals(calendarItems, calendar.getItems());
    }
}
