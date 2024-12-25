package ba.edu.ibu.aitodo.core.service;

import ba.edu.ibu.aitodo.core.api.categorysuggester.CategorySuggester;
import ba.edu.ibu.aitodo.core.model.Category;
import ba.edu.ibu.aitodo.core.repository.CategoryRepository;
import ba.edu.ibu.aitodo.core.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CategorySuggester openAiCategorySuggester;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        // Initialize mocks and inject them into the service
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuggestCategory() {
        // Given
        String taskDescription = "Buy groceries";
        String suggestedCategory = "Shopping";

        // Mock behavior
        when(openAiCategorySuggester.suggestCategory(taskDescription)).thenReturn(suggestedCategory);

        // When
        String result = categoryService.suggestCategory(taskDescription);

        // Then
        assertEquals(suggestedCategory, result, "The suggested category should match");
        verify(openAiCategorySuggester, times(1)).suggestCategory(taskDescription);
    }

    @Test
    void testGetAllCategories() {
        // Given
        List<Category> mockCategories = Arrays.asList(new Category(1L, "Work"), new Category(2L, "Personal"));

        // Mock behavior
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        // When
        List<Category> categories = categoryService.getAllCategories();

        // Then
        assertEquals(2, categories.size(), "The size of the returned category list should be 2");
        assertEquals("Work", categories.get(0).getName(), "The first category name should be 'Work'");
        assertEquals("Personal", categories.get(1).getName(), "The second category name should be 'Personal'");
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateCategory() {
        // Given
        String categoryName = "Health";
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName(categoryName);

        // Mock behavior
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // When
        Category result = categoryService.createCategory(categoryName);

        // Then
        assertEquals(categoryName, result.getName(), "The created category name should match");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}
