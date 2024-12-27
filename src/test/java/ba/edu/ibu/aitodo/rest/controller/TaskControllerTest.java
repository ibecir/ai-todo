package ba.edu.ibu.aitodo.rest.controller;

import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.service.TaskService;
import ba.edu.ibu.aitodo.rest.dto.TaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetTaskById() throws Exception {
        // Given
        Long taskId = 1L;
        Task mockTask = new Task(taskId, "Task 1", "Description 1");

        when(taskService.getById(taskId)).thenReturn(mockTask);

        // When & Then
        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    void testGetTasksByUserEmail() throws Exception {
        // Given
        String email = "user@example.com";
        List<Task> mockTasks = Arrays.asList(new Task(1L, "Task 1", "Description 1"), new Task(2L, "Task 2", "Description 2"));

        when(taskService.getTasksByUserEmail(email)).thenReturn(mockTasks);

        // When & Then
        mockMvc.perform(get("/api/tasks/email/{email}", email)).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2)).andExpect(jsonPath("$[0].title").value("Task 1")).andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        // Given
        List<Task> mockTasks = Arrays.asList(new Task(1L, "Task 1", "Description 1"), new Task(2L, "Task 2", "Description 2"));

        when(taskService.getAllTasks()).thenReturn(mockTasks);

        // When & Then
        mockMvc.perform(get("/api/tasks")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2)).andExpect(jsonPath("$[0].title").value("Task 1")).andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void testCreateTask() throws Exception {
        // Given
        String email = "user@example.com";
        TaskDTO taskDTO = new TaskDTO("Task 1", "Description 1", "High", "Pending");
        Task mockTask = new Task(1L, taskDTO.getTitle(), taskDTO.getDescription());

        when(taskService.createTask(any(TaskDTO.class), eq(email))).thenReturn(mockTask);

        // When & Then
        mockMvc.perform(post("/api/tasks/{email}", email)
                .content(objectMapper.writeValueAsString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    void testUpdateTask() throws Exception {
        // Given
        Long taskId = 1L;
        Task updatedTaskDetails = new Task(null, "Updated Task", "Updated Description");
        Task updatedTask = new Task(taskId, "Updated Task", "Updated Description");

        when(taskService.updateTask(eq(taskId), any(Task.class))).thenReturn(updatedTask);

        // When & Then
        mockMvc.perform(put("/api/tasks/{id}", taskId).content(objectMapper.writeValueAsString(updatedTaskDetails)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(taskId)).andExpect(jsonPath("$.title").value("Updated Task")).andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void testDeleteTask() throws Exception {
        // Given
        Long taskId = 1L;

        doNothing().when(taskService).deleteTask(taskId);

        // When & Then
        mockMvc.perform(delete("/api/tasks/{id}", taskId)).andExpect(status().isOk());
        verify(taskService, times(1)).deleteTask(taskId);
    }
}
