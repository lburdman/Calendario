package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.view.MainView;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView();
        MainController mainController = new MainController(mainView, primaryStage);

        Scene scene = new Scene(mainView);

        primaryStage.setTitle("Calendar App");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

