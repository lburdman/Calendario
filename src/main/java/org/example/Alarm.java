package org.example;

import java.time.LocalDateTime;

public class Alarm {
    LocalDateTime actDate; //Si tiene fecha y hora de activacion
    Long relativeInterval; //En minutos, si es un intervalo relativo
    enum notType {NOTIFICACION, SONIDO, EMAIL};

}
