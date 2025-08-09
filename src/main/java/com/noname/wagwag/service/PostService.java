package com.noname.wagwag.service;

import com.noname.wagwag.model.request.CreatePostRequest;
import com.noname.wagwag.model.request.UpdatePostRequest;
import com.noname.wagwag.model.response.PostResponse;
import com.noname.wagwag.repository.PostRepository;
import com.wagwag.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    
    private final PostRepository postRepository;
    
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setThumbnail(request.getThumbnail());
        post.setUserId(request.getUserId());
        post.setVideoId(request.getVideoId());
        post.setRegionId(request.getRegionId());
        post.setAccessStatus(request.getAccessStatus());
        post.setCreatedAt(Instant.now());
        post.setUpdatedAt(Instant.now());
        post.setViewCnt(0);
        post.setShareCnt(0);
        
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
}
