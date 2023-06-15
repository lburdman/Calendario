package org.example.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MemoryPersistenceHandlerTest {

    @Test
    public void serializeTest() {
        MemoryPersistenceHandler persistence = new MemoryPersistenceHandler();
        Calendar c = new Calendar();
        c.createEvent("Pasear perro", "Salir y pasear al perro", LocalDateTime.of(2023, 6, 15,20, 0), LocalDateTime.of(2023, 6, 15,21, 0));

        assertTrue(persistence.serialize(c));
    }

    @Test
    public void deserializeTest() {
        MemoryPersistenceHandler persistence = new MemoryPersistenceHandler();
        Calendar c = new Calendar();
        Event originalEvent = c.createEvent("Pasear perro", "Salir y pasear al perro", LocalDateTime.of(2023, 6, 15,20, 0), LocalDateTime.of(2023, 6, 15,21, 0));

        persistence.serialize(c);
        Calendar c2 = persistence.deserialize();
        Event loadedEvent = c2.getEvent(originalEvent.getId());

        assertEquals(originalEvent.getStartDateTime(), loadedEvent.getStartDateTime());
        assertEquals(originalEvent.getEndDateTime(), loadedEvent.getEndDateTime());
        assertEquals(originalEvent.getTitle(), loadedEvent.getTitle());
        assertEquals(originalEvent.getDescription(), loadedEvent.getDescription());
    }
}