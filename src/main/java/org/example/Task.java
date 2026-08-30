package org.example;

public class Task {

    private final long id;
    private final String title;
    private boolean completed;

    public Task(long id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggleCompleted() {
        completed = !completed;
    }
}