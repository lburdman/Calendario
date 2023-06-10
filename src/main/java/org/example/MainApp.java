package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.view.DayView;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        DayView dayView = new DayView();
        Scene scene = new Scene(dayView);

        primaryStage.setTitle("Day View");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(200);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

