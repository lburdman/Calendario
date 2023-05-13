package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class CalendarItem {
    private int id;
    private String title;
    private String description;
    private List<Alarm> alarms;


    public CalendarItem(String title, String description) {
        this.title = (title != null) ?  title : "no title";
        this.description = (description != null) ? description : "no description";
        this.alarms = (alarms != null) ? alarms : new ArrayList<>();
    }

    public abstract boolean isCalendarItemBetween(LocalDate startDate, LocalDate endDate);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.isBlank() ? "no title" : title;
    }

    public void setID(int id){ this.id = id; }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.isBlank() ? "no description" : description;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    public void removeAlarm(int idAlarm) {

        for (int i = 0; i < alarms.size(); i++){
            if (alarms.get(i).getId() == idAlarm)   alarms.remove(i);
        }
    }

    public void deleteAllAlarms() {
        this.alarms.clear();
    }
}
