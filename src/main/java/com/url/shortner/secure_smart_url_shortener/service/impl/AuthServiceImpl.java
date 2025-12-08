package com.url.shortner.secure_smart_url_shortener.service.impl;

import com.url.shortner.secure_smart_url_shortener.entity.Users;
import com.url.shortner.secure_smart_url_shortener.repo.UserRepository;
import com.url.shortner.secure_smart_url_shortener.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(Users user) {
        if(userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User Inserted Successfully!!";
    }

}
