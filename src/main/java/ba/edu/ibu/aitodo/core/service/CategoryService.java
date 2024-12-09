package ba.edu.ibu.aitodo.core.service;

import ba.edu.ibu.aitodo.core.api.categorysuggester.CategorySuggester;
import ba.edu.ibu.aitodo.core.model.Category;
import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.repository.CategoryRepository;
import ba.edu.ibu.aitodo.core.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final CategorySuggester openAiCategorySuggester;

    public CategoryService(CategoryRepository categoryRepository, TaskRepository taskRepository, CategorySuggester openAiCategorySuggester) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
        this.openAiCategorySuggester = openAiCategorySuggester;
    }

    public String suggestCategory(String taskDescription) {
        return openAiCategorySuggester.suggestCategory(taskDescription);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category createCategory(String categoryName){
        Category category = new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
        return category;
    }
}
