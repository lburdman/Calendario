package org.example;

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
        Integer interval = 30;
        Alarm alarm = new Notification(interval);

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
        Alarm alarm = new Email(interval);

        assertEquals(alarm.getAlarmType(), AlarmType.EMAIL);
        assertEquals(alarm.getTriggerDate(), LocalDateTime.now().withNano(0).plusMinutes(interval));
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
        Integer interval = 30;
        Alarm alarm = new Sound(interval);

        assertEquals(alarm.getAlarmType(), AlarmType.SOUND);
        assertEquals(alarm.getTriggerDate(), LocalDateTime.now().withNano(0).plusMinutes(interval));
    }
}
