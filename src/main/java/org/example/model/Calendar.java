package org.example.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Calendar {
    private Map<UUID, Event> events;
    private Map<UUID, Task> tasks;

    public Calendar() {
        this.events = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    public Map<UUID, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<UUID, Event> events) {
        this.events = events;
    }

    public Map<UUID, Task> getTasks() {
        return tasks;
    }

    public void setTasks(Map<UUID, Task> tasks) {
        this.tasks = tasks;
    }

    public Event createEvent(String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Event event = new Event(title, description, startDateTime, endDateTime);
        this.events.put(event.getId(), event);
        return event;
    }

    public Task createTask(String title, String description, LocalDateTime dueDate) {
        Task task = new Task(title, description, dueDate);
        this.tasks.put(task.getId(), task);
        return task;
    }

    public Task createWholeDayTask(String title, String description, LocalDate dueDate) {
        Task wholeDay = new WholeDayTask(title, description, dueDate);
        this.tasks.put(wholeDay.getId(), wholeDay);
        return wholeDay;
    }

    public Event getEvent(UUID id){
        return this.events.get(id);
    }

    public Task getTask(UUID id){
        return this.tasks.get(id);
    }

    public void updateEvent(UUID id, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Event event = this.events.get(id);
        if (event == null) return;
        event.setTitle(title);
        event.setDescription(description);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);
    }

    public void updateTask(UUID id, String title, String description, LocalDateTime dueDate) {
        Task task = this.tasks.get(id);
        if (task == null) return;
        task.setTitle(title);
        task.setDescription(description);
        task.setExpDate(dueDate);
    }

    public boolean removeEvent(UUID id) {
        if (idNotFoundEvent(id)) return false;
        this.events.remove(id);
        return true;
    }

    public boolean removeTask(UUID id) {
        if (idNotFoundTask(id)) return false;
        this.tasks.remove(id);
        return true;
    }

    /*public boolean addAlarmToEvent(UUID eventId, LocalDateTime triggerDate, AlarmType alarmType) {
        Event event = this.events.get(eventId);
        if (event == null) return false;

        Alarm alarm = createAlarm(triggerDate, alarmType);
        if (alarm == null) return false;

        event.addAlarm(alarm);
        return true;
    }

    public boolean addAlarmToTask(UUID taskId, LocalDateTime triggerDate, AlarmType alarmType) {
        Task task = this.tasks.get(taskId);
        if (task == null) return false;

        Alarm alarm = createAlarm(triggerDate, alarmType);
        if (alarm == null) return false;

        task.addAlarm(alarm);
        return true;
    }*/

    public void addAlarmWithDateTime(CalendarItem ci, LocalDateTime triggerDate, AlarmType alarmType) {
        ci.addAlarm(createAlarmWithTriggerDate(triggerDate, alarmType));
    }

    public void addAlarmWithRelativeInterval(CalendarItem ci, long relativeInterval, AlarmType alarmType) {
        ci.addAlarm(createAlarmWithRelativeInterval(relativeInterval, alarmType));
    }

    public Alarm createAlarmWithTriggerDate(LocalDateTime triggerDate, AlarmType alarmType) {
        return switch (alarmType) {
            case NOTIFICATION -> new Notification(triggerDate);
            case SOUND -> new Sound(triggerDate);
            case EMAIL -> new Email(triggerDate);
            default -> throw new IllegalArgumentException();
        };
    }

    public Alarm createAlarmWithRelativeInterval(long relativeInterval, AlarmType alarmType) {
        return switch (alarmType) {
            case NOTIFICATION -> new Notification(relativeInterval);
            case SOUND -> new Sound(relativeInterval);
            case EMAIL -> new Email(relativeInterval);
            default -> throw new IllegalArgumentException();
        };
    }

    public List<Event> listEventsBetween(LocalDate startDate, LocalDate endDate) {
        List<Event> result = new ArrayList<>();

        for(Event event : this.events.values()) {
            if(event.isRepeatable()) {
                List<Event> repeatedEvents = event.getRepeatableSpec().getEventRepetitions(event);
                for(Event repeatedEvent : repeatedEvents) {
                    if(repeatedEvent.isCalendarItemBetween(startDate, endDate)) {
                        result.add(repeatedEvent);
                    }
                }
                if (event.isCalendarItemBetween(startDate, endDate)) {
                    result.add(event);
                }
            }
        }

        return result;
    }

    public List<Task> listTasksBetween(LocalDate startDate, LocalDate endDate) {
        List<Task> result = new ArrayList<>();

        for(Task task : this.tasks.values()) {

            if (task.isCalendarItemBetween(startDate, endDate)) {
                result.add(task);
            }

        }
        return result;
    }

    private boolean idNotFoundEvent(UUID id){
        return (!this.events.containsKey(id));
    }

    private boolean idNotFoundTask(UUID id){
        return (!this.tasks.containsKey(id));
    }


    public void asignAnnuallyRepToEvent(Event event, Integer qtyReps){
        RepeatableSpec rs = new AnnuallyRepeat(event, qtyReps);
        event.setRepeatableSpec(rs);
    }

    public void asignAnnuallyRepToEvent(Event event){
        RepeatableSpec rs = new AnnuallyRepeat(event);
        event.setRepeatableSpec(rs);
    }

    public void asignAnnuallyRepToEvent(Event event, LocalDate endDate){
        RepeatableSpec rs = new AnnuallyRepeat(event, endDate);
        event.setRepeatableSpec(rs);
    }

    public void asignMonthlyRepToEvent(Event event, Integer qtyReps){
        RepeatableSpec rs = new MonthlyRepeat(event, qtyReps);
        event.setRepeatableSpec(rs);
    }

    public void asignMonthlyRepToEvent(Event event){
        RepeatableSpec rs = new MonthlyRepeat(event);
        event.setRepeatableSpec(rs);
    }

    public void asignMonthlyRepToEvent(Event event, LocalDate endDate){
        RepeatableSpec rs = new MonthlyRepeat(event, endDate);
        event.setRepeatableSpec(rs);
    }

    public void asignDailyRepToEvent(Integer interval, Event event, int qtyReps){
        RepeatableSpec rs = new DailyRepeat(interval, event, qtyReps);
        event.setRepeatableSpec(rs);
    }

    public void asignDailyRepToEvent(Integer interval, Event event){
        RepeatableSpec rs = new DailyRepeat(interval, event);
        event.setRepeatableSpec(rs);
    }

    public void asignDailyRepToEvent(Integer interval, Event event, LocalDate endDate){
        RepeatableSpec rs = new DailyRepeat(interval, event, endDate);
        event.setRepeatableSpec(rs);
    }

    public void asignWeeklyRepToEvent(List<DayOfWeek> daysOfWeek, Event event, int qtyReps){
        RepeatableSpec rs = new WeeklyRepeat(daysOfWeek, event, qtyReps);
        event.setRepeatableSpec(rs);
    }

    public void asignWeeklyRepToEvent(List<DayOfWeek> daysOfWeek, Event event){
        RepeatableSpec rs = new WeeklyRepeat(daysOfWeek, event);
        event.setRepeatableSpec(rs);
    }

    public void asignWeeklyRepToEvent(List<DayOfWeek> daysOfWeek, Event event, LocalDate endDate){
        RepeatableSpec rs = new WeeklyRepeat(daysOfWeek, event, endDate);
        event.setRepeatableSpec(rs);
    }

}