package com.noname.wagwag.service;

import com.noname.wagwag.model.response.CategoryResponse;
import com.noname.wagwag.post.repository.entity.PostCategory;
import com.noname.wagwag.repository.CategoryRepository;
import com.noname.wagwag.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;
    
    public List<CategoryResponse> getAllCategories() {
        log.info("Getting all categories");
        return categoryRepository.findAllByOrderByCategoryNameAsc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public CategoryResponse getCategoryById(Long categoryId) {
        log.info("Getting category by id: {}", categoryId);
        PostCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        return convertToResponse(category);
    }
    
    public List<CategoryResponse> getUserPreferredCategories(Long userId) {
        log.info("Getting user preferred categories for user: {}", userId);
        // TODO: 실제로는 사용자 선호도 로직을 구현해야 함
        // 현재는 모든 카테고리를 반환하되, 첫 번째 카테고리를 기본값으로 설정
        List<PostCategory> categories = categoryRepository.findAllByOrderByCategoryNameAsc();
        
        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .createdAt(category.getCreatedAt())
                        .isDefault(categories.indexOf(category) == 0) // 첫 번째를 기본값으로
                        .build())
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CategoryResponse createCategory(String categoryName) {
        log.info("Creating new category: {}", categoryName);
        
        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new RuntimeException("Category already exists: " + categoryName);
        }

        PostCategory category = PostCategory.builder()
                .categoryName(categoryName)
                .build();

        PostCategory savedCategory = categoryRepository.save(category);
        return convertToResponse(savedCategory);
    }
    
    private CategoryResponse convertToResponse(PostCategory category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAt())
                .isDefault(false)
                .build();
    }
}
