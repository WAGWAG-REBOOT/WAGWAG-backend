package com.noname.wagwag.model.response;

public interface PostWithLikeCountDto {
    Long getPostId();
    String getTitle();
    String getUserName();
    Long getVideoId();
    Integer getViewCount();
    Integer getLikeCount();
}
