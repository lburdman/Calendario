package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    
    @Test
    public void testTaskConstruct() {
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 4, 30, 18, 0);

        Task task = new Task(title, description, expDate);

        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(expDate, task.getExpDate());
        assertFalse(task.isCompleted());
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }
    
    @Test
    public void testTaskNoTitleNoDescription() {
        LocalDateTime expDate = LocalDateTime.now().plusDays(3);
        Task task = new Task(expDate);

        assertEquals("no title", task.getTitle());
        assertEquals("no description", task.getDescription());
        assertEquals(expDate, task.getExpDate());
        assertEquals(new ArrayList<Alarm>(), task.getAlarms());
    }
    
    @Test
    public void testTaskSetAsCompleted() {
        String title = "Comprar pan";
        String description = "Ir al super y comprar pan";
        LocalDateTime expDate = LocalDateTime.of(2023, 4, 30, 18, 0);
        Task task = new Task(title, description, expDate);

        task.setAsCompleted();

        assertTrue(task.isCompleted());
    }

    @Test
    public void testTaskIsExpired() {
        Task expTask = new Task("Comprar pan", "Comprar pan en el super", LocalDateTime.now().plusDays(1));
        expTask.setExpDate(LocalDateTime.now().minusDays(3));

        assertTrue(expTask.isExpired());
    }
    
    @Test
    public void testTaskAlreadyExpired() {
        assertThrows(RuntimeException.class, () -> {
            new Task("Pasear al perro", "Sacar al perro a pasear a la plaza", LocalDateTime.now().minusDays(1));
        });
    }
}
