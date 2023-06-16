package org.example.view;

import javafx.scene.shape.Rectangle;
import org.example.model.Event;
import org.example.model.Task;

public class RectangleTask extends Rectangle {
    private Task task;
    public RectangleTask(Task task) {
        this.task = task;
    }
    public Task getTask() {
        return task;
    }

}