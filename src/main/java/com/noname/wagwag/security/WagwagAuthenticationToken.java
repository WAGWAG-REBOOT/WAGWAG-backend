package com.noname.wagwag.security;

import com.noname.wagwag.repository.entity.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * 1. JWT Stateless 아키텍처에서 로그인 직후 임시 인증 용도로만 사용되는 커스텀 토큰
 * 1-1. 임시 인증, 즉 로그인 직후 컨텍스트에 머물다 사라지는 하나의 객체
 * 2. Authservice에서 User 엔티티를 담아 토큰 생성
 * 2-1. SecurityContextHolder에 setAuthentication(생성된 토큰)
 * 2-2. Spring 내부에서 인증 완
 * */


public class WagwagAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;   // username
    private final User details;     // User 엔티티 (ONBOARDING or ACTIVE or INACTIVE)

    /** 로그인 성공 후 사용되는 생성자 */
    public WagwagAuthenticationToken(User user) {
        super(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.principal = user.getUserName();
        this.details = user;
        super.setAuthenticated(true);
    }

    @Override public Object getPrincipal() { return principal; }
    @Override public Object getCredentials() { return null; }
    @Override public User getDetails() { return details; }
    @Override public String getName() { return principal; }

    /** 인증 상태를 외부에서 true 로 바꾸지 못하게 막음 */
    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted bitch");
        super.setAuthenticated(false);
    }
}
