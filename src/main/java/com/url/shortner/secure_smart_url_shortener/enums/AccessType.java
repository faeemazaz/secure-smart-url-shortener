package com.url.shortner.secure_smart_url_shortener.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum AccessType {
    PUBLIC,
    PRIVATE,
    ROLE_BASED;

    @JsonCreator
    public static AccessType fromValue(String value) {
        return Arrays.stream(values())
                .filter(a -> a.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "AccessType:" + value + " is invalid. Only allowed this values: " + Arrays.toString(values())
                ));
    }
}
