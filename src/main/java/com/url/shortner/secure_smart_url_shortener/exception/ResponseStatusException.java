package com.url.shortner.secure_smart_url_shortener.exception;

public class ResponseStatusException extends RuntimeException{
    public ResponseStatusException(String message) {
        super(message);
    }
}
