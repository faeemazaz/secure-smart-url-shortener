package com.url.shortner.secure_smart_url_shortener.stratagy;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("PRIVATE")
public class PrivateAccessStrategy implements IUrlAccessStrategy {

    @Override
    public boolean isAccessible(UrlRecord urlRecord, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false; // JWT required
        }
        Long currentUserId = Long.parseLong(authentication.getName());
        return urlRecord.getOwnerId().equals(currentUserId);
    }
}