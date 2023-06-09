package org.example.model;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Alarm {
    private LocalDateTime triggerDate; //If it has a certain date where it triggers
    private Duration relativeInterval;
    private AlarmType alarmType;
    private boolean active = true;

    public abstract void trigger(); //an override method that will be implemented in the subclasses

    public Alarm(LocalDateTime triggerDate, AlarmType alarmType) {
        this.triggerDate = triggerDate;
        this.alarmType = alarmType;
    }

    public Alarm(Duration relativeInterval, AlarmType alarmType) {
        this.relativeInterval = relativeInterval;
        this.alarmType = alarmType;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivateAlarm() {
        this.active = false;
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

    public Duration getRelativeInterval() {
        return relativeInterval;
    }

    public void setRelativeInterval(Duration relativeInterval) {
        this.relativeInterval = relativeInterval;
    }

}