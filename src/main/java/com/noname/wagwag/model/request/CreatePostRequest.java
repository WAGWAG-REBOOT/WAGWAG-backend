package com.noname.wagwag.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostRequest {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    private String thumbnail;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private Long videoId;
    
    private Long regionId;
    
    private Integer accessStatus = 1; // 기본값 1
}
