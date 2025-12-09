package com.url.shortner.secure_smart_url_shortener.exception;

import org.springframework.security.core.AuthenticationException;

public class UnAuthorizedException extends AuthenticationException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
