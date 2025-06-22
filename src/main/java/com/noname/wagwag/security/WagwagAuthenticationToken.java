package com.noname.wagwag.security;

import com.noname.wagwag.repository.entity.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class WagwagAuthenticationToken extends AbstractAuthenticationToken {
    private final String principal;
    private final String credentials;
    private final User details;

    public WagwagAuthenticationToken(User user) {
        super(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.principal = user.getUserName();
        this.credentials = user.getPassword();
        this.details = user;
        super.setAuthenticated(true);
    }
    @Override public Collection<GrantedAuthority> getAuthorities() { return super.getAuthorities(); }
    @Override public Object getCredentials()      { return credentials; }
    @Override public User   getDetails()          { return details; }
    @Override public Object getPrincipal()        { return principal; }
    @Override public String getName()             { return principal; }

    /** 인증 상태를 외부에서 true 로 바꾸지 못하게 막음 */
    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor instead");
        super.setAuthenticated(false);
    }
}
