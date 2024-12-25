package ba.edu.ibu.aitodo.core.repository;

import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TaskRepositoryUnitTest {

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindTasksByUser_Email() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        Task task1 = new Task();
        task1.setUser(user);
        task1.setDescription("Task 1");

        Task task2 = new Task();
        task2.setUser(user);
        task2.setDescription("Task 2");

        when(taskRepository.findTasksByUser_Email(email)).thenReturn(List.of(task1, task2));

        // When
        List<Task> tasks = taskRepository.findTasksByUser_Email(email);

        // Then
        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getDescription()).isEqualTo("Task 1");
        assertThat(tasks.get(1).getDescription()).isEqualTo("Task 2");
    }

    @Test
    void testGetAllTasksByUserEmail() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        Task task = new Task();
        task.setUser(user);
        task.setDescription("Task 1");

        when(taskRepository.getAllTasksByUserEmail(email)).thenReturn(List.of(task));

        // When
        List<Task> tasks = taskRepository.getAllTasksByUserEmail(email);

        // Then
        assertThat(tasks).hasSize(1);
        assertThat(tasks.getFirst().getUser().getEmail()).isEqualTo(email);
    }
}
