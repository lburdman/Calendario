package org.example.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Sound extends Alarm {
    @Override
    public void trigger() {
        System.out.println("Trigger sound alarm\n");
    }
    public Sound(LocalDateTime activationDate) {
        super(activationDate, AlarmType.SOUND);
    }

    public Sound(Duration relativeInterval) {
        super(relativeInterval, AlarmType.SOUND);
    }
}
