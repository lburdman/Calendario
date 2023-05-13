package org.example;

import java.time.LocalDateTime;

public abstract class Alarm {
    private LocalDateTime triggerDate; //If it has a certain date where it triggers
    private AlarmType alarmType;
    protected Integer id;

    public abstract void trigger(); //an override method that will be implemented in the subclasses

    public Alarm(Integer id, LocalDateTime triggerDate, AlarmType alarmType) {
        this.id = id;
        this.triggerDate = triggerDate;
        this.alarmType = alarmType;
    }

    public Alarm(Integer id, Integer relativeInterval, AlarmType alarmType) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }
}
