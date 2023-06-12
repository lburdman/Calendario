package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.DayController;
import org.example.model.Calendar;
import org.example.view.DayView;

public class MainApp extends Application {
    private Calendar calendar;

    @Override
    public void start(Stage primaryStage) {
        DayView dayView = new DayView();
        calendar = new Calendar();
        DayController dayController = new DayController(calendar, dayView);

        Scene scene = new Scene(dayView);

        primaryStage.setTitle("Day View");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(250);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

