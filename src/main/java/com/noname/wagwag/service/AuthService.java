package com.noname.wagwag.service;

import com.noname.wagwag.model.request.LoginRequest;
import com.noname.wagwag.model.response.LoginResponse;
import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.UserRoleRepository;
import com.noname.wagwag.repository.entity.SocialType;
import com.noname.wagwag.repository.entity.User;
import com.noname.wagwag.repository.entity.UserRole;
import com.noname.wagwag.repository.entity.UserRoleId;
import com.noname.wagwag.security.JwtProvider;
import com.noname.wagwag.security.WagwagAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final Pbkdf2PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;


    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("the account doesn't exist bitch"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        Authentication auth = authenticationManager.authenticate(new WagwagAuthenticationToken(user));
        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtProvider.generate(auth.getName());

        return new LoginResponse(accessToken, "Bearer");
    }

    public User register(String username, String email, String password) {
        if (userRepository.findByUserName(username).isPresent()) {
            throw new IllegalArgumentException("the nickname already exists bitch");
        }

        User user = User.builder()
                .userName(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .socialType(SocialType.DEVELOP) //Temporally
                .userState("ACTIVE")
                .build();

        UserRoleId roleId = new UserRoleId(user.getUserId(), "ROLE_USER");
        UserRole userRole = new UserRole(roleId, user);
        roleRepository.save(userRole);

        return user;
    }



}
