package com.noname.wagwag.model.request;

import com.noname.wagwag.repository.entity.SocialType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    @NotBlank(message = "프로필 이름이 필요합니다")
    private String userName;

    @Min(value = 0, message = "나이는 0 이상이어야 합니다")
    private Integer age;

    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

    /** KAKAO, NAVER, GOOGLE, DEVELOP 중 하나 */
    @NotNull(message = "소셜 타입이 필요합니다")
    private SocialType socialType;
}
