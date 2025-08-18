package com.example.fixBridge.mappers;

import com.example.fixBridge.models.enums.CustomOrdType;
import com.example.fixBridge.models.enums.CustomSide;
import com.example.fixBridge.models.OrderRequest;
import quickfix.fix44.NewOrderSingle;
import quickfix.field.*;

import java.time.LocalDateTime;

public class OrderMapper {

    private OrderMapper(){}

    public static NewOrderSingle toNewOrderSingle(OrderRequest request) {
        String clOrdId = String.valueOf(System.currentTimeMillis());

        NewOrderSingle order = new NewOrderSingle(
                new ClOrdID(clOrdId),
                mapSide(request.side()),
                new TransactTime(LocalDateTime.now()),
                mapOrdType(request.type())
        );

        order.set(new Symbol(request.symbol()));
        order.set(new OrderQty(request.qty()));

        return order;
    }

    private static Side mapSide(CustomSide side) {
        return switch (side) {
            case BUY -> new Side(Side.BUY);
            case SELL -> new Side(Side.SELL);
        };
    }

    private static OrdType mapOrdType(CustomOrdType type) {
        return switch (type) {
            case MARKET -> new OrdType(OrdType.MARKET);
            case LIMIT -> new OrdType(OrdType.LIMIT);
        };
    }
}
