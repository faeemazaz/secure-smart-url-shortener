package com.url.shortner.secure_smart_url_shortener.stratagy;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("ROLE_BASED")
public class RoleBasedAccessStrategy implements IUrlAccessStrategy {
    @Override
    public boolean isAccessible(UrlRecord urlRecord, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_" + urlRecord.getAllowedRole()));
    }
}
