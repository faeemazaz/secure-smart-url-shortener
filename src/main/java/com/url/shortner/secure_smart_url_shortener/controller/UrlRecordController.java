package com.url.shortner.secure_smart_url_shortener.controller;

import com.url.shortner.secure_smart_url_shortener.dto.UrlRecordRequest;
import com.url.shortner.secure_smart_url_shortener.dto.UrlRecordResponse;
import com.url.shortner.secure_smart_url_shortener.service.UrlRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class UrlRecordController {

    @Autowired
    private UrlRecordService urlRecordService;

    @PostMapping("/shorten")
    public UrlRecordResponse shortenUrl(@RequestBody @Valid UrlRecordRequest request,
                                        Authentication authentication) {
        return urlRecordService.shortenUrl(request, authentication);
    }
}
