package com.example.fixBridge.models;

import com.example.fixBridge.models.enums.CustomOrdType;
import com.example.fixBridge.models.enums.CustomSide;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
    @NotBlank(message = "Symbol is mandatory") String symbol,
    @Min(value = 1, message = "Quantity must be positive") int qty,
    @NotNull(message = "Side is mandatory") CustomSide side,
    @NotNull(message = "Type is mandatory") CustomOrdType type,
    boolean inverse
) {}
