package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {
    private String id;

    private int duration;

    private List<Task> dependents = new ArrayList<>();

    public Task(String id, int duration) {
        this.id = id;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public List<Task> getDependents() {
        return Collections.unmodifiableList(dependents);
    }

    public void addDependents(Task... dependents) {
        Collections.addAll(this.dependents, dependents);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                ", dependents=" + dependents +
                '}';
    }
}
