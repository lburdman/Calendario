package org.example;

import org.example.model.*;
import org.junit.Test;

import java.time.LocalDateTime;

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
        long interval = 30;
        Alarm alarm = new Notification(interval);

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
        long interval = 30;
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
        long interval = 30;
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