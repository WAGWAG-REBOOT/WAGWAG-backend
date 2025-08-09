package com.noname.wagwag.model.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePostRequest {
    
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    private String thumbnail;
    
    private Long videoId;
    
    private Long regionId;
    
    private Integer accessStatus;
}
