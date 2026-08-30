package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public TaskController() {
        tasks.add(new Task(nextId.getAndIncrement(), "Create a new task"));
        tasks.add(new Task(nextId.getAndIncrement(), "Run the Java web application"));
    }

    @GetMapping
    public List<Task> getTasks() {
        return tasks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody TaskRequest request) {
        if (request == null || request.title() == null || request.title().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title is required.");
        }

        String cleanTitle = request.title().trim();
        if (cleanTitle.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title must be 100 characters or fewer.");
        }

        Task task = new Task(nextId.getAndIncrement(), cleanTitle);
        tasks.add(task);
        return task;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@org.springframework.web.bind.annotation.PathVariable long id) {
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found.");
        }
    }

    public record TaskRequest(String title) {
    }
}