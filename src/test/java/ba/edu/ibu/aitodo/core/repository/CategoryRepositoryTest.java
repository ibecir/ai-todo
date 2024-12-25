package ba.edu.ibu.aitodo.core.repository;

import ba.edu.ibu.aitodo.core.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class CategoryRepositoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindCategoryByName_CategoryExists() {
        // Given
        String categoryName = "Electronics";
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName(categoryName);

        // Mock behavior
        when(categoryRepository.findCategoryByName(categoryName)).thenReturn(mockCategory);

        // When
        Category foundCategory = categoryRepository.findCategoryByName(categoryName);

        // Then
        assertEquals(categoryName, foundCategory.getName(), "Category name should match");
        assertEquals(1L, foundCategory.getId(), "Category ID should match");
    }

    @Test
    void testFindCategoryByName_CategoryDoesNotExist() {
        // Given
        String categoryName = "NonExistent";

        // Mock behavior
        when(categoryRepository.findCategoryByName(categoryName)).thenReturn(null);

        // When
        Category foundCategory = categoryRepository.findCategoryByName(categoryName);

        // Then
        assertNull(foundCategory, "Category should not be found");
    }
}
