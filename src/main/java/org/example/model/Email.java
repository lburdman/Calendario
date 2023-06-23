package org.example.model;

import java.time.LocalDateTime;

public class Email extends Alarm {
    @Override
    public void trigger() {
        System.out.println("Trigger email alarm\n");
    }
    public Email(LocalDateTime activationDate) {
        super(activationDate, AlarmType.EMAIL);
    }

    public Email(long relativeInterval) {
        super(relativeInterval, AlarmType.EMAIL);
    }
}
