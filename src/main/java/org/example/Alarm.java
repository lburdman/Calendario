package org.example;

import java.time.LocalDateTime;

public abstract class Alarm {
    private LocalDateTime triggerDate; //If it has a certain date where it triggers
    private AlarmType alarmType;

    public abstract void trigger(); //an override method that will be implemented in the subclasses

    public Alarm(LocalDateTime triggerDate, AlarmType alarmType) {
        this.triggerDate = triggerDate;
        this.alarmType = alarmType;
    }

    public Alarm(Integer relativeInterval, AlarmType alarmType) {
        this.triggerDate = LocalDateTime.now().withNano(0).plusMinutes(relativeInterval);
        this.alarmType = alarmType;
    }

    public LocalDateTime getTriggerDate() {
        return triggerDate;
    }

    public void setTriggerDate(LocalDateTime triggerDate) {
        this.triggerDate = triggerDate;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }
}
