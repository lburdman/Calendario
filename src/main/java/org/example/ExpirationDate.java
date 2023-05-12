package org.example;

import java.time.LocalDateTime;

public class ExpirationDate extends Task{

    public ExpirationDate(String title, String description, LocalDateTime expDate){
        super(title, description, expDate);
    }


    public void setExpDate(LocalDateTime expDate) {
        this.expDate = expDate;
    }
}
