package com.url.shortner.secure_smart_url_shortener.controller;

import com.url.shortner.secure_smart_url_shortener.stratagy.UrlRedirectService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/r")
public class UrlRedirectController {
    @Autowired
    private UrlRedirectService urlRedirectService;

    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, Authentication authentication,
                         HttpServletResponse response) throws IOException {
        try {
            String originalUrl = urlRedirectService.redirect(code, authentication);
            response.sendRedirect(originalUrl);
        } catch (RuntimeException e) {
            if(e.getMessage().equals("URL expired")) {
                response.sendRedirect("410 URL expired");
            }
        }
    }
}
