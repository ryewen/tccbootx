package com.loststars.tccbootx.api.order;

import org.mengyun.tcctransaction.api.Compensable;

import java.math.BigDecimal;

public interface OrderServiceAPI {

    @Compensable
    boolean createOrder(String seats);

    @Compensable
    boolean isTrueSeats(String seats);

    @Compensable
    boolean isNotSoldSeats(String seats);

    String createOrder(Integer userId, Integer itemid, Integer itemAmount, BigDecimal redPacketAmount);

    @Compensable
    boolean record(String orderId);
}
