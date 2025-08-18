package com.example.fixBridge.controllers;

import com.example.fixBridge.models.FixOrderResult;
import com.example.fixBridge.services.FixOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FixOrderService fixOrderService;

    @Test
    void testPlaceOrder_Success() throws Exception {
        FixOrderResult mockResult = new FixOrderResult(true, "TD_Manish_FIX", "12345", null);
        Mockito.when(fixOrderService.sendFixOrder(any())).thenReturn(mockResult);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "symbol": "TSLA",
                                    "qty": 1,
                                    "side": "BUY",
                                    "type": "MARKET",
                                    "inverse": false
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.sessionId").value("TD_Manish_FIX"))
                .andExpect(jsonPath("$.orderId").value("12345"))
                .andExpect(jsonPath("$.errorMessage").isEmpty());
    }

    @Test
    void testPlaceOrder_Failure() throws Exception {
        FixOrderResult mockResult = new FixOrderResult(false, null, null, "Invalid order");
        Mockito.when(fixOrderService.sendFixOrder(any())).thenReturn(mockResult);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "symbol": "TSLA",
                                    "qty": 1,
                                    "side": "SELL",
                                    "type": "MARKET",
                                    "inverse": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.sessionId").isEmpty())
                .andExpect(jsonPath("$.orderId").isEmpty())
                .andExpect(jsonPath("$.errorMessage").value("Invalid order"));
    }
}
