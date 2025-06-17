package com.noname.wagwag.service;

import com.noname.wagwag.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository        userRepository;
    private final JwtProvider jwtProvider;


}
