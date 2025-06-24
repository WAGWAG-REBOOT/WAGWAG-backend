// src/main/java/com/noname/wagwag/model/response/LoginResponse.java
package com.noname.wagwag.model.response;

import com.noname.wagwag.repository.entity.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** JWT 발급 결과 + 현재 유저 상태 반환
 * 추후에 record로 수정 예정
 * */
@Getter
@AllArgsConstructor
public class LoginResponse {
    private final String    accessToken;
    private final String    tokenType;   // "Bearer"
    private final UserState userState;   // NOTREADY 또는 ACTIVE
}
