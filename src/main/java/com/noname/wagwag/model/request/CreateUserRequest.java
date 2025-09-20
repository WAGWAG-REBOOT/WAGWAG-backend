package com.noname.wagwag.model.request;

import com.noname.wagwag.repository.entity.SocialType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest {
    
    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must not exceed 20 characters")
    private String userName;
    
    @NotBlank(message = "Nickname is required")
    @Size(max = 20, message = "Nickname must not exceed 20 characters")
    private String nickName;
    
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 150, message = "Age must be less than 150")
    private Integer age;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 20, message = "Email must not exceed 20 characters")
    private String email;
    
    @Size(max = 10, message = "Dong code must not exceed 10 characters")
    private String dongCode;
    
    @NotNull(message = "Social type is required")
    private SocialType socialType;
    
    @Size(max = 20, message = "Phone code must not exceed 20 characters")
    private String phoneCode;
    
    @Size(max = 255, message = "Field must not exceed 255 characters")
    private String field;
}
