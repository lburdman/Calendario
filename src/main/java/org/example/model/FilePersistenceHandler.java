package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class FilePersistenceHandler implements PersistenceHandler {
    private final ObjectMapper objectMapper;
    private final String path = System.getProperty("user.dir") + "/calendar.json";
    public FilePersistenceHandler() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    @Override
    public boolean serialize(Calendar calendar) {
        File f = new File(path);

        try {
            objectMapper.writeValue(f, calendar);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Calendar deserialize() {
        File f = new File(path);
        Calendar c;

        try {
            if(f.exists()) {
                c = objectMapper.readValue(f, Calendar.class);
            } else {
                c = new Calendar();
            }
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
