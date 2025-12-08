package com.url.shortner.secure_smart_url_shortener.entity;

import com.url.shortner.secure_smart_url_shortener.enums.Role;
import jakarta.persistence.*;
import java.time.Instant;
import com.url.shortner.secure_smart_url_shortener.enums.AccessType;

@Entity
public class UrlRecord {
    @Id
    @GeneratedValue
    private Long urlId;

    private Long ownerId;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String shortCode;

    private String accessType;

    private String allowedRole;
    private Instant expiryTime;
    private Integer maxClicks;
    private Integer clickCount = 0;
    private Instant createdAt = Instant.now();

    public UrlRecord(){}
    public UrlRecord(Long urlId, Long ownerId, String originalUrl, String shortCode, String accessType, String allowedRole, Instant expiryTime, Integer maxClicks, Integer clickCount, Instant createdAt) {
        this.urlId = urlId;
        this.ownerId = ownerId;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.accessType = accessType;
        this.allowedRole = allowedRole;
        this.expiryTime = expiryTime;
        this.maxClicks = maxClicks;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }

    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAllowedRole() {
        return allowedRole;
    }

    public void setAllowedRole(String allowedRole) {
        this.allowedRole = allowedRole;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Instant expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getMaxClicks() {
        return maxClicks;
    }

    public void setMaxClicks(Integer maxClicks) {
        this.maxClicks = maxClicks;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
