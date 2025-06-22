// src/main/java/com/noname/wagwag/security/WagWagAuthenticationProvider.java
package com.noname.wagwag.security;

import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class WagwagAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // ① 우리가 지원하는 토큰인지 확인
        if (!(authentication instanceof WagwagAuthenticationToken token)) {
            return null;   // 다른 Provider에게 패스
        }

        // ② DB에서 사용자 조회
        User user = userRepository.findByUserName(token.getPrincipal().toString())
                .orElseThrow(() ->
                        new UsernameNotFoundException("계정을 찾을 수 없음"));

        // ③ 비밀번호 검증
        if (!passwordEncoder.matches(token.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호");
        }

        // ④ 인증 완료된 새 토큰 반환 (권한 포함)
        return new WagwagAuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> authType) {
        return WagwagAuthenticationToken.class.isAssignableFrom(authType);
    }
}
