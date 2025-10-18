package com.noname.wagwag.repository;

import com.noname.wagwag.post.repository.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    
    List<PostCategory> findByPostId(Long postId);
    
    List<PostCategory> findByCategoryId(Long categoryId);
    
    @Query("SELECT pc.postId FROM PostCategory pc WHERE pc.categoryId = :categoryId")
    List<Long> findPostIdsByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query("SELECT pc.categoryId FROM PostCategory pc WHERE pc.postId = :postId")
    List<Long> findCategoryIdsByPostId(@Param("postId") Long postId);
    
    boolean existsByPostIdAndCategoryId(Long postId, Long categoryId);
    
    void deleteByPostIdAndCategoryId(Long postId, Long categoryId);
    
    void deleteByPostId(Long postId);
}
