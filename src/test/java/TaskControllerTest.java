package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {

    @Test
    void startsWithNoTasks() {
        TaskController controller = new TaskController();

        assertEquals(0, controller.getTasks().size());
    }

    @Test
    void creatingTaskAddsTaskToList() {
        TaskController controller = new TaskController();

        controller.createTask(
                new TaskController.TaskRequest("Finish homework")
        );

        assertEquals(1, controller.getTasks().size());
        assertEquals(
                "Finish homework",
                controller.getTasks().get(0).getTitle()
        );
    }

    @Test
    void clearCompletedRemovesOnlyCompletedTasks() {
        TaskController controller = new TaskController();

        Task task1 = controller.createTask(
                new TaskController.TaskRequest("Homework")
        );

        Task task2 = controller.createTask(
                new TaskController.TaskRequest("Docker")
        );

        controller.toggleTask(task1.getId());

        int removed = controller.clearCompletedTasks();

        assertEquals(1, removed);
        assertEquals(1, controller.getTasks().size());
        assertEquals(task2.getId(),
                controller.getTasks().get(0).getId());
    }

    @Test
    void toggleTaskMarksItCompleted() {
        TaskController controller = new TaskController();

        Task task = controller.createTask(
                new TaskController.TaskRequest("Study CI")
        );

        assertFalse(task.isCompleted());

        controller.toggleTask(task.getId());

        assertTrue(task.isCompleted());
    }
}
