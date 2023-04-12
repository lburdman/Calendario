package org.example;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    String title;
    String description;
    LocalDateTime beginDate;
    LocalDateTime endDate;
    Long duration; //Minutes
    enum Repetition {DIARIO, SEMANAL, MENSUAL, ANUAL, NINGUNA};
    Integer interval; //Si la repeticion es diaria
    enum WeekDays {LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO}; //Si la repeticion es semanal
    enum EndRepetition {INF, FECHA, REPETICIONES};
    LocalDateTime endDateRepetition; //Si el fin de repeticion es por fecha
    Integer qtyRepetitions; //Si el fin de repeticiones es por cantidad de repeticiones
    List<Alarm> Alarms;





}
