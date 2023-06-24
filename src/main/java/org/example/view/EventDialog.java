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

public class EventDialog {
    private final Calendar calendar;
    private final List<Alarm> alarms;

    public EventDialog(Calendar calendar) {
        this.alarms = new ArrayList<>();
        this.calendar = calendar;
    }

    public Map<String, Object> displayAndGetEventData() {

        Dialog<Map<String, Object>> dialog = new Dialog<>();
        dialog.setTitle("Add Event");
        dialog.setHeaderText("Enter event details:");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        TextField descriptionField = new TextField();
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setValue(LocalDate.now());
        LocalTimePicker startTimePicker = new LocalTimePicker();
        LocalTimePicker endTimePicker = new LocalTimePicker();
        CheckBox repetition = new CheckBox();
        Spinner<Integer> numberSpinner = new Spinner<>(0, 50, 0); // Range: 0 to 100, initial value: 0
        numberSpinner.setEditable(true); // Allow manual input
        DatePicker expDatePicker = new DatePicker();
        expDatePicker.setValue(LocalDate.now());
        numberSpinner.setDisable(true);
        expDatePicker.setDisable(true);

        repetition.selectedProperty().addListener((observable, oldValue, newValue) -> {
            numberSpinner.setDisable(!newValue);
            expDatePicker.setDisable(!newValue);
        });

        Button alarmButton = new Button("Add Alarm");
        alarmButton.setOnAction(event -> {
            AlarmDialog alarmDialog = new AlarmDialog(calendar);
            alarmDialog.showAlarmDialog();

            Alarm newAlarm = alarmDialog.createAlarm();
            if (newAlarm != null) {
                alarms.add(newAlarm);
            }
        });


        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Start Date:"), 0, 2);
        grid.add(startDatePicker, 1, 2);
        grid.add(new Label("Start Time:"), 0, 3);
        grid.add(startTimePicker, 1, 3);
        grid.add(new Label("End Date:"), 0, 4);
        grid.add(endDatePicker, 1, 4);
        grid.add(new Label("End Time:"), 0, 5);
        grid.add(endTimePicker, 1, 5);
        grid.add(new Label("Do you want daily Repetitions?"), 0, 6);
        grid.add(repetition,1,6);
        grid.add(new Label("Interval: "), 0,7);
        grid.add(numberSpinner,1,7);
        grid.add(new Label("Expiration:"), 0, 8);
        grid.add(expDatePicker,1,8);
        grid.add(alarmButton, 0, 9);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                LocalDate startDate = startDatePicker.getValue();
                LocalTime startTime = startTimePicker.getLocalTime();
                LocalDate endDate = endDatePicker.getValue();
                LocalTime endTime = endTimePicker.getLocalTime();
                String title = titleField.getText();
                String description = descriptionField.getText();
                Integer selectedNumber = numberSpinner.getValue();
                Boolean isDailyRepetition = repetition.isSelected();
                LocalDate expDate = expDatePicker.getValue();

                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

                Map<String, Object> result = new HashMap<>();
                result.put("title", title);
                result.put("description", description);
                result.put("startDateTime", startDateTime);
                result.put("endDateTime", endDateTime);
                result.put("alarms", alarms);
                result.put("interval", selectedNumber);
                result.put("isDailyRepetition", isDailyRepetition);
                result.put("expDate", expDate);

                return result;
            }
            return null;
        });

        Optional<Map<String, Object>> result = dialog.showAndWait();
        if (result.isPresent()) {
            showSuccessDialog("Event added successfully!"); // Show success dialog
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
