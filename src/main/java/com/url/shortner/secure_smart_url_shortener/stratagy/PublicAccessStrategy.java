package com.url.shortner.secure_smart_url_shortener.stratagy;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("PUBLIC")
public class PublicAccessStrategy implements IUrlAccessStrategy{
    @Override
    public boolean isAccessible(UrlRecord urlRecord, Authentication authentication) {
        return true; // anyone can access
    }
}
