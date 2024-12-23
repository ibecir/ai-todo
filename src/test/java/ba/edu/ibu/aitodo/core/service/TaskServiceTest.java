package ba.edu.ibu.aitodo.core.service;

import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.repository.TaskRepository;
import ba.edu.ibu.aitodo.rest.dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Sample Task");
        sampleTask.setDescription("Sample Description");
    }

    @Test
    void getTasksByUserEmail_ShouldReturnTasks() {
        String email = "user1@example.com";

        when(taskRepository.findTasksByUser_Email(email)).thenReturn(Collections.singletonList(sampleTask));

        List<Task> tasks = taskService.getTasksByUserEmail(email);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(sampleTask.getTitle(), tasks.get(0).getTitle());

        verify(taskRepository, times(1)).findTasksByUser_Email(email);
    }

    @Test
    void createTask_ShouldSaveTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskDTO dto = new TaskDTO();
        dto.setCategory(sampleTask.getCategory());
        dto.setDescription(sampleTask.getDescription());
        dto.setDueDate(sampleTask.getDueDate());
        dto.setPriority(sampleTask.getPriority());
        dto.setStatus(sampleTask.getStatus());
        dto.setTitle(sampleTask.getTitle());

        Task createdTask = taskService.createTask(dto, "user1@example.com");

        assertNotNull(createdTask);
        assertEquals(sampleTask.getId(), createdTask.getId());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask_ShouldRemoveTask() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
