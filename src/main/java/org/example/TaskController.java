package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @GetMapping
    public List<Task> getTasks() {
        return tasks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskRequest request) {

        if (request.title() == null || request.title().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Task title cannot be empty."
            );
        }

        Task task = new Task(
                nextId.getAndIncrement(),
                request.title().trim()
        );

        tasks.add(task);

        return task;
    }

    @PostMapping("/{id}/toggle")
    public Task toggleTask(@PathVariable long id) {

        Task task = tasks.stream()
                .filter(existingTask -> existingTask.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Task not found."
                        )
                );

        task.toggleCompleted();

        return task;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {

        boolean removed = tasks.removeIf(
                task -> task.getId() == id
        );

        if (!removed) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found."
            );
        }
    }

    public record TaskRequest(String title) {
    }
}