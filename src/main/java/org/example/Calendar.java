package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<CalendarItem> items;
    final int EMPTY_ID = 0;

    public Calendar(List<CalendarItem> items) {
        this.items = items;
    }

    public Calendar() {
        this.items = new ArrayList<>();
    }

    public List<CalendarItem> getItems() {
        return items;
    }

    public CalendarItem getItems(int id){
        return items.get(id);
    }

    public void setItems(List<CalendarItem> items) {
        this.items = items;
    }

    public void addItem(CalendarItem item) {
        item.setID(items.size());
        items.add(item);
    }

    private boolean isIdNotFound(int id, int itemSize){
        return (id > itemSize || id < EMPTY_ID);
    }

    public boolean modifyItem(int id, CalendarItem item){
        if (isIdNotFound(id,items.size())) return false;
        item.setID(id);
        items.set(id, item);
        return true;
    }

    public void removeItem(CalendarItem item) {
        items.remove(item);
    }

    public List<CalendarItem> listEventsBetween(LocalDate startDate, LocalDate endDate) {
        List<CalendarItem> result = new ArrayList<>();

        for(CalendarItem ci : items) {
            if(ci.isCalendarItemBetween(startDate, endDate)) {
                result.add(ci);
            }
            if(ci instanceof Event event) {
                if (event.getRepeatableSpec() != null) {
                    List<CalendarItem> repeatedItems = event.getRepeatableSpec().listEventRepetitions();
                    for (CalendarItem rci : repeatedItems) {
                        if (rci.isCalendarItemBetween(startDate, endDate)) {
                            result.add(rci);
                        }
                    }
                }
            }
        }
        return result;
    }

    public boolean addAlarmItem(int id, Alarm alarm){
        if (isIdNotFound(id, items.size())) return false;
        items.get(id).addAlarm(alarm);
        return true;
    }

    public void removeAlarmItem(int idItem, int idAlarm){
        items.get(idItem).removeAlarm(idAlarm);
    }


}
