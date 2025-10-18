package com.noname.wagwag.controller;

import com.noname.wagwag.model.response.CategoryResponse;
import com.noname.wagwag.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping("/{user-id}/user_prefered")
    public ResponseEntity<Map<String, List<CategoryResponse>>> getUserPreferredCategories(
            @PathVariable("user-id") Long userId) {
        log.info("Getting user preferred categories for user: {}", userId);
        
        List<CategoryResponse> categories = categoryService.getUserPreferredCategories(userId);
        
        return ResponseEntity.ok(Map.of("categories", categories));
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        log.info("Getting all categories");
        
        List<CategoryResponse> categories = categoryService.getAllCategories();
        
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable Long categoryId) {
        log.info("Getting category by id: {}", categoryId);
        
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        
        return ResponseEntity.ok(category);
    }
    
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestParam String categoryName) {
        log.info("Creating new category: {}", categoryName);
        
        CategoryResponse category = categoryService.createCategory(categoryName);
        
        return ResponseEntity.ok(category);
    }
}
