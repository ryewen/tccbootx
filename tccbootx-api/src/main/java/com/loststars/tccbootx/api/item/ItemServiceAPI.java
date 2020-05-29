package com.loststars.tccbootx.api.item;

import com.loststars.tccbootx.api.item.model.ItemModel;

import java.math.BigDecimal;

public interface ItemServiceAPI {

    boolean isTrueItem(Integer itemId);

    ItemModel getItemModelById(Integer itemId);

    boolean record(String orderId, Integer itemId, Integer itemAmount);
}
