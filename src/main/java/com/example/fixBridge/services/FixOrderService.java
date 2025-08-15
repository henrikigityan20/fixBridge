package com.example.fixBridge.services;

import com.example.fixBridge.mappers.OrderMapper;
import com.example.fixBridge.models.FixOrderResult;
import com.example.fixBridge.models.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixOrderService {

    private final SessionFactory sessionFactory;

    public FixOrderResult sendFixOrder(OrderRequest request) {
        log.info("Received order request: {}", request);
        try {
            SessionID sessionId = sessionFactory.getTradingSession();

            var newSingleOrder = OrderMapper.toNewOrderSingle(request);

            boolean success = Session.sendToTarget(newSingleOrder, sessionId);

            return new FixOrderResult(success, sessionId.getSenderCompID(), newSingleOrder.getClOrdID().toString(), null);
        } catch (Exception e) {
            log.error("Error sending FIX order", e);
            return new FixOrderResult(false, null, null, e.getMessage());
        }
    }
}