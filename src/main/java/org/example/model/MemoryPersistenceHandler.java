package org.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
public class MemoryPersistenceHandler implements PersistenceHandler {
    private final ObjectMapper objectMapper;
    private final StringBuilder serializedData;
    public MemoryPersistenceHandler() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.serializedData = new StringBuilder();
    }
    @Override
    public boolean serialize(Calendar calendar) {
        try {
            String jsonString = objectMapper.writeValueAsString(calendar);
            serializedData.append(jsonString);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Calendar deserialize() {
        try {
            return objectMapper.readValue(serializedData.toString(), Calendar.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
