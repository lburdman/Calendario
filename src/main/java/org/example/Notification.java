package org.example;

import java.time.LocalDateTime;

public class Notification extends Alarm {
    @Override
    public void trigger() {
        //makes a notification appear
    }
    public Notification(int id, LocalDateTime activationDate) {
        super(id, activationDate, AlarmType.NOTIFICATION);
    }

    public Notification(int id, Integer relativeInterval) {
        super(id, relativeInterval, AlarmType.NOTIFICATION);
    }
}
