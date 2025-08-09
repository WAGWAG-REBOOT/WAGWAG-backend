package com.noname.wagwag.repository;

import com.wagwag.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<Post> findByRegionIdOrderByCreatedAtDesc(Long regionId);
    
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.createdAt DESC")
    List<Post> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);
    
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    Page<Post> findByRegionIdOrderByCreatedAtDesc(Long regionId, Pageable pageable);
    
    long countByUserId(Long userId);
    
    @Query("SELECT p FROM Post p ORDER BY p.viewCnt DESC")
    Page<Post> findTopPostsByViewCount(Pageable pageable);
}
