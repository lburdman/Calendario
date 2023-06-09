package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) throws IOException {
        out.print("Hello and welcome!\n");
        FileHandler fh = new FileHandler();
        Calendar c = fh.getCalendar();
        fh.saveCalendar(c);
    }
}