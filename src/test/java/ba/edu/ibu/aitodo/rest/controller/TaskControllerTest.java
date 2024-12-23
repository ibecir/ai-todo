package ba.edu.ibu.aitodo.rest.controller;

import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void getTasksByUserEmail_ShouldReturnTasks() throws Exception {
        String email = "user1@example.com";

        // Create a sample task
        Task sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Test Task");
        sampleTask.setDescription("Test Description");
        sampleTask.setPriority("High");
        sampleTask.setStatus("Ongoing");

        // Mock the service method
        when(taskService.getTasksByUserEmail(email)).thenReturn(Collections.singletonList(sampleTask));

        // Perform the GET request
        mockMvc.perform(get("/api/tasks/email/{email}", email)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) // Print the request and response for debugging
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleTask.getId()))
                .andExpect(jsonPath("$[0].title").value(sampleTask.getTitle()))
                .andExpect(jsonPath("$[0].description").value(sampleTask.getDescription()));

        // Verify that the service method was called exactly once
        verify(taskService, times(1)).getTasksByUserEmail(email);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        String email = "user1@example.com";

        Task sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("New Task");
        sampleTask.setDescription("This is a new task");
        sampleTask.setPriority("High");
        sampleTask.setStatus("Pending");

        // Mock the service method
        when(taskService.createTask(any(), eq(email))).thenReturn(sampleTask);

        // Perform the POST request
        mockMvc.perform(post("/api/tasks/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Task\",\"description\":\"This is a new task\",\"priority\":\"High\",\"status\":\"Pending\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleTask.getId()))
                .andExpect(jsonPath("$.title").value(sampleTask.getTitle()))
                .andExpect(jsonPath("$.description").value(sampleTask.getDescription()));

        // Verify the service method was called
        verify(taskService, times(1)).createTask(any(), eq(email));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        Long taskId = 1L;

        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setPriority("Low");
        updatedTask.setStatus("Completed");

        // Mock the service method
        when(taskService.updateTask(eq(taskId), any())).thenReturn(updatedTask);

        // Perform the PUT request
        mockMvc.perform(put("/api/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\",\"description\":\"Updated Description\",\"priority\":\"Low\",\"status\":\"Completed\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedTask.getId()))
                .andExpect(jsonPath("$.title").value(updatedTask.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedTask.getDescription()));

        // Verify the service method was called
        verify(taskService, times(1)).updateTask(eq(taskId), any());
    }

    @Test
    void deleteTask_ShouldReturnOkStatus() throws Exception {
        Long taskId = 1L;

        // Perform the DELETE request
        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andDo(print())
                .andExpect(status().isOk());

        // Verify the service method was called
        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    void getAllTasks_ShouldReturnTaskList() throws Exception {
        Task sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Sample Task");
        sampleTask.setDescription("Sample Description");
        sampleTask.setPriority("Medium");
        sampleTask.setStatus("Ongoing");

        // Mock the service method
        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(sampleTask));

        // Perform the GET request
        mockMvc.perform(get("/api/tasks")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleTask.getId()))
                .andExpect(jsonPath("$[0].title").value(sampleTask.getTitle()))
                .andExpect(jsonPath("$[0].description").value(sampleTask.getDescription()));

        // Verify the service method was called
        verify(taskService, times(1)).getAllTasks();
    }
}