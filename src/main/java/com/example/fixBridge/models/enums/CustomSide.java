package com.example.fixBridge.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomSide {
    BUY, SELL;

    @JsonCreator
    public static CustomSide fromString(String value) {
        if (value == null) return null;
        return switch (value.toUpperCase()) {
            case "BUY" -> BUY;
            case "SELL" -> SELL;
            default -> throw new IllegalArgumentException("Unknown side: " + value);
        };
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
