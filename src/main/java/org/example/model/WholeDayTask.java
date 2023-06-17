package org.example.model;

import java.time.LocalDate;

public class WholeDayTask extends Task {
    private LocalDate taskDate;

    public WholeDayTask(String title, String description, LocalDate taskDate){
        super(title, description, taskDate.atTime(23,59,59));
        this.taskDate = taskDate;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }
}
