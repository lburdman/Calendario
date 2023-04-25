package org.example;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    public void testEventConstructor(){

        String title = "Curso Analisis Matematico";
        String description = "Clases en la facultad";
        LocalDateTime startDateEvent = LocalDateTime.of(2023,4, 30, 9, 0);
        LocalDateTime endDateEvent = LocalDateTime.of(2023, 4, 30, 13, 0);

        Event event = new Event(title, description, null, startDateEvent,endDateEvent);

        assertEquals(title, event.getTitle());
        assertEquals(description, event.getDescription());
        assertEquals(new ArrayList<Alarm>(), event.getAlarms());
        assertEquals(startDateEvent, event.getStartDateTime());
        assertEquals(endDateEvent, event.getEndDateTime());
    }

    @Test
    public void testEventNoDescriptionNoTitle(){
        String title = "Kinesiologia";
        LocalDateTime startDateEvent = LocalDateTime.of(2023,4, 30, 9, 0);
        LocalDateTime endDateEvent = LocalDateTime.of(2023, 4, 30, 13, 0);

        Event event = new Event(title, null, startDateEvent,endDateEvent);

        assertEquals(title, event.getTitle());
        assertEquals("no description", event.getDescription());
        assertEquals(new ArrayList<Alarm>(), event.getAlarms());
        assertEquals(startDateEvent, event.getStartDateTime());
        assertEquals(endDateEvent, event.getEndDateTime());

    }

    @Test
    public void testModifyEvent(){
        String title = "Algoritmos";
        String description = "Clases de Algoritmos III";
        LocalDateTime startDateEvent = LocalDateTime.of(2023,4, 30, 9, 0);
        LocalDateTime endDateEvent = LocalDateTime.of(2023, 4, 30, 13, 0);

        Event event = new Event(title, description, null, startDateEvent,endDateEvent);

        assertEquals(title, event.getTitle());
        assertEquals(description, event.getDescription());
        assertEquals(startDateEvent, event.getStartDateTime());
        assertEquals(endDateEvent, event.getEndDateTime());

        String titleModify = "Algoritmos III";
        String descriptionModify = "Clases de teoria de Algoritmos III";
        LocalDateTime startDateEventModify = LocalDateTime.of(2023,5, 11, 9, 0);
        LocalDateTime endDateEventModify = LocalDateTime.of(2023, 5, 11, 13, 0);

        event.setTitle(titleModify);
        event.setDescription(descriptionModify);
        event.setStartDateTime(startDateEventModify);
        event.setEndDateTime(endDateEventModify);

        assertEquals(titleModify, event.getTitle());
        assertEquals(descriptionModify, event.getDescription());
        assertEquals(startDateEventModify, event.getStartDateTime());
        assertEquals(endDateEventModify, event.getEndDateTime());
    }


    @Test
    public void testIsCalendarItemBetween() {
        LocalDateTime startDateEvent = LocalDateTime.of(2023, 4, 20, 14, 0);
        LocalDateTime endDateEvent = startDateEvent.plusHours(3);
        Event e = new Event(null, null, null, startDateEvent, endDateEvent);
        LocalDate startBetween = LocalDate.of(2023, 4, 19);
        LocalDate endBetween = startBetween.plusDays(3);

        assertTrue(e.isCalendarItemBetween(startBetween, endBetween));
    }

}
