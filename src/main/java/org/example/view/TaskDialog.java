package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.LocalTimePicker;
import org.example.model.Alarm;
import org.example.model.Calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TaskDialog {
    private final Calendar calendar;
    private final List<Alarm> alarms;

    public TaskDialog(Calendar calendar) {
        this.alarms = new ArrayList<>();
        this.calendar = calendar;
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

        Button alarmButton = new Button("Add Alarm");
        alarmButton.setOnAction(event -> {
            AlarmDialog alarmDialog = new AlarmDialog(calendar);
            alarmDialog.showAlarmDialog();

            Alarm newAlarm = alarmDialog.createAlarm();
            if (newAlarm != null) {
                alarms.add(newAlarm);
            }
        });


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
        grid.add(alarmButton, 0, 5);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                LocalDate endDate = expDatePicker.getValue();
                String title = titleField.getText();
                String description = descriptionField.getText();
                LocalDateTime expDate = (wholeDayField.isSelected()) ? LocalDateTime.of(endDate, LocalTime.MAX) : LocalDateTime.of(endDate, endTimePicker.getLocalTime());

                Map<String, Object> result = new HashMap<>();
                result.put("title", title);
                result.put("description", description);
                result.put("expDate", expDate);
                result.put("alarms", alarms);

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
