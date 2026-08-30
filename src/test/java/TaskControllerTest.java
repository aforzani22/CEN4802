import org.example.TaskController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskControllerTest {

    @Test
    void startsWithTwoExampleTasks() {
        TaskController controller = new TaskController();
        assertEquals(2, controller.getTasks().size());
    }
}

