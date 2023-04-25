package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<CalendarItem> items;

    public Calendar(List<CalendarItem> items) {
        this.items = items;
    }

    public Calendar() {
        this.items = new ArrayList<>();
    }

    public List<CalendarItem> getItems() {
        return items;
    }

    public void setItems(List<CalendarItem> items) {
        this.items = items;
    }

    public void addItem(CalendarItem item) {
        items.add(item);
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
}
