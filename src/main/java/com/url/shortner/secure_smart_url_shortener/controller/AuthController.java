package com.url.shortner.secure_smart_url_shortener.controller;

import com.url.shortner.secure_smart_url_shortener.entity.Users;
import com.url.shortner.secure_smart_url_shortener.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Users user) {
        return ResponseEntity.ok().body(authService.registerUser(user));
    }


}
