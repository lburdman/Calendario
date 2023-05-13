package org.example;

import java.time.LocalDateTime;

public class Sound extends Alarm {
    @Override
    public void trigger() {
        //makes a sound
    }
    public Sound(Integer id, LocalDateTime activationDate) {
        super(id, activationDate, AlarmType.SOUND);
    }

    public Sound(Integer id, Integer relativeInterval) {
        super(id, relativeInterval, AlarmType.SOUND);
    }
}
