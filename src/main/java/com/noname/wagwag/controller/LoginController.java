// src/main/java/com/noname/wagwag/controller/LoginController.java
package com.noname.wagwag.controller;

import com.noname.wagwag.model.request.LoginRequest;
import com.noname.wagwag.model.response.LoginResponse;
import com.noname.wagwag.repository.entity.SocialType;
import com.noname.wagwag.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  1. POST /api/auth/login -> 소셜 로그인 (클라이언트가 프로필 데이터 전송)
 *  2. POST /api/auth/login/dev -> 개발모드 로그인 (더미 데이터로 자동 로그인)
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> socialLogin(@Valid @RequestBody LoginRequest req) {
        LoginResponse token = authService.login(req);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login/dev")
    public ResponseEntity<LoginResponse> devLogin() {

        /* 더미 사용자 프로필 생성 */
        LoginRequest devReq = new LoginRequest();
        devReq.setUserName("devUser");
        devReq.setAge(0);
        devReq.setEmail("dev@wagwag.com");
        devReq.setSocialType(SocialType.DEVELOP);

        LoginResponse token = authService.login(devReq);
        return ResponseEntity.ok(token);
    }
}
