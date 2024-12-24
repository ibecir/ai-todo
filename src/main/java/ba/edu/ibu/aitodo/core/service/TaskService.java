package ba.edu.ibu.aitodo.core.service;

import ba.edu.ibu.aitodo.core.model.Category;
import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.model.User;
import ba.edu.ibu.aitodo.core.repository.CategoryRepository;
import ba.edu.ibu.aitodo.core.repository.TaskRepository;
import ba.edu.ibu.aitodo.core.repository.UserRepository;
import ba.edu.ibu.aitodo.rest.dto.TaskDTO;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final OpenAiService openAiService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, OpenAiService openAiService, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.openAiService = openAiService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getById(Long taskId) {
        return taskRepository.getById(taskId);
    }

    public Task createTask(TaskDTO taskDto, String email) {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setTitle(taskDto.getTitle());
        task.setDueDate(taskDto.getDueDate());

        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            task.setUser(userOptional.get());
        }

        if(taskDto.getCategory() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(taskDto.getCategory().getId());
            if (categoryOptional.isPresent()) {
                task.setCategory(categoryOptional.get());
            }
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setPriority(taskDetails.getPriority());
        task.setStatus(taskDetails.getStatus());
        task.setCategory(taskDetails.getCategory());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByUserEmail(String email) {
        return taskRepository.getAllTasksByUserEmail(email);
    }
}
