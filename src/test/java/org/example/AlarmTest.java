package org.example;

import org.example.model.*;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class AlarmTest {

    @Test
    public void testNewNotificationWithDate() {
        LocalDateTime triggerDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Notification(triggerDate);

        assertEquals(alarm.getAlarmType(), AlarmType.NOTIFICATION);
        assertEquals(alarm.getTriggerDate(), triggerDate);
    }

    @Test
    public void testNewNotificationWithRelativeInterval() {
        Duration interval = Duration.of(30, ChronoUnit.MINUTES);
        Alarm alarm = new Notification(interval);
        LocalDateTime alarmDate = LocalDateTime.of(2023, 7, 22, 14, 0);

        assertEquals(alarm.getAlarmType(), AlarmType.NOTIFICATION);
        assertEquals(alarm.getRelativeInterval(), interval);
    }

    @Test
    public void testNewEmailWithDate() {
        LocalDateTime triggerDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Email(triggerDate);

        assertEquals(alarm.getAlarmType(), AlarmType.EMAIL);
        assertEquals(alarm.getTriggerDate(), triggerDate);
    }

    @Test
    public void testNewEmailWithRelativeInterval() {
        Duration interval = Duration.of(30, ChronoUnit.MINUTES);
        LocalDateTime alarmDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Email(interval);

        assertEquals(alarm.getAlarmType(), AlarmType.EMAIL);
        assertEquals(alarm.getRelativeInterval(), interval);
    }

    @Test
    public void testNewSoundWithDate() {
        LocalDateTime triggerDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Sound(triggerDate);

        assertEquals(alarm.getAlarmType(), AlarmType.SOUND);
        assertEquals(alarm.getTriggerDate(), triggerDate);
    }

    @Test
    public void testNewSoundWithRelativeInterval() {
        Duration interval = Duration.of(30, ChronoUnit.MINUTES);
        LocalDateTime alarmDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Sound(interval);

        assertEquals(alarm.getAlarmType(), AlarmType.SOUND);
        assertEquals(alarm.getRelativeInterval(), interval);
    }


    @Test
    public void testAlarmDeactivate() {
        LocalDateTime triggerDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Sound(triggerDate);

        assertEquals(alarm.getAlarmType(), AlarmType.SOUND);
        assertEquals(alarm.getTriggerDate(), triggerDate);

        alarm.deactivateAlarm();
        assertFalse(alarm.isActive());
    }
}