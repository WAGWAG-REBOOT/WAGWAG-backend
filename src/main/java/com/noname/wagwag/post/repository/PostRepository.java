package com.noname.wagwag.post.repository;

import com.noname.wagwag.model.response.PostWithLikeCountDto;
import com.noname.wagwag.post.repository.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
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
    
    // 새로 추가된 메서드들
    @Query("SELECT p FROM Post p WHERE p.postId IN :postIds ORDER BY p.createdAt DESC")
    Page<Post> findByPostIdInOrderByCreatedAtDesc(@Param("postIds") List<Long> postIds, Pageable pageable);
    
    Page<Post> findByRegionIdOrderByViewCntDesc(Long regionId, Pageable pageable);
    
    int countByRegionIdAndCreatedAtBetween(Long regionId, Instant startTime, Instant endTime);
    
    int countByCreatedAtBetween(Instant startTime, Instant endTime);
    
    // 효율적인 인기 포스트 조회 (좋아요 수 포함)
    @Query("SELECT p.postId as postId, p.title as title, u.userName as userName, " +
           "p.videoId as videoId, p.viewCnt as viewCount, " +
           "COALESCE((SELECT COUNT(l) FROM Like l WHERE l.id.postId = p.postId), 0) as likeCount " +
           "FROM Post p " +
           "LEFT JOIN User u ON p.userId = u.userId " +
           "WHERE p.regionId = :regionId " +
           "ORDER BY p.viewCnt DESC")
    List<PostWithLikeCountDto> findPopularPostsByRegionWithLikeCount(@Param("regionId") Long regionId, Pageable pageable);
    
    // 효율적인 실시간 포스트 조회 (좋아요 수 포함)
    @Query("SELECT p.postId as postId, p.title as title, u.userName as userName, " +
           "p.videoId as videoId, p.viewCnt as viewCount, " +
           "COALESCE((SELECT COUNT(l) FROM Like l WHERE l.id.postId = p.postId), 0) as likeCount " +
           "FROM Post p " +
           "LEFT JOIN User u ON p.userId = u.userId " +
           "WHERE p.regionId = :regionId " +
           "ORDER BY p.createdAt DESC")
    List<PostWithLikeCountDto> findRealtimePostsByRegionWithLikeCount(@Param("regionId") Long regionId, Pageable pageable);
}
