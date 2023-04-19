package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void testTaskConstruct() {
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 4, 30, 18, 0);

        Task task = new Task(title, description, expDate);

        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(expDate, task.getExpDate());
        assertFalse(task.isCompleted());
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }

    @Test
    public void testTaskNoTitleNoDescription() {
        LocalDateTime expDate = LocalDateTime.now().plusDays(3);
        Task task = new Task("", "", expDate);
        assertEquals("sin titulo", task.getTitle());
        assertEquals("sin descripcion", task.getDescription());
        assertEquals(expDate, task.getExpDate());
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }

    @Test
    public void testTaskConstructorSimple() {
        LocalDateTime expDate = LocalDateTime.now().plusDays(3);
        Task task = new Task(expDate);
        assertEquals("sin titulo", task.getTitle());
        assertEquals("sin descripcion", task.getDescription());
    }

    @Test
    public void testTaskSetAsCompleted() {
        String title = "Comprar pan";
        String description = "Ir al super y comprar pan";
        LocalDateTime expDate = LocalDateTime.of(2023, 4, 30, 18, 0);

        Task task = new Task(title, description, expDate);
        task.setAsCompleted();

        assertTrue(task.isCompleted());
    }

    @Test
    public void testTaskIsExpired() {
        Task expTask = new Task("Comprar pan", "Comprar pan en el super", LocalDateTime.now().plusDays(1));
        expTask.setExpDate(LocalDateTime.now().minusDays(3));
        assertTrue(expTask.isExpired());
    }

    @Test
    public void testTaskAlreadyExpired() {
        assertThrows(RuntimeException.class, () -> {
            new Task("Pasear al perro", "Sacar al perro a pasear a la plaza", LocalDateTime.now().minusDays(1));
        });
    }

    @Test
    public void testAddAlarm() {
        Task task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", LocalDateTime.now().plusDays(3));
        //Alarm alarm = new Alarm(LocalDateTime.now().plusDays(3).minusHours(1), Alarm.AlarmType.NOTIFICACION);
        Alarm alarm = new Alarm(LocalDateTime.now().plusDays(3).minusHours(1), AlarmType.NOTIFICATION);
        task.addAlarm(alarm);
        ArrayList<Alarm> alarms = new ArrayList<>();
        alarms.add(alarm);
        assertEquals(alarms, task.getAlarms());
    }

    @Test
    public void testDeleteAlarm() {
        Task task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", LocalDateTime.now().plusDays(3));
        //Alarm alarm = new Alarm(LocalDateTime.now().plusDays(3).minusHours(1), Alarm.AlarmType.EMAIL);
        //var TypeNotification = Set.of(AlarmType.EMAIL);
        Alarm alarm = new Alarm(LocalDateTime.now().plusDays(3).minusHours(1), AlarmType.EMAIL);
        task.addAlarm(alarm);
        task.deleteAlarm(alarm);
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }

    @Test
    public void testDeleteAllAlarms() {
        Task task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", LocalDateTime.now().plusDays(3));
        //var TypeNotification1 = Set.of(AlarmType.EMAIL);
        //var TypeNotification2 = Set.of(AlarmType.SOUND);

        //Alarm alarm1 = new Alarm(LocalDateTime.now().plusDays(3).minusHours(1), Alarm.AlarmType.EMAIL);
        //Alarm alarm2 = new Alarm(LocalDateTime.now().plusDays(3).minusMinutes(30), Alarm.AlarmType.SOUND);
        Alarm alarm1 = new Alarm(LocalDateTime.now().plusDays(3).minusHours(1), AlarmType.EMAIL);
        Alarm alarm2 = new Alarm(LocalDateTime.now().plusDays(3).minusMinutes(30), AlarmType.SOUND);
        task.addAlarm(alarm1);
        task.addAlarm(alarm2);
        task.deleteAllAlarms();
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }


}