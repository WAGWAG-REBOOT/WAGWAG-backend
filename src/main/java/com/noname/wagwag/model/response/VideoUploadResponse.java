package com.noname.wagwag.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoUploadResponse {
    private boolean success;
    private Long postId;
    private Long videoId;
    private String message;
}
