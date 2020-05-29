package com.loststars.tccbootx.gateway.gateway.order;

import com.loststars.tccbootx.api.item.ItemServiceAPI;
import com.loststars.tccbootx.api.item.model.ItemModel;
import com.loststars.tccbootx.api.order.OrderServiceAPI;
import com.loststars.tccbootx.api.user.UserServiceAPI;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderGateway {

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    @Autowired
    private UserServiceAPI userServiceAPI;

    @Autowired
    private ItemServiceAPI itemServiceAPI;

    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", asyncConfirm = true)
    public void record(String orderId, Integer userId, Integer itemId, Integer itemAmount, BigDecimal redPacketAmount) {
        System.out.println("OrderGateway record");
        orderServiceAPI.record(orderId);

        ItemModel itemModel = itemServiceAPI.getItemModelById(itemId);
        BigDecimal totalPrice = itemModel.getPrice().multiply(new BigDecimal(itemAmount));
        BigDecimal walletAmount = totalPrice.subtract(redPacketAmount);

        userServiceAPI.redPacketRecord(orderId, userId, redPacketAmount);
        userServiceAPI.walletRecord(orderId, userId, walletAmount);

        itemServiceAPI.record(orderId, itemId, itemAmount);
    }

    public void confirmRecord(String orderId, Integer userId, Integer itemId, Integer itemAmount, BigDecimal redPacketAmount) {
        System.out.println("OrderGateway confirmRecord");
    }

    public void cancelRecord(String orderId, Integer userId, Integer itemId, Integer itemAmount, BigDecimal redPacketAmount) {
        System.out.println("OrderGateway cancelRecord");
    }

    @Compensable(confirmMethod = "confirmSendMessage", cancelMethod = "cancelSendMessage", asyncConfirm = true)
    public void sendMessage(String message) {
        System.out.println("this is consumer sendMessage message = " + message);

        orderServiceAPI.createOrder(message);

        orderServiceAPI.isTrueSeats(message);

        orderServiceAPI.isNotSoldSeats(message);
        //System.out.println(serviceAPI.sendMessage(message));
    }

    public void confirmSendMessage(String message) {
        System.out.println("this is consumer confirmSendMessage message = " + message);
        //System.out.println(serviceAPI.sendMessage(message));
    }

    public void cancelSendMessage(String message) {
        System.out.println("this is consumer cancelSendMessage message = " + message);
        //System.out.println(serviceAPI.sendMessage(message));
    }
}
