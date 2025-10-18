package com.noname.wagwag.post.controller;

import com.noname.wagwag.model.request.CreatePostRequest;
import com.noname.wagwag.model.request.UpdatePostRequest;
import com.noname.wagwag.model.response.PopularPostResponse;
import com.noname.wagwag.model.response.PostListResponse;
import com.noname.wagwag.model.response.PostResponse;
import com.noname.wagwag.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
 * 12. GET /api/posts/category/{category-id} - 카테고리별 포스트 조회 (페이징)
 * 13. GET /api/popular_post/{region_id} - 지역별 인기 포스트
 * 14. GET /api/realtime_post/{region_id} - 지역별 실시간 포스트
 * 15. GET /api/posts/count/today - 오늘 업로드된 포스트 수
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
    
    /**
     * 카테고리별 포스트 조회 (페이징)
     * GET /api/posts/category/{category-id}?page=0&size=20&sort=createdAt,desc
     */
    @GetMapping("/category/{category-id}")
    public ResponseEntity<Map<String, Object>> getPostsByCategory(
            @PathVariable("category-id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        
        Page<Map<String, Object>> posts = postService.getPostsByCategory(categoryId, page, size, sort);
        
        return ResponseEntity.ok(Map.of(
                "content", posts.getContent(),
                "pageable", Map.of(
                        "page", posts.getNumber(),
                        "size", posts.getSize(),
                        "totalElements", posts.getTotalElements(),
                        "totalPages", posts.getTotalPages(),
                        "first", posts.isFirst(),
                        "last", posts.isLast()
                )
        ));
    }
    
    /**
     * 오늘 업로드된 포스트 수 조회
     * GET /api/posts/count/today?regionId=1
     */
    @GetMapping("/count/today")
    public ResponseEntity<Map<String, Integer>> getTodayPostCount(
            @RequestParam(required = false) Long regionId) {
        
        Map<String, Integer> result = postService.getTodayPostCount(regionId);
        return ResponseEntity.ok(result);
    }
}

/**
 * 별도 엔드포인트들 (다른 경로)
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class RegionalPostController {
    
    private final PostService postService;
    
    /**
     * 지역별 인기 포스트 조회
     * GET /api/popular_post/{region_id}?limit=10
     */
    @GetMapping("/popular_post/{region_id}")
    public ResponseEntity<List<PopularPostResponse>> getPopularPosts(
            @PathVariable("region_id") Long regionId,
            @RequestParam(defaultValue = "10") int limit) {

        List<PopularPostResponse> result = postService.getPopularPosts(regionId, limit);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 지역별 실시간 포스트 조회
     * GET /api/realtime_post/{region_id}?limit=10
     */
    @GetMapping("/realtime_post/{region_id}")
    public ResponseEntity<List<PopularPostResponse>> getRealtimePosts(
            @PathVariable("region_id") Long regionId,
            @RequestParam(defaultValue = "10") int limit) {

        List<PopularPostResponse> result = postService.getRealtimePosts(regionId, limit);
        return ResponseEntity.ok(result);
    }
}
