package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        out.print("Hello and welcome!\n");
        Calendar c;
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        String path = "target/calendar.json";
        File f = new File(path);

        try {
            if(f.exists()) {
                c = om.readValue(f, Calendar.class);
                out.printf("Loading existing calendar from %s.\n", path);
            } else {
                c = new Calendar();
                out.print("Creating new calendar.\n");
            }
            om.writeValue(new File(path), c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}