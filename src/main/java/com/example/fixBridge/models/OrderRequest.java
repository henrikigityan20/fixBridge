package com.example.fixBridge.models;


import com.example.fixBridge.models.enums.CustomOrdType;
import com.example.fixBridge.models.enums.CustomSide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Symbol is mandatory")
    private String symbol;

    @Min(value = 1, message = "Quantity must be positive")
    private int qty;

    @NotNull(message = "Side is mandatory")
    private CustomSide side;

    @NotNull(message = "Type is mandatory")
    private CustomOrdType type;

    @Builder.Default
    private boolean inverse = false;
}