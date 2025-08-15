package com.example.fixBridge.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomOrdType {
    MARKET, LIMIT;

    @JsonCreator
    public static CustomOrdType fromString(String value) {
        if (value == null) return null;
        return switch (value.toUpperCase()) {
            case "MARKET" -> MARKET;
            case "LIMIT" -> LIMIT;
            default -> throw new IllegalArgumentException("Unknown order type: " + value);
        };
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
