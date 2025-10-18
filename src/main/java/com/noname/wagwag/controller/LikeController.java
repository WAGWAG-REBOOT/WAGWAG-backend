package com.noname.wagwag.controller;

import com.noname.wagwag.model.request.LikeRequest;
import com.noname.wagwag.model.response.LikeResponse;
import com.noname.wagwag.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class LikeController {
    
    private final LikeService likeService;
    
    @PostMapping("/{post-id}/like")
    public ResponseEntity<LikeResponse> likePost(
            @PathVariable("post-id") Long postId,
            @Valid @RequestBody LikeRequest request) {
        log.info("Liking post {} by user {}", postId, request.getUserId());
        
        try {
            LikeResponse response = likeService.likePost(postId, request.getUserId());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error liking post: {}", e.getMessage());
            return ResponseEntity.status(409)
                    .body(LikeResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .build());
        }
    }
    
    @DeleteMapping("/{post-id}/like")
    public ResponseEntity<LikeResponse> unlikePost(
            @PathVariable("post-id") Long postId,
            @RequestParam Long userId) {
        log.info("Unliking post {} by user {}", postId, userId);
        
        try {
            LikeResponse response = likeService.unlikePost(postId, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error unliking post: {}", e.getMessage());
            return ResponseEntity.status(404)
                    .body(LikeResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/{post-id}/like/count")
    public ResponseEntity<Integer> getLikeCount(@PathVariable("post-id") Long postId) {
        log.info("Getting like count for post: {}", postId);
        
        Integer likeCount = likeService.getLikeCount(postId);
        return ResponseEntity.ok(likeCount);
    }
    
    @GetMapping("/{post-id}/like/status")
    public ResponseEntity<Boolean> checkLikeStatus(
            @PathVariable("post-id") Long postId,
            @RequestParam Long userId) {
        log.info("Checking like status for post {} by user {}", postId, userId);
        
        boolean isLiked = likeService.isPostLikedByUser(postId, userId);
        return ResponseEntity.ok(isLiked);
    }
}
