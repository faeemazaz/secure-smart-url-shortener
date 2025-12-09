package com.url.shortner.secure_smart_url_shortener.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Role {
    USER,
    ADMIN;

    @JsonCreator
    public static Role fromValue(String value) {
        return Arrays.stream(values())
                .filter(r -> r.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Role:" + value + " is invalid. Only allowed values: " + Arrays.toString(values())
                ));
    }
}
