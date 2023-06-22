package org.example.controller;

import org.example.model.Calendar;
import org.example.model.Task;
import org.example.view.EventDialog;
import org.example.view.TaskDialog;

import java.time.LocalDateTime;
import java.util.Map;

public class TaskController {
    private Calendar calendar;

    public TaskController(Calendar calendar) {
        this.calendar = calendar;
    }

    public void addTask() {
        TaskDialog taskDialog = new TaskDialog();
        Map<String, Object> taskData = taskDialog.displayAndGetTaskData();

        if (taskData != null) {
            String title = (String) taskData.get("title");
            String description = (String) taskData.get("description");
            LocalDateTime expDate = (LocalDateTime) taskData.get("expDate");

            calendar.createTask(title, description, expDate);
        }
    }

}
