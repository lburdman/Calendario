package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class Notification extends Alarm {
    @Override
    public void trigger() {
        System.out.println("Trigger notification alarm\n");
    }
    public Notification(LocalDateTime activationDate) {
        super(activationDate, AlarmType.NOTIFICATION);
    }

    public Notification(Duration relativeInterval) {
        super(relativeInterval, AlarmType.NOTIFICATION);
    }
}
