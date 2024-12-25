package ba.edu.ibu.aitodo.rest.controller;

import ba.edu.ibu.aitodo.core.model.Category;
import ba.edu.ibu.aitodo.core.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetCategorySuggestionForTask() throws Exception {
        // Given
        String description = "Buy groceries";
        String suggestedCategory = "Shopping";

        when(categoryService.suggestCategory(description)).thenReturn(suggestedCategory);

        // When & Then
        mockMvc.perform(get("/api/category/suggest/{description}", description))
                .andExpect(status().isOk())
                .andExpect(content().string(suggestedCategory));
    }

    @Test
    void testGetAllCategories() throws Exception {
        // Given
        List<Category> mockCategories = Arrays.asList(
                new Category(1L, "Work"),
                new Category(2L, "Personal")
        );

        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        // When & Then
        mockMvc.perform(get("/api/category/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Work"))
                .andExpect(jsonPath("$[1].name").value("Personal"));
    }

    @Test
    void testCreateTask() throws Exception {
        // Given
        String categoryName = "Health";
        Category mockCategory = new Category(1L, categoryName);

        when(categoryService.createCategory(categoryName)).thenReturn(mockCategory);

        // When & Then
        mockMvc.perform(post("/api/category/create")
                        .content(categoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryName))
                .andExpect(jsonPath("$.id").value(1));
    }
}
