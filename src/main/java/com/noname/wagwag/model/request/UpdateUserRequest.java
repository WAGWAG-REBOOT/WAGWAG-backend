package com.noname.wagwag.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserRequest {
    
    @Size(max = 20, message = "Nickname must not exceed 20 characters")
    private String nickName;
    
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 150, message = "Age must be less than 150")
    private Integer age;
    
    @Size(max = 10, message = "Dong code must not exceed 10 characters")
    private String dongCode;
    
    @Size(max = 20, message = "Phone code must not exceed 20 characters")
    private String phoneCode;
    
    @Size(max = 255, message = "Field must not exceed 255 characters")
    private String field;
    
    @Size(max = 20, message = "User state must not exceed 20 characters")
    private String userState;
}
