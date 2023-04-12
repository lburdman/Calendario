package org.example;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
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
    public void testTaskNoTitle() {
        String description = "Ir al super y comprar pan";
        LocalDateTime expDate = LocalDateTime.of(2023, 4, 30, 18, 0);

        assertThrows(IllegalArgumentException.class, () -> new Task(null, description, expDate));
    }

    @Test
    public void testTaskNoDescription() {
        String title = "Comprar pan";
        LocalDateTime expDate = LocalDateTime.of(2023, 4, 30, 18, 0);

        assertThrows(IllegalArgumentException.class, () -> new Task(title, null, expDate));
    }

    @Test
    public void testTaskNoExpDate() {
        String title = "Comprar pan";
        String description = "Ir al super y comprar pan";

        assertThrows(IllegalArgumentException.class, () -> new Task(title, description, null));
    }

    @Test
    public void testTaskInvalidExpDate() {
        String title = "Comprar pan";
        String description = "Ir al super y comprar pan en el pasado";
        LocalDateTime expDate = LocalDateTime.of(2021, 4, 30, 18, 0);

        assertThrows(IllegalArgumentException.class, () -> new Task(title, description, expDate));
    }

}