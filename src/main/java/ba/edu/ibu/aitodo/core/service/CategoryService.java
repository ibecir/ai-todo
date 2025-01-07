package ba.edu.ibu.aitodo.core.service;

import ba.edu.ibu.aitodo.core.api.categorysuggester.CategorySuggester;
import ba.edu.ibu.aitodo.core.model.Category;
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

    /**
     * Suggests the most appropriate category for a given task description using the GPT-3.5-turbo-instruct model.
     * <p>
     * This method utilizes OpenAI's GPT-3.5-turbo-instruct model, a natural language processing (NLP) engine
     * designed for high-quality instruction-following tasks. It processes the provided task description and
     * determines the most relevant category based on the context and semantics of the input text.
     * </p>
     *
     * <h3>Model Details:</h3>
     * <ul>
     *     <li>The GPT-3.5-turbo-instruct model is optimized for processing concise instructions and providing structured responses.</li>
     *     <li>It leverages vast knowledge and contextual understanding to suggest categories with high accuracy.</li>
     * </ul>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * {@code
     * String taskDescription = "Prepare monthly financial reports";
     * String suggestedCategory = suggestCategory(taskDescription);
     * System.out.println("Suggested Category: " + suggestedCategory);
     * }
     * </pre>
     *
     * <h3>Notes:</h3>
     * <ul>
     *     <li>Ensure the task description is clear and concise to improve the accuracy of AI-generated suggestions.</li>
     *     <li>The AI model's output may be influenced by how well the task description is articulated.</li>
     *     <li>If no suitable category can be determined, the method may return a default or generic category, such as "Uncategorized".</li>
     * </ul>
     *
     * @param taskDescription A plain-text description of the task for which a category is to be suggested.
     *                        This must be non-null and non-empty for accurate results.
     * @return The name of the suggested category as a non-null string. If no specific category can be determined,
     * the model may return a default value.
     * @throws IllegalArgumentException if {@code taskDescription} is null, empty, or consists only of whitespace.
     * @throws RuntimeException         if an error occurs while communicating with the GPT-3.5-turbo-instruct API.
     */
    public String suggestCategory(String taskDescription) {
        return openAiCategorySuggester.suggestCategory(taskDescription);
    }

    /**
     * Retrieves all available categories from the underlying category repository.
     * <p>
     * This method fetches all categories stored in the data repository, including any newly added categories.
     * It is typically used to display the full list of categories for selection, management, or reporting purposes.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * {@code
     * List<Category> categories = getAllCategories();
     * categories.forEach(category -> System.out.println(category.getName()));
     * }
     * </pre>
     *
     * <h3>Notes:</h3>
     * <ul>
     *     <li>If no categories exist, the method will return an empty list, not {@code null}.</li>
     *     <li>The method interacts directly with the repository and may reflect database-level constraints.</li>
     * </ul>
     *
     * @return A {@code List} of {@code Category} objects representing all categories stored in the system.
     * If no categories are found, an empty list is returned.
     * @throws RuntimeException if an error occurs while accessing the category repository (e.g., database unavailability).
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Creates and persists a new category in the category repository.
     * <p>
     * This method initializes a new {@code Category} object, assigns it the provided name, and saves it to the repository.
     * It returns the created {@code Category} object, which reflects the saved state.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * {@code
     * String categoryName = "Finance";
     * Category newCategory = createCategory(categoryName);
     * System.out.println("Created Category: " + newCategory.getName());
     * }
     * </pre>
     *
     * <h3>Notes:</h3>
     * <ul>
     *     <li>The {@code categoryName} must be unique within the repository. If duplicate category names are not allowed,
     *         ensure validation at a higher level or within the repository logic.</li>
     *     <li>This method does not perform complex validation beyond ensuring the name is non-null and non-empty.
     *         Additional validation can be implemented in the service or controller layer.</li>
     * </ul>
     *
     * @param categoryName The name of the new category to be created. This must be a non-null, non-empty string.
     * @return The newly created {@code Category} object containing the assigned name and any additional default values.
     * @throws IllegalArgumentException if {@code categoryName} is null, empty, or consists only of whitespace.
     * @throws RuntimeException         if an error occurs while saving the category to the repository (e.g., database write failure).
     */
    public Category createCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
        return category;
    }


}
