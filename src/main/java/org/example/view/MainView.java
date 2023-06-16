package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.example.controller.MainController;

public class MainView extends BorderPane {
    private final DayView dayView;
    private final WeekView weekView;
    private final MonthView monthView;
    private final ComboBox<String> viewSelector;
    private final Button addEventButton;
    private final Button addTaskButton;
    private final MainController mainController;

    public MainView(MainController mainController) {
        this.mainController = mainController;
        dayView = new DayView(this.mainController);
        weekView = new WeekView();
        monthView = new MonthView(this.mainController);

        viewSelector = new ComboBox<>();
        viewSelector.getItems().addAll("Day view", "Week view", "Month view");
        viewSelector.setValue("Day view");
        HBox topContainer = new HBox();
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().add(viewSelector);

        addEventButton = new Button("Add Event");
        addTaskButton = new Button("Add Task");
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(addEventButton, addTaskButton);

        this.setTop(topContainer);
        this.setCenter(dayView);
        this.setBottom(buttonsBox);
    }

    public DayView getDayView() {
        return dayView;
    }

    public WeekView getWeekView() {
        return weekView;
    }

    public MonthView getMonthView() {
        return monthView;
    }

    public ComboBox<String> getViewSelector() {
        return viewSelector;
    }

    public Button getAddEventButton() {
        return addEventButton;
    }

    public Button getAddTaskButton() {
        return addTaskButton;
    }
}

