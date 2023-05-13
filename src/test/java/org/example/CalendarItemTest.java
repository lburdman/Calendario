package org.example;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarItemTest {

    @Test
    public void testTaskAddAlarm() {
        LocalDateTime randomDate = LocalDateTime.now();
        CalendarItem task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", randomDate.plusDays(3));
        Alarm alarm = new Notification(1, randomDate.plusDays(3).minusHours(1));
        task.addAlarm(alarm);
        ArrayList<Alarm> alarms = new ArrayList<>();
        alarms.add(alarm);
        assertEquals(alarms, task.getAlarms());
    }

    @Test
    public void testDeleteAlarm() {
        Task task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", LocalDateTime.now().plusDays(3));
        Alarm alarm = new Email(1, LocalDateTime.now().plusDays(3).minusHours(1));

        task.addAlarm(alarm);
        task.removeAlarm(1);
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }

    @Test
    public void testDeleteAllAlarms() {
        Task task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", LocalDateTime.now().plusDays(3));
        Alarm alarm1 = new Email(1, LocalDateTime.now().plusDays(3).minusHours(1));
        Alarm alarm2 = new Sound(2, LocalDateTime.now().plusDays(3).minusMinutes(30));
        task.addAlarm(alarm1);
        task.addAlarm(alarm2);
        task.deleteAllAlarms();
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }

    @Test
    public void testModifyAlarm(){
        LocalDateTime randomDate = LocalDateTime.now();
        CalendarItem task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", randomDate.plusDays(3));
        Alarm alarm = new Notification(1, randomDate.plusDays(3).minusHours(1));
        task.addAlarm(alarm);
        ArrayList<Alarm> alarms = new ArrayList<>();
        alarms.add(alarm);
        assertEquals(alarms, task.getAlarms());

        Alarm alarm2 = new Notification(2, randomDate.plusDays(5).minusHours(1).minusMinutes(20));
        alarms.add(alarm2);
        task.setAlarms(alarms);
        assertEquals(alarms, task.getAlarms());
    }
}
