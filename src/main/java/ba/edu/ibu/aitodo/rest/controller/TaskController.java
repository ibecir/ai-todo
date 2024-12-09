package ba.edu.ibu.aitodo.rest.controller;

import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.service.TaskService;
import ba.edu.ibu.aitodo.rest.dto.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/email/{email}")
    public ResponseEntity<List<Task>> getTasksByUserEmail(@PathVariable String email) {
        return ResponseEntity.ok(taskService.getTasksByUserEmail(email));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{email}")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO task, @PathVariable String email) {
        return ResponseEntity.ok(taskService.createTask(task, email));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDetails));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}