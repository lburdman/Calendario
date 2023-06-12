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

public class EventDialog {

    public EventDialog() {
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
        DatePicker endDatePicker = new DatePicker();
        LocalTimePicker startTimePicker = new LocalTimePicker();
        LocalTimePicker endTimePicker = new LocalTimePicker();

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Start Date:"), 0, 2);
        grid.add(startDatePicker, 1, 2);
        grid.add(new Label("Start Time:"), 0, 4);
        grid.add(startTimePicker, 1, 4);
        grid.add(new Label("End Date:"), 0, 3);
        grid.add(endDatePicker, 1, 3);
        grid.add(new Label("End Time:"), 0, 5);
        grid.add(endTimePicker, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                LocalDate startDate = startDatePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();
                LocalTime startTime = startTimePicker.getLocalTime();
                LocalTime endTime = endTimePicker.getLocalTime();
                String title = titleField.getText();
                String description = descriptionField.getText();

                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

                Map<String, Object> result = new HashMap<>();
                result.put("title", title);
                result.put("description", description);
                result.put("startDateTime", startDateTime);
                result.put("endDateTime", endDateTime);

                return result;
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }
}
