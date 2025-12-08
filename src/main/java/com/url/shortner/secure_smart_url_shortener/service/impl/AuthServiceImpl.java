package com.url.shortner.secure_smart_url_shortener.service.impl;

import com.url.shortner.secure_smart_url_shortener.dto.LoginRequest;
import com.url.shortner.secure_smart_url_shortener.dto.LoginResponse;
import com.url.shortner.secure_smart_url_shortener.entity.Users;
import com.url.shortner.secure_smart_url_shortener.repo.UserRepository;
import com.url.shortner.secure_smart_url_shortener.security.CustomUserDetailsService;
import com.url.shortner.secure_smart_url_shortener.security.JwtUtill;
import com.url.shortner.secure_smart_url_shortener.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtill jwtUtil;

    @Override
    public String registerUser(Users user) {
        if(userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User Inserted Successfully!!";
    }


    @Override
    public LoginResponse loginUser(LoginRequest request) {
        // Authenticate user using email and password
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponse(token, userDetails.getUsername());
    }

}
