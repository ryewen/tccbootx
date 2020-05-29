package com.loststars.tccbootx.api.user.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserModel implements Serializable {

    private Integer id;

    private String username;

    private BigDecimal walletAmount;

    private BigDecimal redPacketAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(BigDecimal walletAmount) {
        this.walletAmount = walletAmount;
    }

    public BigDecimal getRedPacketAmount() {
        return redPacketAmount;
    }

    public void setRedPacketAmount(BigDecimal redPacketAmount) {
        this.redPacketAmount = redPacketAmount;
    }
}
