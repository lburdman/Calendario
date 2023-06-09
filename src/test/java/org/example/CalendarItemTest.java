package org.example;

import org.example.model.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarItemTest {
    @Test
    public void testTaskAddAlarm() {
        LocalDateTime randomDate = LocalDateTime.of(2023, 4, 21, 10, 0);
        CalendarItem task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", randomDate.plusDays(3));
        Alarm alarm = new Notification(randomDate.plusDays(3).minusHours(1));
        task.addAlarm(alarm);
        ArrayList<Alarm> alarms = new ArrayList<>();
        alarms.add(alarm);
        assertEquals(alarms, task.getAlarms());
    }

    @Test
    public void testDeleteAllAlarm() {
        LocalDateTime randomDate = LocalDateTime.of(2023, 4, 21, 10, 0);
        Task task = new Task("Hacer ejercicio", "Hacer 30 minutos de cardio", randomDate.plusDays(3));
        Alarm alarm = new Email(randomDate.plusDays(3).minusHours(1));
        Alarm alarm2 = new Sound(randomDate.plusDays(2).plusHours(3));

        task.addAlarm(alarm);
        task.addAlarm(alarm2);
        task.deleteAllAlarms();
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }

}
