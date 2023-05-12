package org.example;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class WholeDay extends Task{
    private LocalDateTime startDateTime;

    public WholeDay(String title, String description, LocalDate beginDate){
        super(title, description, null);
        this.startDateTime = beginDate.atStartOfDay();
        expDate = startDateTime.plusDays(1);
    }

    public LocalDateTime getStartDateTime(){
        return startDateTime;
    }

    public void setStartDateTime(LocalDate startDate) {
        this.startDateTime = startDate.atStartOfDay();
        expDate = startDateTime.plusDays(1);
    }
}
