package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.model.Event;

public class EventRectangleView {
    public static void showDetails(RectangleEvent rectangleEvent, Pane hourCell, MainController mainController) {
        Event event = rectangleEvent.getEvent();

        Stage eventWindow = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Text title = new Text("Title: " + event.getTitle());
        Text description = new Text("Description: " + event.getDescription());
        Text startDateTime = new Text("Start: " + event.getStartDateTime().withSecond(0).withNano(0));
        Text endDateTime = new Text("End: " + event.getEndDateTime().withSecond(0).withNano(0));
        Button deleteButton = new Button("Delete Event");

        layout.getChildren().addAll(title, description, startDateTime, endDateTime, deleteButton);

        deleteButton.setOnAction(actionEvent -> {
            mainController.removeEvent(event);
            hourCell.getChildren().remove(rectangleEvent);

            eventWindow.close();
        });

        Scene scene = new Scene(layout);
        eventWindow.setScene(scene);
        eventWindow.setTitle("Event Details");
        eventWindow.show();
    }
}

