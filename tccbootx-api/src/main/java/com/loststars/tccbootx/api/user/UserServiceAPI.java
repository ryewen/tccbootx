package com.loststars.tccbootx.api.user;

import org.mengyun.tcctransaction.api.Compensable;

import java.math.BigDecimal;

public interface UserServiceAPI {

    boolean isTrueUser(Integer userId);

    @Compensable
    boolean walletRecord(String orderId, Integer userId, BigDecimal walletAmount);

    @Compensable
    boolean redPacketRecord(String orderId, Integer userId, BigDecimal redWalletAmount);
}
