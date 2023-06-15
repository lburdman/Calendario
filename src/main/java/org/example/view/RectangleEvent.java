package org.example.view;

import javafx.scene.shape.Rectangle;
import org.example.model.Event;

public class RectangleEvent extends Rectangle {
    private Event event;
    public RectangleEvent(Event event) {
        this.event = event;
    }
    public Event getEvent() {
        return event;
    }

}
