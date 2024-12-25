package ba.edu.ibu.aitodo.core.service;

import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.model.User;
import ba.edu.ibu.aitodo.core.repository.CategoryRepository;
import ba.edu.ibu.aitodo.core.repository.TaskRepository;
import ba.edu.ibu.aitodo.core.repository.UserRepository;
import ba.edu.ibu.aitodo.rest.dto.TaskDTO;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private OpenAiService openAiService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        // Given
        List<Task> mockTasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1"),
                new Task(2L, "Task 2", "Description 2")
        );

        when(taskRepository.findAll()).thenReturn(mockTasks);

        // When
        List<Task> result = taskService.getAllTasks();

        // Then
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        // Given
        Long taskId = 1L;
        Task mockTask = new Task(taskId, "Task 1", "Description 1");

        when(taskRepository.getById(taskId)).thenReturn(mockTask);

        // When
        Task result = taskService.getById(taskId);

        // Then
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).getById(taskId);
    }

    @Test
    void testCreateTask() {
        // Given
        String email = "user@example.com";
        TaskDTO taskDTO = new TaskDTO("Task 1", "Description 1", "High", "Pending");
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setId(1L);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(mockUser));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Task result = taskService.createTask(taskDTO, email);

        // Then
        assertEquals("Task 1", result.getTitle());
        assertEquals(email, result.getUser().getEmail());
        verify(userRepository, times(1)).findUserByEmail(email);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask() {
        // Given
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Old Task", "Old Description");
        Task updatedTaskDetails = new Task(null, "Updated Task", "Updated Description");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Task result = taskService.updateTask(taskId, updatedTaskDetails);

        // Then
        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void testDeleteTask() {
        // Given
        Long taskId = 1L;

        // When
        taskService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void testGetTasksByUserEmail() {
        // Given
        String email = "user@example.com";
        List<Task> mockTasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1"),
                new Task(2L, "Task 2", "Description 2")
        );

        when(taskRepository.getAllTasksByUserEmail(email)).thenReturn(mockTasks);

        // When
        List<Task> result = taskService.getTasksByUserEmail(email);

        // Then
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).getAllTasksByUserEmail(email);
    }
}
