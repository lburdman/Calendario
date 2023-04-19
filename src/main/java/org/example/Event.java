package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {

    private String title;
    private String description;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;
    private Long duration; //Minutes
    //enum Repetition {DIARIO, SEMANAL, MENSUAL, ANUAL, NINGUNA};
    private Repetition repetition;
    private Integer interval; //Si la repeticion es diaria
    //enum WeekDays {LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO}; //Si la repeticion es semanal
    //enum EndRepetition {INF, FECHA, REPETICIONES};
    private WeekDays weekDays;
    private  EndRepetition endRepetition;
    private LocalDateTime endDateRepetition; //Si el fin de repeticion es por fecha
    private Integer qtyRepetitions; //Si el fin de repeticiones es por cantidad de repeticiones
    private ArrayList<Alarm> alarms;


    public Event(String title, String description, LocalDateTime beginDate, LocalDateTime endDate, Repetition repetition) {
        this.title = title.isBlank() ? "No title" : title;
        this.description = description.isBlank() ? "No description" : description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.repetition = repetition;
        this.alarms = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.isBlank() ? "No Title" : title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.isBlank() ? "No Description" : description;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }




}
