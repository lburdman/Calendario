package org.example;

import java.time.LocalDateTime;

public class Email extends Alarm {
    @Override
    public void trigger() {
        //sends an Email
    }
    public Email(LocalDateTime activationDate) {
        super(activationDate, AlarmType.EMAIL);
    }

    public Email(Integer relativeInterval) {
        super(relativeInterval, AlarmType.EMAIL);
    }
}
