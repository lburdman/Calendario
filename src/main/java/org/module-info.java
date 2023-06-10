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
