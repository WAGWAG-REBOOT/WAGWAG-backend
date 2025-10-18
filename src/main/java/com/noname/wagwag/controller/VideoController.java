package com.noname.wagwag.controller;

import com.noname.wagwag.model.request.VideoUploadRequest;
import com.noname.wagwag.model.response.VideoUploadResponse;
import com.noname.wagwag.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class VideoController {
    
    private final VideoService videoService;
    
    @PostMapping("/upload")
    public ResponseEntity<VideoUploadResponse> uploadVideoPost(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam("userId") Long userId,
            @RequestParam("regionId") Long regionId,
            @RequestParam(value = "accessStatus", defaultValue = "1") Integer accessStatus) {
        
        log.info("Uploading video post - Title: {}, User: {}, Region: {}", title, userId, regionId);
        
        VideoUploadRequest request = new VideoUploadRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setVideoFile(videoFile);
        request.setUserId(userId);
        request.setRegionId(regionId);
        request.setAccessStatus(accessStatus);
        
        try {
            VideoUploadResponse response = videoService.uploadVideo(request);
            return ResponseEntity.status(201).body(response);
        } catch (RuntimeException e) {
            log.error("Error uploading video: {}", e.getMessage());
            
            VideoUploadResponse errorResponse = VideoUploadResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            
            if (e.getMessage().contains("파일 크기")) {
                return ResponseEntity.status(413).body(errorResponse); // Payload Too Large
            } else if (e.getMessage().contains("지원하지 않는 파일")) {
                return ResponseEntity.status(415).body(errorResponse); // Unsupported Media Type
            } else {
                return ResponseEntity.status(400).body(errorResponse); // Bad Request
            }
        }
    }
    
    @DeleteMapping("/video/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long videoId) {
        log.info("Deleting video: {}", videoId);
        
        try {
            videoService.deleteVideo(videoId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error deleting video: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
