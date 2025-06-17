package com.noname.wagwag.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * 1. 생성자에서 주어진 secretKey로 JWT 서명을 위한 HMAC 키 생성
 * 2. generate()를 통해 username 기반 JWT 토큰 생성 (1시간 유효)
 * 2-1. 1000 * 60 * 60 -> 1시간, 100 * 60 * 10 -> 10분
 * 3. getUsername()으로 토큰에서 subject(username) 추출
 * 4. validate()로 서명, 만료 등 유효성 검증
 * JwtAuthTokenFilter에서 이 클래스를 이용해 인증 흐름 수행
 */
public class JwtProvider {

    private final Key key;

    public JwtProvider(String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String username) {
        Date now   = new Date();
        long expireMs = 1000 * 60 * 60;
        Date limit = new Date(now.getTime() + expireMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(limit)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsername(String token) {
        return parser().parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            parser().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private JwtParser parser() {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }
}
