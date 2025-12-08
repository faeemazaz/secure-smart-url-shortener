package com.url.shortner.secure_smart_url_shortener.dto;

import com.url.shortner.secure_smart_url_shortener.enums.Role;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.url.shortner.secure_smart_url_shortener.enums.AccessType;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class UrlRecordRequest {
    @NotBlank(message = "Please enter the url")
    private String originalUrl;

    @NotNull(message = "Please enter the access type")
    private AccessType accessType;

    private Role allowedRole;

    @Min(value = 1, message = "Expiry time must be at least 1 min")
    @Max(value = 1440, message = "Expiry time must be at least 1 min")
    private Integer expiryTime;

    private Integer maxClicks;

    public UrlRecordRequest() {}

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public Role getAllowedRole() {
        return allowedRole;
    }

    public void setAllowedRole(Role allowedRole) {
        this.allowedRole = allowedRole;
    }

    public Integer getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Integer expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getMaxClicks() {
        return maxClicks;
    }

    public void setMaxClicks(Integer maxClicks) {
        this.maxClicks = maxClicks;
    }
}
