package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.LocalTimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaskDialog {
    public TaskDialog() {
    }

    public Map<String, Object> displayAndGetTaskData() {
        Dialog<Map<String, Object>> dialog = new Dialog<>();
        dialog.setTitle("Add Task");
        dialog.setHeaderText("Enter task details:");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        TextField descriptionField = new TextField();
        CheckBox wholeDayField = new CheckBox();
        DatePicker expDatePicker = new DatePicker();
        expDatePicker.setValue(LocalDate.now());
        LocalTimePicker endTimePicker = new LocalTimePicker();
        CheckBox alarmField = new CheckBox();

        ComboBox<String>typeAlarm = new ComboBox<>();
        typeAlarm.getItems().addAll("Sound", "Notification", "Email");


        expDatePicker.setDisable(true);
        endTimePicker.setDisable(true);

        expDatePicker.disableProperty().bind(wholeDayField.selectedProperty());
        endTimePicker.disableProperty().bind(wholeDayField.selectedProperty());

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Whole Day Task: "), 0, 2);
        grid.add(wholeDayField, 1,2);
        grid.add(new Label("End Date:"), 0, 3);
        grid.add(expDatePicker, 1, 3);
        grid.add(new Label("End Time:"), 0, 4);
        grid.add(endTimePicker, 1, 4);
        grid.add(new Label ("Alarm: "), 0,5);
        grid.add(alarmField, 1, 5);
        grid.add(new Label("Type alarm:"), 0,6);
        grid.add(typeAlarm,1,6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                LocalDate endDate = expDatePicker.getValue();
                LocalTime endTime = endTimePicker.getLocalTime();
                String title = titleField.getText();
                String description = descriptionField.getText();

                //LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime expDate = LocalDateTime.of(endDate, endTime);

                Map<String, Object> result = new HashMap<>();
                result.put("title", title);
                result.put("description", description);
                result.put("expDate", expDate);

                return result;
            }
            return null;
        });

        Optional<Map<String, Object>> result = dialog.showAndWait();
        if (result.isPresent()) {
            showSuccessDialog("Task added successfully!");
            return result.get();
        }
        return null;
    }

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
