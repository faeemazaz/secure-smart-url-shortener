package com.url.shortner.secure_smart_url_shortener.service;

import com.url.shortner.secure_smart_url_shortener.dto.LoginRequest;
import com.url.shortner.secure_smart_url_shortener.dto.LoginResponse;
import com.url.shortner.secure_smart_url_shortener.entity.Users;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public String registerUser(Users user);

    public LoginResponse loginUser(LoginRequest request);
}
