package org.example;

import java.time.LocalDateTime;

public class Alarm {

    /*public enum AlarmType {
        NOTIFICACION,
        SONIDO,
        EMAIL
    }
*/
    private LocalDateTime activationDate; //Si tiene fecha y hora de activacion
    private Long relativeInterval; //En minutos, si es un intervalo relativo
    private AlarmType alarmType;

    public void activate(){};

    public Alarm(LocalDateTime activationDate, AlarmType alarmType) {
        this.activationDate = activationDate;
        this.alarmType = alarmType;
    }

    public Alarm(Long relativeInterval, AlarmType alarmType) {
        this.relativeInterval = relativeInterval;
        this.alarmType = alarmType;
    }

    public LocalDateTime getActivationDate() {

        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {

        this.activationDate = activationDate;
    }

    public Long getRelativeInterval() {

        return relativeInterval;
    }

    public void setRelativeInterval(Long relativeInterval) {

        this.relativeInterval = relativeInterval;
    }

    public AlarmType getNotificationType() {
        return alarmType;
    }

    public void setNotificationType(AlarmType notificationType) {
        this.alarmType = notificationType;
    }
}
