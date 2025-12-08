package com.url.shortner.secure_smart_url_shortener.service;

import com.url.shortner.secure_smart_url_shortener.dto.UrlRecordRequest;
import com.url.shortner.secure_smart_url_shortener.dto.UrlRecordResponse;
import org.springframework.security.core.Authentication;

public interface UrlRecordService {
    public UrlRecordResponse shortenUrl(UrlRecordRequest request, Authentication authentication);
}
