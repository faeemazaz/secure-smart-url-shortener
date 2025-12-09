package com.url.shortner.secure_smart_url_shortener.stratagy;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import com.url.shortner.secure_smart_url_shortener.entity.Users;
import com.url.shortner.secure_smart_url_shortener.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("PRIVATE")
public class PrivateAccessStrategy implements IUrlAccessStrategy {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isAccessible(UrlRecord urlRecord, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false; // JWT required
        }
        Users user = (Users) authentication.getPrincipal();
        return urlRecord.getOwnerId().equals(user.getUserId());
    }
}