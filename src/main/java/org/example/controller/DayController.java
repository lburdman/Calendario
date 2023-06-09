package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.example.model.Calendar;
public class DayController {

    @FXML
    private VBox dayView;

    @FXML
    private Label dayLabel;

    @FXML
    private Button addEventButton;

    @FXML
    private Button addTaskButton;

    public void initialize() {
        for (int i = 0; i < 24; i++) {
            HBox hourBox = new HBox();
            hourBox.setId("hour-" + i);
            Label hourLabel = new Label(String.format("%02d:00", i));
            hourBox.getChildren().add(hourLabel);
            dayView.getChildren().add(hourBox);
        }
    }
}
/*
public class DayController {

    @FXML
    private ListView<String> hourlyEventList;

    @FXML
    private Button addEventButton;

    // Modelo - referencia a los datos (eventos, tareas)
    private Calendar calendar;

    public DayController(Calendar calendar) {
        this.calendar = calendar;
    }

    @FXML
    private void initialize() {
        for (int i = 0; i < 24; i++) {
            hourlyEventList.getItems().add(String.format("%02d:00", i));
        }
    }

    @FXML
    private void addEvent() {
        // Maneja el click en el botón agregar evento
    }

    @FXML
    private void addTask() {
        // Maneja el click en el botón agregar evento
    }
}*/

