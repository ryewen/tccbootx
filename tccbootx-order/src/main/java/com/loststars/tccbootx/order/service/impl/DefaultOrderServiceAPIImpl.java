package com.loststars.tccbootx.order.service.impl;

import com.loststars.tccbootx.api.item.ItemServiceAPI;
import com.loststars.tccbootx.api.item.model.ItemModel;
import com.loststars.tccbootx.api.order.OrderServiceAPI;
import com.loststars.tccbootx.api.order.model.OrderModel;
import com.loststars.tccbootx.order.dao.OrderDOMapper;
import com.loststars.tccbootx.order.dataobject.OrderDO;
import org.joda.time.DateTime;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class DefaultOrderServiceAPIImpl implements OrderServiceAPI {

    @Autowired
    private ItemServiceAPI itemServiceAPI;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Compensable(confirmMethod = "confirmCreateOrder", cancelMethod = "cancelCreateOrder", transactionContextEditor = DubboTransactionContextEditor.class)
    public boolean createOrder(String seats) {
        System.out.println("provider create order");
        return true;
    }

    public boolean confirmCreateOrder(String seats) {
        System.out.println("provider create order confirm");
        return false;
    }

    public boolean cancelCreateOrder(String seats) {
        System.out.println("provider create order cancel");
        return false;
    }

    @Override
    @Compensable(confirmMethod = "confirmIsTrueSeats", cancelMethod = "cancelIsTrueSeats", transactionContextEditor = DubboTransactionContextEditor.class)
    public boolean isTrueSeats(String seats) {
        System.out.println("provider isTrueSeats");
        if (seats.equals("1,2,3")) throw new IllegalArgumentException();
        return true;
    }

    public boolean confirmIsTrueSeats(String seats) {
        System.out.println("provider isTrueSeats confirm");
        return false;
    }

    public boolean cancelIsTrueSeats(String seats) {
        System.out.println("provider isTrueSeats cancel");
        return false;
    }

    @Override
    @Compensable(confirmMethod = "confirmIsNotSoldSeats", cancelMethod = "cancelIsNotSoldSeats", transactionContextEditor = DubboTransactionContextEditor.class)
    public boolean isNotSoldSeats(String seats) {
        System.out.println("provider isNotSoldSeats");
        if (seats.equals("4,5")) throw new IllegalArgumentException();
        return true;
    }

    public boolean confirmIsNotSoldSeats(String seats) {
        System.out.println("provider isNotSoldSeats confirm");
        return false;
    }

    public boolean cancelIsNotSoldSeats(String seats) {
        System.out.println("provider isNotSoldSeats cancel");
        return false;
    }

    @Override
    @Transactional
    public String createOrder(Integer userId, Integer itemId, Integer itemAmount, BigDecimal redPacketAmount) {
        OrderModel orderModel = new OrderModel();

        DateTime dateTime = new DateTime();
        String dateStr = dateTime.toString("yyyyMMddHHmmss");
        Random random = new Random();
        StringBuilder randomBuilder = new StringBuilder();
        for (int i = 0; i < 5; ++ i) randomBuilder.append(random.nextInt(10));
        String orderId = dateStr + randomBuilder.toString();

        orderModel.setItemAmount(itemAmount);
        orderModel.setUserId(userId);

        ItemModel itemModel = itemServiceAPI.getItemModelById(itemId);
        if (itemModel == null) return null;
        orderModel.setTotalPrice(itemModel.getPrice().multiply(new BigDecimal(itemAmount)));

        orderModel.setStatus(OrderModel.STATUS_DRAFT);
        orderModel.setOrderId(orderId);
        orderModel.setItemId(itemId);

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);

        int result = orderDOMapper.insertSelective(orderDO);
        if (result > 0) {
            return orderId;
        } else {
            return null;
        }
    }

    @Override
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    public boolean record(String orderId) {
        System.out.println("DefaultOrderServiceAPIImpl record");
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);

        //校验订单状态是否为draft，是则更新为paying
        if (orderDO != null && orderDO.getStatus().equals(OrderModel.STATUS_DRAFT)) {
            orderDO.setStatus(OrderModel.STATUS_PAYING);
            orderDOMapper.updateByPrimaryKeySelective(orderDO);
        }
        return true;
    }

    @Transactional
    public boolean confirmRecord(String orderId) {
        System.out.println("DefaultOrderServiceAPIImpl confirmRecord");
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);

        //校验订单状态是否为paying，是则更新为confirmed
        if (orderDO != null && orderDO.getStatus().equals(OrderModel.STATUS_PAYING)) {
            orderDO.setStatus(OrderModel.STATUS_CONFIRMED);
            orderDOMapper.updateByPrimaryKeySelective(orderDO);
        }
        return true;
    }

    @Transactional
    public boolean cancelRecord(String orderId) {
        System.out.println("DefaultOrderServiceAPIImpl cancelRecord");
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);

        //校验订单状态是否为paying，是则更新为cancel
        if (orderDO != null && (orderDO.getStatus().equals(OrderModel.STATUS_PAYING) || orderDO.getStatus().equals(OrderModel.STATUS_DRAFT))) {
            orderDO.setStatus(OrderModel.STATUS_CANCEL);
            orderDOMapper.updateByPrimaryKeySelective(orderDO);
        }
        return false;
    }
}
