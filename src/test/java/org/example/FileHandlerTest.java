package org.example.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    @Test
    public void testEventJson() throws IOException {
        Calendar c = new Calendar();
        String pathEvents = "src/main/Resources/event.json";
        String title = "Test event";
        String description = "This is a test event";
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        LocalDateTime endEvent = LocalDateTime.of(2023, 6, 5, 15, 0);
        Event e = c.createEvent(title, description, beginEvent, endEvent);

        c.saveEventsToJsonFile(pathEvents, e);
        File fileEvents = new File(pathEvents);
        assertTrue(fileEvents.exists());
        assertTrue(fileEvents.isFile());

        Event loadedEvent = c.loadEventFromJsonFile(pathEvents);

        assertEquals(title, loadedEvent.getTitle());
        assertEquals(description, loadedEvent.getDescription());
        assertEquals(beginEvent, loadedEvent.getStartDateTime());
        assertEquals(endEvent, loadedEvent.getEndDateTime());

    }

    @Test
    public void testTaskJson() throws IOException {
        Calendar c = new Calendar();
        String pathTask = "src/main/Resources/task.json";

        String titleTask = "Test task";
        String descriptionTask = "This is a test task";
        LocalDateTime beginEvent = LocalDateTime.of(2023, 6, 5, 12, 0);
        Task t = c.createTask(titleTask, descriptionTask, beginEvent);

        c.saveTasksToJsonFile(pathTask,t);
        File fileTasks = new File(pathTask);
        assertTrue(fileTasks.exists());
        assertTrue(fileTasks.isFile());

        Task loadedTask = c.loadTaskFromJsonFile(pathTask);

        assertEquals(titleTask, loadedTask.getTitle());
        assertEquals(descriptionTask, loadedTask.getDescription());
        assertEquals(beginEvent, loadedTask.getExpDate());
        assertFalse(loadedTask.getComplete());

    }
}
