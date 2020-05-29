package com.loststars.tccbootx.item.service.model;

public class ItemLogModel {

    public static final String STATUS_DRAFT = "draft";

    public static final String STATUS_CONFIRMED = "confirmed";

    public static final String STATUS_CANCEL = "cancel";

    private Integer id;

    private String orderId;

    private Integer itemId;

    private Integer amount;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
