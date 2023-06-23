package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.view.MainView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp extends Application {
    private ScheduledExecutorService scheduler;

    @Override
    public void start(Stage primaryStage) {
        MainController mainController = new MainController(primaryStage);
        MainView mainView = new MainView(mainController);
        mainController.setMainView(mainView);
        mainController.initialize();

        Scene scene = new Scene(mainView);

        primaryStage.setTitle("Calendar App");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        fireAlarmChecker(mainController);
    }

    private void fireAlarmChecker(MainController mc) {
        scheduler = Executors.newScheduledThreadPool(1);

        Runnable alarm = () -> {
            try {
                Platform.runLater(mc::checkForAlarms);
            } catch (Throwable t) {
                System.out.println(t);
            }
        };

        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        long secondsInMinute = 60;
        long secondsUntilNextMinute = secondsInMinute - (currentTimeInSeconds % secondsInMinute);

        scheduler.scheduleAtFixedRate(alarm, secondsUntilNextMinute, 60, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

