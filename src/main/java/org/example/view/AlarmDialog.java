package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.model.Alarm;
import org.example.model.AlarmType;
import org.example.model.Calendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AlarmDialog {
    private final Calendar calendar;
    private long relativeInterval;
    private AlarmType alarmType;

    public AlarmDialog(Calendar calendar) {
        this.calendar = calendar;
    }

    public Map<String, Object> showAlarmDialog() {
        Dialog<Map<String, Object>> dialog = new Dialog<>();
        dialog.setTitle("Add Alarm");
        dialog.setHeaderText("Enter alarm details:");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ChoiceBox<AlarmType> alarmTypeChoiceBox = new ChoiceBox<>();
        alarmTypeChoiceBox.getItems().addAll(AlarmType.NOTIFICATION);
        alarmTypeChoiceBox.setValue(AlarmType.NOTIFICATION);

        Spinner<Integer> durationSpinner = new Spinner<>(0, 600, 15);
        durationSpinner.setEditable(true);

        grid.add(new Label("Alarm Type:"), 0, 0);
        grid.add(alarmTypeChoiceBox, 1, 0);
        grid.add(new Label("Relative Interval (minutes):"), 0, 1);
        grid.add(durationSpinner, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                relativeInterval = durationSpinner.getValue();
                alarmType = alarmTypeChoiceBox.getValue();

                Map<String, Object> result = new HashMap<>();
                result.put("relative interval", relativeInterval);
                result.put("alarm type", alarmType);

                return result;
            }
            return null;
        });

        Optional<Map<String, Object>> result = dialog.showAndWait();
        if (result.isPresent()) {
            showSuccessDialog("Alarm added successfully!"); // Show success dialog
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

    public Alarm createAlarm() {
        if (alarmType == null) {
            return null;
        }
        return calendar.createAlarmWithRelativeInterval(relativeInterval, alarmType);
    }

}
