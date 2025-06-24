// src/main/java/com/noname/wagwag/service/AuthService.java
package com.noname.wagwag.service;

import com.noname.wagwag.model.request.LoginRequest;
import com.noname.wagwag.model.response.LoginResponse;
import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.UserRoleRepository;
import com.noname.wagwag.repository.entity.*;
import com.noname.wagwag.security.JwtProvider;
import com.noname.wagwag.security.WagwagAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 1. login 에서는 이메일 + 소셜로그인 타입을 통해 사용자 존재여부 확인
 * 1-2. 새로운 로그인일 경우 온보딩 처리를 위해서 소셜로그인을 통한 이름, 나이, 이메일, 소셜타입만 저장
 * 1-3. userState 는 온보딩이 마무리가 되지 않았으므로 NOTREADY로 처리
 * 1-4. 로그인 시간 갱신
 * 1-5. 임시인증 처리 및 SecurityContext 주입
 * 1-6. 토큰생성
 * 2. nicknameExists 에서는 온보딩 과정에서의 닉네임 중복검사 처리
 * 3. finishOnboarding 에서는 선호 카테고리, 주소 저장등을 해야하나 테이블 수정이 필요해 사용처가 없음, 나중에 조정해야 함
 * */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;

    /* ────────── 1) 로그인 (소셜·개발 공통) ────────── */
    public LoginResponse login(LoginRequest loginRequest) {

        // ① email + socialType 기준으로 존재 여부 확인
        User user = userRepository
                .findByEmail(loginRequest.getEmail())
                .filter(u -> u.getSocialType() == loginRequest.getSocialType())
                .orElseGet(() -> {
                    // 신규: 최소 정보만 저장, userState = NOTREADY
                    User newUser = User.builder()
                            .userName(loginRequest.getUserName())
                            .age(loginRequest.getAge())
                            .email(loginRequest.getEmail())
                            .socialType(loginRequest.getSocialType())
                            .userState("NOTREADY")
                            .created(new Date())
                            .lastUpdated(new Date())
                            .build();
                    return userRepository.save(newUser);
                });

        // ② 마지막 로그인 시각 갱신
        user.setLastUpdated(new Date());
        userRepository.save(user);

        // ③ 임시 인증 컨텍스트 (Spring 내부용)
        Authentication auth = new WagwagAuthenticationToken(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // ④ JWT 발급
        String accessToken = jwtProvider.generate(user.getUserName());
        return new LoginResponse(accessToken, "Bearer", UserState.valueOf(user.getUserState()));
    }

    /* ────────── 2) 닉네임 중복 검사 ────────── */
    public boolean nicknameExists(String nickname) {
        return userRepository.findByUserName(nickname).isPresent();
    }

    /* ────────── 3) 온보딩 완료: 닉네임·동코드 저장 ────────── */
    public User finishOnboarding(Long userId, String nickname, String dongCode) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        if (!"NOTREADY".equals(user.getUserState())) {
            throw new IllegalStateException("이미 온보딩이 끝났거나 잘못된 상태");
        }

        user.setUserName(nickname);
        user.setDongCode(dongCode);
        user.setUserState("ACTIVE");
        user.setLastUpdated(new Date());
        userRepository.save(user);

        // ROLE_USER 권한 부여
        UserRoleId roleId = new UserRoleId(user.getUserId(), "ROLE_USER");
        roleRepository.findById(roleId)
                .orElseGet(() -> roleRepository.save(new UserRole(roleId, user)));

        return user;
    }
}
