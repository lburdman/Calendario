package org.example;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Map;
import java.util.UUID;

public class FileHandler {
    JsonFileH jsonFileH;

    public FileHandler(JsonFileH jsonFileH){
        this.jsonFileH = jsonFileH;
    }

    public void saveCalendar(Map<UUID, Event> events, Map<UUID, Task> tasks, String path){
        try {
            BufferedOutputStream object = new BufferedOutputStream(new FileOutputStream(path));
            jsonFileH.save(events, tasks, object);
            object.close();
        } catch (IOException ex){
            return;
        }
    }

    public Map<UUID, Event> readEvent(String path){
        try {
            BufferedInputStream object = new BufferedInputStream(new FileInputStream(path));
            Map<UUID, Event> event = jsonFileH.read(object);
            object.close();
            return  event;
        } catch (IOException ex){
            return null;
        }
    }

}
