module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens org.example to javafx.fxml, com.fasterxml.jackson.databind;
    opens org.example.controller to javafx.fxml;
    exports org.example;
}
/*
module org {

        opens org to javafx.fxml, com.fasterxml.jackson.databind;
        opens org.controllers to javafx.fxml;
        exports org.models.calendar.appointment to com.fasterxml.jackson.databind;
        exports org.models.calendar.task to com.fasterxml.jackson.databind;
        exports org.models.calendar.event to com.fasterxml.jackson.databind;
        exports org.models.calendar.alarms to com.fasterxml.jackson.databind;
        exports org.models.calendar.event.frequency to com.fasterxml.jackson.databind;
        exports org;

        opens org.models.calendar.appointment to com.fasterxml.jackson.databind;
        opens org.models.calendar.task to com.fasterxml.jackson.databind;
        opens org.models.calendar.event to com.fasterxml.jackson.databind;
        opens org.models.calendar.alarms to com.fasterxml.jackson.databind;
        opens org.models.calendar.event.frequency to com.fasterxml.jackson.databind;
        }
*/