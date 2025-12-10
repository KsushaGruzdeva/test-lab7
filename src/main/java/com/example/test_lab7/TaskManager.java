package com.example.test_lab7;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private int nextId = 1;
    
    public Task createTask(String title, String description, Priority priority) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Заголовок задачи не может быть пустым");
        }
        
        Task task = new Task(nextId++, title, description, priority, Status.TODO);
        tasks.put(task.getId(), task);
        return task;
    }
    
    public Task getTask(int id) {
        return tasks.get(id);
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    
    public boolean updateTaskStatus(int id, Status newStatus) {
        Task task = tasks.get(id);
        if (task != null) {
            task.setStatus(newStatus);
            return true;
        }
        return false;
    }
    
    public List<Task> getTasksByPriority(Priority priority) {
        return tasks.values().stream()
            .filter(task -> task.getPriority() == priority)
            .toList();
    }
    
    public int getCompletedTasksCount() {
        return (int) tasks.values().stream()
            .filter(task -> task.getStatus() == Status.DONE)
            .count();
    }
}

enum Priority { LOW, MEDIUM, HIGH }
enum Status { TODO, IN_PROGRESS, DONE }

class Task {
    private final int id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private Date createdAt;
    
    public Task(int id, String title, String description, Priority priority, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = new Date();
    }
    
    // Геттеры и сеттеры
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String desc) { this.description = desc; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority p) { this.priority = p; }
    public Status getStatus() { return status; }
    public void setStatus(Status s) { this.status = s; }
    public Date getCreatedAt() { return createdAt; }
}