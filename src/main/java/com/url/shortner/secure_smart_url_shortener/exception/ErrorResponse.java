package com.url.shortner.secure_smart_url_shortener.exception;

public class ErrorResponse<T> {
    private String status;
    private T response;

    public ErrorResponse(String status, T response) {
        this.status = status;
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
