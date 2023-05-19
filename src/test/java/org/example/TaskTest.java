package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskConstruct() {
        String title = "Hacer ejercicio";
        String description = "Hacer ejercicio antes del 30 de abril";
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);

        Task task = new Task(title, description, expDate);

        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(expDate, task.getExpDate());
        assertFalse(task.isCompleted());
    }

    @Test
    public void testTaskNoTitleNoDescription() {
        LocalDateTime beginDate = LocalDateTime.of(2023, 6, 23, 18, 0);
        LocalDateTime expDate = beginDate.plusDays(3);
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
        LocalDateTime expDate = LocalDateTime.of(2023, 6, 30, 18, 0);
        Task task = new Task(title, description, expDate);

        task.setAsCompleted();

        assertTrue(task.isCompleted());
    }

    @Test
    public  void testWholeDayTask(){
        String title = "Comprar pan";
        String description = "Ir al super y comprar pan";
        LocalDate wholeDayDate = LocalDate.of(2023, 4, 5);
        WholeDayTask wholeDayTask = new WholeDayTask(title, description, wholeDayDate);

        assertEquals(title, wholeDayTask.getTitle());
        assertEquals(description, wholeDayTask.getDescription());
        assertEquals(wholeDayDate, wholeDayTask.getTaskDate());
        assertEquals(null, wholeDayTask.getExpDate());
        assertFalse(wholeDayTask.isCompleted());
    }
}
