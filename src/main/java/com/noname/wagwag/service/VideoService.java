package com.noname.wagwag.service;

import com.noname.wagwag.model.request.VideoUploadRequest;
import com.noname.wagwag.model.response.VideoUploadResponse;
import com.noname.wagwag.repository.PostRepository;
import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.VideoRepository;
import com.noname.wagwag.repository.ServiceRegionRepository;
import com.noname.wagwag.repository.entity.Post;
import com.noname.wagwag.repository.entity.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {
    
    private final VideoRepository videoRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ServiceRegionRepository serviceRegionRepository;
    
    @Value("${file.upload.path:/tmp/uploads}")
    private String uploadPath;
    
    @Transactional
    public VideoUploadResponse uploadVideo(VideoUploadRequest request) {
        log.info("Uploading video for user: {} in region: {}", request.getUserId(), request.getRegionId());
        
        // 유효성 검사
        validateUploadRequest(request);
        
        try {
            // 파일 저장
            String videoPath = saveVideoFile(request.getVideoFile());
            
            // Post 생성
            Post post = Post.builder()
                    .title(request.getTitle())
                    .userId(request.getUserId())
                    .regionId(request.getRegionId())
                    .accessStatus(request.getAccessStatus())
                    .build();
            
            Post savedPost = postRepository.save(post);
            
            // Video 생성
            Video video = Video.builder()
                    .videoPath(videoPath)
                    .fileSize(request.getVideoFile().getSize())
                    .encodingType(getFileExtension(request.getVideoFile().getOriginalFilename()))
                    .videoId(savedPost.getPostId())
                    .build();
            
            Video savedVideo = videoRepository.save(video);
            
            // Post의 videoId 업데이트
            savedPost.setVideoId(savedVideo.getVideoId());
            postRepository.save(savedPost);
            
            log.info("Video uploaded successfully. PostId: {}, VideoId: {}", 
                    savedPost.getPostId(), savedVideo.getVideoId());
            
            return VideoUploadResponse.builder()
                    .success(true)
                    .postId(savedPost.getPostId())
                    .videoId(savedVideo.getVideoId())
                    .message("동영상이 성공적으로 업로드되었습니다")
                    .build();
                    
        } catch (Exception e) {
            log.error("Error uploading video", e);
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    private void validateUploadRequest(VideoUploadRequest request) {
        // User 존재 확인
        if (!userRepository.existsById(request.getUserId())) {
            throw new RuntimeException("사용자를 찾을 수 없습니다: " + request.getUserId());
        }
        
        // Region 존재 확인
        if (!serviceRegionRepository.existsById(request.getRegionId())) {
            throw new RuntimeException("지역을 찾을 수 없습니다: " + request.getRegionId());
        }
        
        // 파일 유효성 검사
        MultipartFile videoFile = request.getVideoFile();
        if (videoFile.isEmpty()) {
            throw new RuntimeException("비디오 파일이 비어있습니다");
        }
        
        // 파일 크기 제한 (100MB)
        long maxFileSize = 100 * 1024 * 1024; // 100MB
        if (videoFile.getSize() > maxFileSize) {
            throw new RuntimeException("파일 크기가 너무 큽니다. 최대 100MB까지 업로드 가능합니다");
        }
        
        // 파일 확장자 검사
        String filename = videoFile.getOriginalFilename();
        if (filename == null || !isValidVideoFile(filename)) {
            throw new RuntimeException("지원하지 않는 파일 형식입니다. mp4, avi, mov, wmv 파일만 업로드 가능합니다");
        }
    }
    
    private String saveVideoFile(MultipartFile file) throws IOException {
        // 업로드 디렉토리 생성
        Path uploadDir = Paths.get(uploadPath);
        Files.createDirectories(uploadDir);
        
        // 고유한 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + "." + extension;
        
        // 파일 저장
        Path filePath = uploadDir.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return filePath.toString();
    }
    
    private boolean isValidVideoFile(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return extension.matches("mp4|avi|mov|wmv|mkv|flv|webm");
    }
    
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
    
    public Video getVideoByPostId(Long postId) {
        log.info("Getting video for post: {}", postId);
        return videoRepository.findByPostId(postId)
                .orElseThrow(() -> new RuntimeException("Video not found for post: " + postId));
    }
    
    @Transactional
    public void deleteVideo(Long videoId) {
        log.info("Deleting video: {}", videoId);
        
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found: " + videoId));
        
        // 파일 시스템에서 파일 삭제
        try {
            Path filePath = Paths.get(video.getVideoPath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("Failed to delete video file: {}", video.getVideoPath(), e);
        }
        
        // DB에서 삭제
        videoRepository.delete(video);
    }
}
