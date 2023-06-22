package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.model.Task;

public class TaskRectangleView {
    public static void showDetails(RectangleTask rectangleTask, Pane hourCell, MainController mainController) {
        Task task = rectangleTask.getTask();

        Stage taskWindow = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Text title = new Text("Title: " + task.getTitle());
        Text description = new Text("Description: " + task.getDescription());
        //Text startDateTime = new Text("Start: " + task. event.getStartDateTime().withSecond(0).withNano(0));
        Text endDateTime = new Text("End: " + task.getExpDate());
        CheckBox isComplete = new CheckBox("Is The Task Complete? ");

        isComplete.setSelected(task.getComplete());

        Button deleteButton = new Button("Delete Task");

        layout.getChildren().addAll(title, description, endDateTime, isComplete, deleteButton);

        isComplete.setOnAction(event -> {
                    if (isComplete.isSelected()) {
                        task.setComplete();
                    }
        });

        deleteButton.setOnAction(actionEvent -> {
            mainController.removeTask(task);
            hourCell.getChildren().remove(rectangleTask);

            taskWindow.close();
        });

        Scene scene = new Scene(layout);
        taskWindow.setScene(scene);
        taskWindow.setTitle("Task Details");
        taskWindow.show();
    }
}
