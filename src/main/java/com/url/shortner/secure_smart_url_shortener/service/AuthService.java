package com.url.shortner.secure_smart_url_shortener.service;

import com.demo.urlShortner.urlShortner.entity.User;
import com.url.shortner.secure_smart_url_shortener.entity.Users;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public String registerUser(Users user);
}
