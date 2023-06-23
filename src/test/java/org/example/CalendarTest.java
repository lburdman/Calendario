package org.example;

import org.example.model.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CalendarTest {
    /*
    @Test
    public void testSaveAndLoadCalendar() throws IOException {
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        String path = "target/testCalendar.json";
        File f = new File(path);

        // Create new calendar and add an event
        Calendar c = new Calendar();
        String title = "Test event";
        String description = "This is a test event";
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent(title, description, beginEvent, endEvent);
        String titleTask = "Test task";
        String descriptionTask = "This is a test task";
        Task t = c.createTask(titleTask, descriptionTask, beginEvent);

        // Save to file
        om.writeValue(f, c);

        // Load from file
        Calendar loadedCalendar = om.readValue(f, Calendar.class);

        // Verify that the loaded calendar has the same event
        Event loadedEvent = loadedCalendar.getEvent(e.getId());
        Task loadedTask = loadedCalendar.getTask(t.getId());

        assertNotNull(loadedEvent);
        assertEquals(title, loadedEvent.getTitle());
        assertEquals(description, loadedEvent.getDescription());
        assertEquals(beginEvent, loadedEvent.getStartDateTime());
        assertEquals(endEvent, loadedEvent.getEndDateTime());
        assertEquals(titleTask, loadedTask.getTitle());
        assertEquals(descriptionTask, loadedTask.getDescription());
        assertEquals(beginEvent, loadedTask.getExpDate());
    }

     */



    @Test
    public void testCreateEvent(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());

    }

    @Test
    public void testCreateEventMonthlQtyRep(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        RepeatableSpec repeatableSpec1 = new MonthlyRepeat(e,4);
        e.setRepeatableSpec(repeatableSpec1);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());


    }

    @Test
    public void testCreateTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask("Hacer ejercicio", "Hacer ejercicio antes del 30 de abril", expDate);

        assertEquals(title, t.getTitle());
        assertEquals(description, t.getDescription());
        assertEquals(expDate, t.getExpDate());
        assertFalse(t.getComplete());

    }

    @Test
    public void testModifyEvent(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());

        String newTitle ="Salir a pasear al perro";

        c.updateEvent(e.getId(), newTitle, description, beginEvent, endEvent);

        assertEquals(newTitle, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());

    }

    @Test
    public void testModifyTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask("Hacer ejercicio", "Hacer ejercicio antes del 30 de abril", expDate);

        assertEquals(title, t.getTitle());
        assertEquals(description, t.getDescription());
        assertEquals(expDate, t.getExpDate());
        assertFalse(t.getComplete());

        String newTitle = "Estudiar para el parcial";
        String newDescription = "Estudiar Algoritnmos III";

        c.updateTask(t.getId(), newTitle, newDescription, expDate);

        assertEquals(newTitle, t.getTitle());
        assertEquals(newDescription, t.getDescription());
        assertEquals(expDate, t.getExpDate());
        assertFalse(t.getComplete());

    }

    @Test
    public void testRemoveEvent(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent(title, description, beginEvent, endEvent);

        UUID id = e.getId();
        UUID idRandom = UUID.randomUUID();
        assertTrue(c.removeEvent(id));
        assertFalse(c.removeEvent(idRandom));
    }

    @Test
    public void testRemoveTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask(title, description, expDate);

        UUID id = t.getId();
        UUID idRandom = UUID.randomUUID();
        assertTrue(c.removeTask(id));
        assertFalse(c.removeTask(idRandom));

    }

    @Test
    public void testAddAlarmEvent(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent(title, description, beginEvent, endEvent);

        c.addAlarmWithDateTime(e, LocalDateTime.of(2023, 6, 3, 16, 0), AlarmType.NOTIFICATION);

        List<Alarm> alarms = e.getAlarms();

        assertEquals(c.getEvent(e.getId()).getAlarms(), alarms);
    }

    @Test
    public void testAddAlarmTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask(title, description, expDate);

        c.addAlarmWithDateTime(t, LocalDateTime.of(2023, 6, 3, 16, 0), AlarmType.NOTIFICATION);

        List<Alarm> alarms = t.getAlarms();

        assertEquals(c.getTask(t.getId()).getAlarms(), alarms);
    }

    @Test
    public void testRemoveAlarmTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask(title, description, expDate);

        c.addAlarmWithDateTime(t, LocalDateTime.of(2023, 6, 3, 16, 0), AlarmType.NOTIFICATION);

        List<Alarm> alarms = t.getAlarms();

        assertEquals(c.getTask(t.getId()).getAlarms(), alarms);
    }

    @Test
    public void testCreateEventWithAnnuallyRepeat(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        c.asignAnnuallyRepToEvent(e);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertNull(e.getRepeatableSpec().getEndDate());
    }

    @Test
    public void testCreateEventWithAnnuallyRepeatQty(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        c.asignAnnuallyRepToEvent(e, 3);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertEquals(3, e.getRepeatableSpec().getQtyReps());

    }

    @Test
    public void testCreateEventWithAnnuallyRepeatExpDate(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        LocalDate endDate = beginEvent.toLocalDate().plusYears(4).plusDays(3);
        c.asignAnnuallyRepToEvent(e, endDate);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertEquals(endDate, e.getRepeatableSpec().getEndDate());

    }

    @Test
    public void testCreateEventWithMonthlyRepeat(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        c.asignMonthlyRepToEvent(e);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertNull(e.getRepeatableSpec().getEndDate());
    }

    @Test
    public void testCreateEventWithMonthlyRepeatQty(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        c.asignMonthlyRepToEvent(e, 4);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertEquals(4, e.getRepeatableSpec().getQtyReps());

    }

    @Test
    public void testCreateEventWithMonthlyRepeatExpDate(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        LocalDate endDate = beginEvent.toLocalDate().plusMonths(4).plusDays(3);
        c.asignMonthlyRepToEvent(e, endDate);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertEquals(endDate, e.getRepeatableSpec().getEndDate());

    }

    @Test
    public void testCreateEventWithDailyRepeat(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        Integer interval = 1;
        c.asignDailyRepToEvent(interval, e);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertNull(e.getRepeatableSpec().getEndDate());
    }

    @Test
    public void testCreateEventWithDailyRepeatQty(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        Integer interval = 1;
        c.asignDailyRepToEvent(interval, e, 3);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertEquals(3, e.getRepeatableSpec().getQtyReps());

    }

    @Test
    public void testCreateEventWithDailyRepeatExpDate(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        LocalDate endDate = beginEvent.toLocalDate().plusDays(2); //Until 5
        Integer interval = 1;
        c.asignDailyRepToEvent(interval, e, endDate);

        assertEquals(title, e.getTitle());
        assertEquals(description, e.getDescription());
        assertEquals(beginEvent, e.getStartDateTime());
        assertEquals(endEvent, e.getEndDateTime());
        assertEquals(endDate, e.getRepeatableSpec().getEndDate());
    }
}