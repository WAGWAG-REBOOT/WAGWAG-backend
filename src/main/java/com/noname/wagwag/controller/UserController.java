// src/main/java/com/noname/wagwag/controller/UserController.java
package com.noname.wagwag.controller;

import com.noname.wagwag.model.response.NicknameValidationResponse;   // ✅ 외부 DTO import
import com.noname.wagwag.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 닉네임 중복 검사
 *   GET /api/user/validate?nickname={닉네임}
 *   (Bearer 토큰 필요)
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/validate")
    public ResponseEntity<NicknameValidationResponse> validateNickname(
            @RequestParam String nickname) {

        boolean exists = authService.nicknameExists(nickname);
        return ResponseEntity.ok(new NicknameValidationResponse(exists));
    }
}
