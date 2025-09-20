package com.noname.wagwag.controller;

import com.noname.wagwag.model.request.CreatePostRequest;
import com.noname.wagwag.model.request.UpdatePostRequest;
import com.noname.wagwag.model.response.PostListResponse;
import com.noname.wagwag.model.response.PostResponse;
import com.noname.wagwag.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시물 작업을 위한 RESTful API 컨트롤러
 * 
 * 엔드포인트:
 * 1. GET /api/posts - 페이징과 함께 모든 게시물 조회
 * 2. GET /api/posts/{id} - ID로 특정 게시물 조회
 * 3. POST /api/posts - 새 게시물 생성
 * 4. PUT /api/posts/{id} - 기존 게시물 수정
 * 5. DELETE /api/posts/{id} - 게시물 삭제
 * 6. GET /api/posts/user/{userId} - 사용자별 게시물 조회
 * 7. GET /api/posts/region/{regionId} - 지역별 게시물 조회
 * 8. GET /api/posts/search - 제목으로 게시물 검색
 * 9. GET /api/posts/top - 조회수 기준 인기 게시물 조회
 * 10. POST /api/posts/{id}/share - 공유 수 증가
 * 11. GET /api/posts/user/{userId}/count - 사용자별 게시물 수 조회
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    
    /**
     * 모든 게시물 조회
     * GET /api/posts?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<PostListResponse> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<PostResponse> posts = postService.getAllPosts(page, size);
        PostListResponse response = PostListResponse.from(posts);
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID로 특정 게시물 조회
     * GET /api/posts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }
    
    /**
     * 새 게시물 생성
     * POST /api/posts
     */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse post = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
    
    /**
     * 기존 게시물 수정
     * PUT /api/posts/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request) {
        
        PostResponse post = postService.updatePost(id, request);
        return ResponseEntity.ok(post);
    }
    
    /**
     * 게시물 삭제
     * DELETE /api/posts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * 사용자별 게시물 조회
     * GET /api/posts/user/{userId}
     * GET /api/posts/user/{userId}?page=0&size=10 (with pagination)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPostsByUser(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        
        if (page != null && size != null) {
            Page<PostResponse> posts = postService.getPostsByUser(userId, page, size);
            PostListResponse response = PostListResponse.from(posts);
            return ResponseEntity.ok(response);
        } else {
            List<PostResponse> posts = postService.getPostsByUser(userId);
            return ResponseEntity.ok(posts);
        }
    }
    
    /**
     * 지역별 게시물 조회
     * GET /api/posts/region/{regionId}
     */
    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<PostResponse>> getPostsByRegion(@PathVariable Long regionId) {
        List<PostResponse> posts = postService.getPostsByRegion(regionId);
        return ResponseEntity.ok(posts);
    }
    
    /**
     * 제목으로 게시물 검색
     * GET /api/posts/search?keyword=searchterm
     */
    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String keyword) {
        List<PostResponse> posts = postService.searchPosts(keyword);
        return ResponseEntity.ok(posts);
    }
    
    /**
     * 조회수 기준 인기 게시물 조회
     * GET /api/posts/top?page=0&size=10
     */
    @GetMapping("/top")
    public ResponseEntity<PostListResponse> getTopPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<PostResponse> posts = postService.getTopPosts(page, size);
        PostListResponse response = PostListResponse.from(posts);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 공유 수 증가
     * POST /api/posts/{id}/share
     */
    @PostMapping("/{id}/share")
    public ResponseEntity<Void> sharePost(@PathVariable Long id) {
        postService.incrementShareCount(id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 사용자별 게시물 수 조회
     * GET /api/posts/user/{userId}/count
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getPostCountByUser(@PathVariable Long userId) {
        long count = postService.getPostCountByUser(userId);
        return ResponseEntity.ok(count);
    }
}
