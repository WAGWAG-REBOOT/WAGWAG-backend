package com.noname.wagwag.service;

import com.noname.wagwag.model.response.LikeResponse;
import com.noname.wagwag.post.repository.PostRepository;
import com.noname.wagwag.repository.LikeRepository;
import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.entity.Like;
import com.noname.wagwag.repository.entity.LikeId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public LikeResponse likePost(Long postId, Long userId) {
        log.info("User {} is trying to like post {}", userId, postId);
        
        // 이미 좋아요가 있는지 확인
        if (likeRepository.existsByIdPostIdAndIdUserId(postId, userId)) {
            throw new RuntimeException("User already liked this post");
        }
        
        // Post와 User 존재 확인
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        
        // 좋아요 생성
        LikeId likeId = new LikeId(postId, userId);
        Like like = Like.builder()
                .id(likeId)
                .build();
        
        likeRepository.save(like);
        
        // 업데이트된 좋아요 수 조회
        Integer likeCount = likeRepository.countByPostId(postId);
        
        log.info("Post {} liked by user {}. Total likes: {}", postId, userId, likeCount);
        
        return LikeResponse.builder()
                .success(true)
                .likeCount(likeCount)
                .build();
    }
    
    @Transactional
    public LikeResponse unlikePost(Long postId, Long userId) {
        log.info("User {} is trying to unlike post {}", userId, postId);
        
        // 좋아요가 있는지 확인
        if (!likeRepository.existsByIdPostIdAndIdUserId(postId, userId)) {
            throw new RuntimeException("Like not found");
        }
        
        // 좋아요 삭제
        likeRepository.deleteByIdPostIdAndIdUserId(postId, userId);
        
        // 업데이트된 좋아요 수 조회
        Integer likeCount = likeRepository.countByPostId(postId);
        
        log.info("Post {} unliked by user {}. Total likes: {}", postId, userId, likeCount);
        
        return LikeResponse.builder()
                .success(true)
                .likeCount(likeCount)
                .build();
    }
    
    public Integer getLikeCount(Long postId) {
        log.info("Getting like count for post: {}", postId);
        return likeRepository.countByPostId(postId);
    }
    
    public boolean isPostLikedByUser(Long postId, Long userId) {
        log.info("Checking if post {} is liked by user {}", postId, userId);
        return likeRepository.existsByIdPostIdAndIdUserId(postId, userId);
    }
}
