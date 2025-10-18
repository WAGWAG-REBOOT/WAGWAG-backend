package com.noname.wagwag.post.service;

import com.noname.wagwag.model.request.CreatePostRequest;
import com.noname.wagwag.model.request.UpdatePostRequest;
import com.noname.wagwag.model.response.PopularPostResponse;
import com.noname.wagwag.model.response.PostResponse;
import com.noname.wagwag.post.repository.PostRepository;
import com.noname.wagwag.model.response.PostWithLikeCountDto;
import com.noname.wagwag.repository.PostCategoryRepository;
import com.noname.wagwag.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final LikeRepository likeRepository;
    
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .thumbnail(request.getThumbnail())
                .userId(request.getUserId())
                .videoId(request.getVideoId())
                .regionId(request.getRegionId())
                .accessStatus(request.getAccessStatus())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .viewCnt(0)
                .shareCnt(0)
                .build();


        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }
    
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        // 조회수 증가
        incrementViewCount(postId);
        
        return PostResponse.from(post);
    }
    
    public Page<PostResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return posts.map(PostResponse::from);
    }
    
    public List<PostResponse> getPostsByUser(Long userId) {
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
    
    public Page<PostResponse> getPostsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return posts.map(PostResponse::from);
    }
    
    public List<PostResponse> getPostsByRegion(Long regionId) {
        List<Post> posts = postRepository.findByRegionIdOrderByCreatedAtDesc(regionId);
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
    
    public List<PostResponse> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(keyword);
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
    
    public Page<PostResponse> getTopPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findTopPostsByViewCount(pageable);
        return posts.map(PostResponse::from);
    }
    
    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getThumbnail() != null) {
            post.setThumbnail(request.getThumbnail());
        }
        if (request.getVideoId() != null) {
            post.setVideoId(request.getVideoId());
        }
        if (request.getRegionId() != null) {
            post.setRegionId(request.getRegionId());
        }
        if (request.getAccessStatus() != null) {
            post.setAccessStatus(request.getAccessStatus());
        }
        
        post.setUpdatedAt(Instant.now());
        Post updatedPost = postRepository.save(post);
        return PostResponse.from(updatedPost);
    }
    
    @Transactional
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        postRepository.deleteById(postId);
    }
    
    @Transactional
    public void incrementViewCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        post.setViewCnt((post.getViewCnt() != null ? post.getViewCnt() : 0) + 1);
        postRepository.save(post);
    }
    
    @Transactional
    public void incrementShareCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        post.setShareCnt((post.getShareCnt() != null ? post.getShareCnt() : 0) + 1);
        postRepository.save(post);
    }
    
    public long getPostCountByUser(Long userId) {
        return postRepository.countByUserId(userId);
    }
    
    // 카테고리별 포스트 조회 (페이징)
    public Page<Map<String, Object>> getPostsByCategory(Long categoryId, int page, int size, String sort) {
        log.info("Getting posts by category: {} with page: {}, size: {}, sort: {}", categoryId, page, size, sort);
        
        // 정렬 설정
        Sort sortBy = createSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);
        
        // 카테고리에 속한 postId들 조회
        List<Long> postIds = postCategoryRepository.findPostIdsByCategoryId(categoryId);
        
        if (postIds.isEmpty()) {
            return Page.empty(pageable);
        }
        
        // postId로 Post들 조회
        Page<Post> posts = postRepository.findByPostIdInOrderByCreatedAtDesc(postIds, pageable);
        
        // 좋아요 수 포함한 응답 생성
        return posts.map(post -> {
            Integer likeCount = likeRepository.countByPostId(post.getPostId());
            return Map.of(
                    "postId", post.getPostId(),
                    "title", post.getTitle(),
                    "userId", post.getUserId(),
                    "videoId", post.getVideoId(),
                    "viewCount", post.getViewCnt() != null ? post.getViewCnt() : 0,
                    "likeCount", likeCount != null ? likeCount : 0,
                    "shareCount", post.getShareCnt() != null ? post.getShareCnt() : 0
            );
        });
    }
    
    // 인기 포스트 조회 (지역별) - 개선된 버전
    public List<PopularPostResponse> getPopularPosts(Long regionId, int limit) {
        log.info("Getting popular posts for region: {} with limit: {}", regionId, limit);
        
        Pageable pageable = PageRequest.of(0, limit);
        List<PostWithLikeCountDto> posts = postRepository.findPopularPostsByRegionWithLikeCount(regionId, pageable);
        
        return posts.stream()
                .map(post -> PopularPostResponse.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .userName(post.getUserName() != null ? post.getUserName() : "USER_" + post.getPostId())
                        .videoId(post.getVideoId())
                        .viewCount(post.getViewCount() != null ? post.getViewCount() : 0)
                        .likeCount(post.getLikeCount() != null ? post.getLikeCount() : 0)
                        .build())
                .collect(Collectors.toList());
    }
    
    // 실시간 포스트 조회 (지역별, 최신순) - 개선된 버전
    public List<PopularPostResponse> getRealtimePosts(Long regionId, int limit) {
        log.info("Getting realtime posts for region: {} with limit: {}", regionId, limit);
        
        Pageable pageable = PageRequest.of(0, limit);
        List<PostWithLikeCountDto> posts = postRepository.findRealtimePostsByRegionWithLikeCount(regionId, pageable);
        
        return posts.stream()
                .map(post -> PopularPostResponse.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .userName(post.getUserName() != null ? post.getUserName() : "USER_" + post.getPostId())
                        .videoId(post.getVideoId())
                        .viewCount(post.getViewCount() != null ? post.getViewCount() : 0)
                        .likeCount(post.getLikeCount() != null ? post.getLikeCount() : 0)
                        .build())
                .collect(Collectors.toList());
    }
    
    // 오늘 업로드된 포스트 수 조회
    public Map<String, Integer> getTodayPostCount(Long regionId) {
        log.info("Getting today's post count for region: {}", regionId);
        
        Instant startOfDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endOfDay = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        
        int count;
        if (regionId != null) {
            count = postRepository.countByRegionIdAndCreatedAtBetween(regionId, startOfDay, endOfDay);
        } else {
            count = postRepository.countByCreatedAtBetween(startOfDay, endOfDay);
        }
        
        return Map.of("count", count);
    }
    
    private Sort createSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        
        String[] sortParams = sort.split(",");
        if (sortParams.length == 2) {
            String property = sortParams[0];
            String direction = sortParams[1];
            Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction) ? 
                    Sort.Direction.ASC : Sort.Direction.DESC;
            return Sort.by(sortDirection, property);
        }
        
        return Sort.by(Sort.Direction.DESC, "createdAt");
    }
}
