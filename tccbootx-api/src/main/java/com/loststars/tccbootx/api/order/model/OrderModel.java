package com.loststars.tccbootx.api.order.model;

import java.math.BigDecimal;

public class OrderModel {

    public static final String STATUS_DRAFT = "draft";

    public static final String STATUS_PAYING = "paying";

    public static final String STATUS_CONFIRMED = "confirmed";

    public static final String STATUS_CANCEL = "cancel";

    private String id;

    private Integer userId;

    private Integer itemId;

    private Integer itemAmount;

    private BigDecimal totalPrice;

    private String status;

    public String getId() {
        return id;
    }

    public void setOrderId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Integer itemAmount) {
        this.itemAmount = itemAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
