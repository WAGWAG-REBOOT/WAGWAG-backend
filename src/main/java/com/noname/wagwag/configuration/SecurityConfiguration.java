package com.noname.wagwag.configuration;


import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.security.JwtAuthTokenFilter;
import com.noname.wagwag.security.JwtProvider;
import com.noname.wagwag.security.WagwagAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 1. JWT-STATELESS 정책기반임
 * 2. JwtProvider는 HS-256를 사용하므로 32bit 이상의 키 주입 필요
 * 3. JwtAuthTokenFilter는 모든 요청에서 헤더를 검사함
 * 3-1. 헤더는 "Authorization: Bearer..." 로 시작
 * 4. PasswordEncoder는 이전에 구현한 비밀번호 관련 로직, 나중에 사용 하것지
 * 5. WagwagAuthenticationProvider는 Authservice가 만든 WagwagAuthenticationToken만 처리
 * 6. SecurityFilterChain은 "/api/auth/**" 경로만 일단 토큰없이 접근 가능, 이외의 엔드포인트는 인증 필요
 * */


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;


    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider("secret-key");
    }

    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter(JwtProvider jwtProvider) {
        return new JwtAuthTokenFilter(jwtProvider, userDetailsService);
    }

    @Bean
    public Pbkdf2PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
    @Bean
    public AuthenticationProvider wagwagAuthenticationProvider(UserRepository userRepository) {
        return new WagwagAuthenticationProvider(userRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, AuthenticationProvider provider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(provider)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthTokenFilter jwtFilter, AuthenticationProvider wagwagAuthenticationProvider) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(wagwagAuthenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}
