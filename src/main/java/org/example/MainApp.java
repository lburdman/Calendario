package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.controller.DayController;
import org.example.controller.WeekController;
import org.example.model.Calendar;
import org.example.view.DayView;
import org.example.view.WeekView;

public class MainApp extends Application {
    private BorderPane root;
    private DayView dayView;
    private WeekView weekView;
    private DayController dayController;
    private WeekController weekController;
    final private Integer DAY_WIDTH = 300;

    @Override
    public void start(Stage primaryStage) {
        Calendar calendar = new Calendar();
        root = new BorderPane();

        dayView = new DayView();
        weekView = new WeekView();

        dayController = new DayController(calendar, dayView);
        weekController = new WeekController(calendar, weekView);

        ComboBox<String> viewSelector = new ComboBox<>();
        viewSelector.getItems().addAll("Day view", "Week view", "Month view");
        viewSelector.setValue("Day view");
        HBox topContainer = new HBox();
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().add(viewSelector);

        root.setTop(topContainer);
        root.setCenter(dayView);
        primaryStage.setWidth(DAY_WIDTH);
        primaryStage.setHeight(DAY_WIDTH * 2.2);

        viewSelector.valueProperty().addListener((obs, oldValue, newValue) -> {
            switch (newValue) {
                case "Day view":
                    root.setCenter(dayView);
                    primaryStage.setWidth(DAY_WIDTH);
                    primaryStage.setHeight(DAY_WIDTH * 2.2);
                    break;
                case "Week view":
                    root.setCenter(weekView);
                    primaryStage.setWidth(DAY_WIDTH * 3);
                    primaryStage.setHeight(DAY_WIDTH * 2.5);
                    break;
                case "Month view":

                    break;
            }
        });

        Scene scene = new Scene(root);
        primaryStage.setTitle("Calendar App");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

