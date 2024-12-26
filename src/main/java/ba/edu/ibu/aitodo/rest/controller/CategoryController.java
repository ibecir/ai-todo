package ba.edu.ibu.aitodo.rest.controller;

import ba.edu.ibu.aitodo.core.model.Category;
import ba.edu.ibu.aitodo.core.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/suggest/{description}")
    public ResponseEntity<String> getCategorySuggestionForTask(@PathVariable String description) {
        return ResponseEntity.ok(categoryService.suggestCategory(description));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ResponseEntity<Category> createCategory(@RequestBody String categoryName) {
        return ResponseEntity.ok(categoryService.createCategory(categoryName));
    }
}

