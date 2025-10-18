package com.noname.wagwag.post.controller;


import com.noname.wagwag.model.response.PopularPostResponse;
import com.noname.wagwag.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
