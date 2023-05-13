package org.example;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class WholeDay extends Task{
    private LocalDateTime beginDateTime;

    public WholeDay(String title, String description, LocalDate beginDate){
        super(title, description, null);
        this.beginDateTime = beginDate.atStartOfDay();
        expDate = beginDate.atTime(23,59,59);
    }

    public LocalDateTime getStartDateTime(){
        return beginDateTime;
    }

    public void setStartDateTime(LocalDate startDate) {
        this.beginDateTime = startDate.atStartOfDay();
        expDate = beginDateTime.plusDays(1);
    }
}
