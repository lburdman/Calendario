package org.example;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AlarmTest {
    
    @Test
    public void testNewNotificationWithDate() {
        LocalDateTime triggerDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Notification(1, triggerDate);

        assertEquals(alarm.getAlarmType(), AlarmType.NOTIFICATION);
        assertEquals(alarm.getTriggerDate(), triggerDate);
    }
    
    @Test
    public void testNewNotificationWithRelativeInterval() {
        Integer interval = 30;
        Alarm alarm = new Notification(1, interval);

        assertEquals(alarm.getAlarmType(), AlarmType.NOTIFICATION);
        assertEquals(alarm.getTriggerDate(), LocalDateTime.now().withNano(0).plusMinutes(interval));
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
        Integer interval = 30;
        Alarm alarm = new Email(1, interval);

        assertEquals(alarm.getAlarmType(), AlarmType.EMAIL);
        assertEquals(alarm.getTriggerDate(), LocalDateTime.now().withNano(0).plusMinutes(interval));
    }
    
    @Test
    public void testNewSoundWithDate() {
        LocalDateTime triggerDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Alarm alarm = new Sound(1, triggerDate);

        assertEquals(alarm.getAlarmType(), AlarmType.SOUND);
        assertEquals(alarm.getTriggerDate(), triggerDate);
    }
    
    @Test
    public void testNewSoundWithRelativeInterval() {
        Integer interval = 30;
        Alarm alarm = new Sound(1, interval);

        assertEquals(alarm.getAlarmType(), AlarmType.SOUND);
        assertEquals(alarm.getTriggerDate(), LocalDateTime.now().withNano(0).plusMinutes(interval));
    }
}
