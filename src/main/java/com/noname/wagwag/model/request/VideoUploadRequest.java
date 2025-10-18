package com.noname.wagwag.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoUploadRequest {
    @NotNull
    @Size(max = 40, message = "제목은 최대 40자까지 입력 가능합니다.")
    private String title;
    
    @Size(max = 180, message = "설명은 최대 180자까지 입력 가능합니다.")
    private String description;
    
    @NotNull
    private MultipartFile videoFile;
    
    @NotNull
    private Long userId;
    
    @NotNull
    private Long regionId;
    
    private Integer accessStatus = 1; // Default public
}
