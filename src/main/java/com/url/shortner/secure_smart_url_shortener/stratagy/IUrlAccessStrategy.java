package com.url.shortner.secure_smart_url_shortener.stratagy;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import org.springframework.security.core.Authentication;

public interface IUrlAccessStrategy {
    boolean isAccessible(UrlRecord urlRecord, Authentication authentication);
}
