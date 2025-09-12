package com.noname.wagwag.repository;

import com.noname.wagwag.repository.entity.Like;
import com.noname.wagwag.repository.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.id.postId = :postId")
    Integer countByPostId(@Param("postId") Long postId);
    
    @Query("SELECT l FROM Like l WHERE l.id.userId = :userId")
    List<Like> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT l FROM Like l WHERE l.id.postId = :postId")
    List<Like> findByPostId(@Param("postId") Long postId);
    
    boolean existsByIdPostIdAndIdUserId(Long postId, Long userId);
    
    void deleteByIdPostIdAndIdUserId(Long postId, Long userId);
}
