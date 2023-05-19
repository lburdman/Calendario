package org.example;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CalendarTest {

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
    public void testCreateTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask("Hacer ejercicio", "Hacer ejercicio antes del 30 de abril", expDate);


        assertEquals(title, t.getTitle());
        assertEquals(description, t.getDescription());
        assertEquals(expDate, t.getExpDate());
        assertFalse(t.isCompleted());

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
        assertFalse(t.isCompleted());

        String newTitle = "Estudiar para el parcial";
        String newDescription = "Estudiar Algoritnmos III";

        c.updateTask(t.getId(), newTitle, newDescription, expDate);

        assertEquals(newTitle, t.getTitle());
        assertEquals(newDescription, t.getDescription());
        assertEquals(expDate, t.getExpDate());
        assertFalse(t.isCompleted());

    }

    @Test
    public void testRemoveEvent(){
        String title = "pasear al perro";
        String description = "Salir afuera y pasear al perro";
        Calendar c = new Calendar();
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);

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
        Task t = c.createTask("Hacer ejercicio", "Hacer ejercicio antes del 30 de abril", expDate);

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
        Event e = c.createEvent("pasear al perro", "Salir afuera y pasear al perro", beginEvent, endEvent);
        UUID id = e.getId();

        assertTrue(c.addAlarmToEvent(id, LocalDateTime.of(2023, 6, 3, 16, 0), AlarmType.NOTIFICATION));

    }

    @Test
    public void testAddAlarmTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask("Hacer ejercicio", "Hacer ejercicio antes del 30 de abril", expDate);

        UUID id = t.getId();

        assertTrue(c.addAlarmToTask(id, LocalDateTime.of(2023, 6, 3, 16, 0), AlarmType.NOTIFICATION));
    }

    @Test
    public void testRemoveAlarmTask(){
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Calendar c = new Calendar();
        Task t = c.createTask("Hacer ejercicio", "Hacer ejercicio antes del 30 de abril", expDate);

        UUID id = t.getId();

        assertTrue(c.addAlarmToTask(id, LocalDateTime.of(2023, 6, 3, 16, 0), AlarmType.NOTIFICATION));
    }


}
