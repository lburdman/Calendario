package org.example;

import java.time.LocalDateTime;

public class Notification extends Alarm {
    @Override
    public void trigger() {
        //makes a notification appear
    }
    public Notification(LocalDateTime activationDate) {
        super(activationDate, AlarmType.NOTIFICATION);
    }

    public Notification(Integer relativeInterval) {
        super(relativeInterval, AlarmType.NOTIFICATION);
    }
}
