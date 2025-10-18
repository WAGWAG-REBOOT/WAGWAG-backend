package com.noname.wagwag.repository;

import com.noname.wagwag.repository.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findByCategoryName(String categoryName);
    
    List<Category> findAllByOrderByCategoryNameAsc();
    
    @Query("SELECT c FROM Category c WHERE c.categoryName LIKE %:keyword%")
    List<Category> findByCategoryNameContaining(@Param("keyword") String keyword);
    
    boolean existsByCategoryName(String categoryName);
}
