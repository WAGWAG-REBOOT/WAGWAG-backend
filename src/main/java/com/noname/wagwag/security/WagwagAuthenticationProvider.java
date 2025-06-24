// src/main/java/com/noname/wagwag/security/WagWagAuthenticationProvider.java
package com.noname.wagwag.security;

import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * 1. AuthService 가 만든 WagwagAuthenticationToken 만 처리
 * 2. authenticate 에서 토큰안에 User가 존재할 경우 통과
 * 2-2. 없을 경우 useremail로 DB 조회 후 User 엔티티 주입 후 새 토큰 생성
 * */




@RequiredArgsConstructor
public class WagwagAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository  userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // ① 우리가 지원하는 토큰인지 확인
        if (!(authentication instanceof WagwagAuthenticationToken token)) {
            return null;   // 다른 Provider에게 패스
        }

        /* 이미 User 엔티티가 들어 있으면 바로 통과 */
        if (token.getDetails() != null) {
            return token;
        }

        /* details 가 비어 있으면 DB 조회로 보충 */
        User user = userRepository.findByEmail(token.getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("계정 있긴함?"));

        return new WagwagAuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> authType) {
        return WagwagAuthenticationToken.class.isAssignableFrom(authType);
    }
}
