package com.noname.wagwag.model.response;

import com.wagwag.Post;
import lombok.Data;

import java.time.Instant;

@Data
public class PostResponse {
    
    private Long postId;
    private String title;
    private Instant createdAt;
    private Instant updatedAt;
    private String thumbnail;
    private Integer viewCnt;
    private Integer shareCnt;
    private Integer accessStatus;
    private Long userId;
    private Long videoId;
    private Long regionId;
    
    public static PostResponse from(Post post) {
        PostResponse response = new PostResponse();
        response.setPostId(post.getPostId());
        response.setTitle(post.getTitle());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        response.setThumbnail(post.getThumbnail());
        response.setViewCnt(post.getViewCnt());
        response.setShareCnt(post.getShareCnt());
        response.setAccessStatus(post.getAccessStatus());
        response.setUserId(post.getUserId());
        response.setVideoId(post.getVideoId());
        response.setRegionId(post.getRegionId());
        return response;
    }
}
