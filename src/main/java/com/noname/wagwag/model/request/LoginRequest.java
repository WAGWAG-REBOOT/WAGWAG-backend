package com.noname.wagwag.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {


    @NotBlank(message = "닉네임 왜안씀?")
    private String username;

    @NotBlank(message = "비번 왜안씀?")
    private String password;

}
