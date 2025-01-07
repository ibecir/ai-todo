package ba.edu.ibu.aitodo.core.service;

import ba.edu.ibu.aitodo.core.api.categorysuggester.CategorySuggester;
import ba.edu.ibu.aitodo.core.model.Category;
import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.repository.CategoryRepository;
import ba.edu.ibu.aitodo.core.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing and interacting with task categories.
 * <p>
 * The {@code CategoryService} acts as the central point for operations related to categories,
 * such as creating new categories, retrieving all existing categories, and suggesting categories
 * for tasks using an AI-powered recommendation engine (GPT-3.5-turbo-instruct model).
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Interacting with the {@code CategoryRepository} to persist and retrieve category data.</li>
 *     <li>Utilizing the {@code CategorySuggester} (based on GPT-3.5-turbo-instruct) to recommend suitable categories for tasks.</li>
 *     <li>Providing task-related services by interacting with the {@code TaskRepository}, if applicable.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@code CategoryRepository}: Manages database interactions for category entities.</li>
 *     <li>{@code TaskRepository}: Handles database interactions for task entities.</li>
 *     <li>{@code CategorySuggester}: Provides AI-powered category suggestions based on task descriptions.</li>
 * </ul>
 *
 * <h2>Thread Safety:</h2>
 * <p>
 * This service is stateless and thread-safe as long as the underlying repositories and suggesters
 * are themselves thread-safe, which is typically the case in a Spring-managed environment.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * {@code
 * @Autowired
 * private CategoryService categoryService;
 *
 * // Suggesting a category
 * String suggestedCategory = categoryService.suggestCategory("Prepare a financial report");
 *
 * // Creating a new category
 * Category newCategory = categoryService.createCategory("Finance");
 *
 * // Retrieving all categories
 * List<Category> categories = categoryService.getAllCategories();
 * }
 * </pre>
 *
 * @see Category
 * @see Task
 * @see CategoryRepository
 * @see TaskRepository
 * @see CategorySuggester
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final CategorySuggester openAiCategorySuggester;

    /**
     * Constructs a new {@code CategoryService} with the specified dependencies.
     *
     * @param categoryRepository      The repository for managing category data.
     * @param taskRepository          The repository for managing task data.
     * @param openAiCategorySuggester The AI-powered suggester for determining task categories.
     */
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
     * <p><strong>Notes:</strong></p>
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
     * <p><strong>Notes:</strong></p>
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
     * <p><strong>Notes:</strong></p>
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
