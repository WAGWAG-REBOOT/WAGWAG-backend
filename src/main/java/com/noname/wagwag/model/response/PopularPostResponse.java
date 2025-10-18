package com.noname.wagwag.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PopularPostResponse {
    private Long postId;
    private String title;
    private String userName;
    private Long videoId;
    private Integer viewCount;
    private Integer likeCount;
}
