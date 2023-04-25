package org.example;

import java.time.LocalDateTime;

public class Sound extends Alarm {
    @Override
    public void trigger() {
        //makes a sound
    }
    public Sound(LocalDateTime activationDate) {
        super(activationDate, AlarmType.SOUND);
    }

    public Sound(Integer relativeInterval) {
        super(relativeInterval, AlarmType.SOUND);
    }
}
