package org.example;

import java.time.LocalDateTime;

public class Email extends Alarm {
    @Override
    public void trigger() {
        //sends an Email
    }
    public Email(int id, LocalDateTime activationDate) {
        super(id, activationDate, AlarmType.EMAIL);
    }

    public Email(int id, Integer relativeInterval) {
        super(id, relativeInterval, AlarmType.EMAIL);
    }
}
