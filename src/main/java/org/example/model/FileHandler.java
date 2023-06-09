package org.example.model;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

import static java.lang.System.out;

public class FileHandler {
    private ObjectMapper om;
    private String path;
    private File f;

    public FileHandler() throws StreamReadException, IOException, DatabindException {
        this.om =  new ObjectMapper().registerModule(new JavaTimeModule());
        this.path = "target/calendar.json";
        this.f = new File(path);
    }

    public Calendar getCalendar() {
        Calendar c;
        try {
            if (f.exists()) {
                c = om.readValue(f, Calendar.class);
                out.printf("Loading existing calendar from %s.\n", path);
            } else {
                c = new Calendar();
                out.print("Creating new calendar.\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    public void saveCalendar(Calendar c) throws IOException {
        om.writeValue(new File(path), c);
    }
}
