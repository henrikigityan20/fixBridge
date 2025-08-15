package com.example.fixBridge.models;

public record FixOrderResult(
    boolean success,
    String sessionId,
    String orderId,
    String errorMessage
) {}