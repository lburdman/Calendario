package org.example.view;

import com.fasterxml.jackson.core.JsonParser;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MonthView extends BorderPane {
    private final Label monthLabel;
    private final Button prevMonthButton;
    private final Button nextMonthButton;
    private final GridPane gridPane;

    private static final int NUM_DAYS_IN_WEEK = 7;
    private static final String[] DAYS_OF_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public MonthView() {
        monthLabel = new Label("");
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        setMonthLabel(firstDayOfMonth);

        prevMonthButton = new Button("<");
        nextMonthButton = new Button(">");

        HBox monthNavigation = new HBox(1);
        monthNavigation.setAlignment(Pos.CENTER);
        monthNavigation.getChildren().addAll(prevMonthButton, monthLabel, nextMonthButton);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(50));
        gridPane.setVgap(50);
        gridPane.setHgap(50);

        updateGrid(firstDayOfMonth);

        this.setTop(monthNavigation);
        this.setCenter(gridPane);
    }

    public void setMonthLabel(LocalDate startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL" + " - " + "YYYY");
        String formattedStartDate = startDate.format(formatter);
        monthLabel.setText(formattedStartDate);
    }

    public void updateGrid(LocalDate firstDayOfMonth) {
        gridPane.getChildren().clear();
        gridPane.setMinWidth(100);
        int startColumn = firstDayOfMonth.getDayOfWeek().getValue();

        for (int i = 0; i < NUM_DAYS_IN_WEEK; i++) {
            Label dayLabel = new Label(DAYS_OF_WEEK[i]);
            gridPane.add(dayLabel, i, 0);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
        }

        // Add labels for each day of the month
        for (int day = 1; day <= LocalDate.now().lengthOfMonth(); day++) {
            Label monthLabel = new Label(String.valueOf(day));
            gridPane.add(monthLabel, (day + startColumn - 2) % NUM_DAYS_IN_WEEK, (day + startColumn - 2) / NUM_DAYS_IN_WEEK + 1);
            GridPane.setHalignment(monthLabel, HPos.CENTER);
        }

    }

    public Button getPrevMonthButton() {
        return prevMonthButton;
    }

    public Button getNextMonthButton() {
        return nextMonthButton;
    }


    public Label getMonthLabel(){
        return monthLabel;
    }
}
