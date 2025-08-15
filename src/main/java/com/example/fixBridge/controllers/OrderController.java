package com.example.fixBridge.controllers;

import com.example.fixBridge.models.FixOrderResult;
import com.example.fixBridge.models.OrderRequest;
import com.example.fixBridge.services.FixOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final FixOrderService fixOrderService;

    @PostMapping
    public FixOrderResult placeOrder(@RequestBody @Valid OrderRequest request) {
        return fixOrderService.sendFixOrder(request);
    }
}