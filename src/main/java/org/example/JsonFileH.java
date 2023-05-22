package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonFileH {

    private ObjectMapper objectMapper;

    public  JsonFileH(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public void save(Map<UUID, Event> events, Map<UUID, Task> tasks, BufferedOutputStream object){
        try{
            objectMapper.writeValue(events, object);
            objectMapper.writeValue(tasks, object);
        } catch (IOException ex) {
            return;
        }
    }

    public Map<UUID, Event> read(BufferedInputStream object){
        Map<UUID, Event> event = new HashMap<>();
        try {
            event = objectMapper.readValue(object, new TypeReference<>());
        } catch (Exception ex){
            event = null;
        }
        return  event;
    }

}
