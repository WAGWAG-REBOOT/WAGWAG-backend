package com.noname.wagwag.controller;

import com.noname.wagwag.model.response.PostResponse;
import com.noname.wagwag.model.response.ServiceRegionResponse;
import com.noname.wagwag.service.ServiceRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController {
    
    private final ServiceRegionService serviceRegionService;
    
    /**
     * 지역 정보 조회
     * GET /api/region/{region-id}
     */
    @GetMapping("/{region-id}")
    public ResponseEntity<ServiceRegionResponse> getRegion(@PathVariable("region-id") Long regionId) {
        ServiceRegionResponse region = serviceRegionService.getRegion(regionId);
        return ResponseEntity.ok(region);
    }
    
    /**
     * 지역의 모든 게시물 조회
     * GET /api/region/{region-id}/posts
     */
    @GetMapping("/{region-id}/posts")
    public ResponseEntity<List<PostResponse>> getPostsInRegion(@PathVariable("region-id") Long regionId) {
        List<PostResponse> posts = serviceRegionService.getPostsInRegion(regionId);
        return ResponseEntity.ok(posts);
    }
    
    /**
     * 모든 지역 조회 (추가 기능)
     * GET /api/region
     */
    @GetMapping
    public ResponseEntity<List<ServiceRegionResponse>> getAllRegions() {
        List<ServiceRegionResponse> regions = serviceRegionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }
    
    /**
     * 활성화된 지역만 조회 (추가 기능)
     * GET /api/region?active=true
     */
    @GetMapping(params = "active")
    public ResponseEntity<List<ServiceRegionResponse>> getActiveRegions(@RequestParam boolean active) {
        if (active) {
            List<ServiceRegionResponse> regions = serviceRegionService.getActiveRegions();
            return ResponseEntity.ok(regions);
        } else {
            List<ServiceRegionResponse> regions = serviceRegionService.getAllRegions();
            return ResponseEntity.ok(regions);
        }
    }
    
    /**
     * 지역 코드로 조회 (추가 기능)
     * GET /api/region?code=regionCode
     */
    @GetMapping(params = "code")
    public ResponseEntity<ServiceRegionResponse> getRegionByCode(@RequestParam String code) {
        ServiceRegionResponse region = serviceRegionService.getRegionByCode(code);
        return ResponseEntity.ok(region);
    }
    
    /**
     * 지역명으로 검색 (추가 기능)
     * GET /api/region?search=regionName
     */
    @GetMapping(params = "search")
    public ResponseEntity<List<ServiceRegionResponse>> searchRegionsByName(@RequestParam String search) {
        List<ServiceRegionResponse> regions = serviceRegionService.searchRegionsByName(search);
        return ResponseEntity.ok(regions);
    }
    
    /**
     * 최소 대역폭 이상의 지역 조회 (추가 기능)
     * GET /api/region?minBandwidth=1000
     */
    @GetMapping(params = "minBandwidth")
    public ResponseEntity<List<ServiceRegionResponse>> getRegionsWithMinBandwidth(@RequestParam Integer minBandwidth) {
        List<ServiceRegionResponse> regions = serviceRegionService.getRegionsWithMinBandwidth(minBandwidth);
        return ResponseEntity.ok(regions);
    }
}