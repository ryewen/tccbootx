package com.loststars.tccbootx.user.service.impl;

import com.loststars.tccbootx.api.user.UserServiceAPI;
import com.loststars.tccbootx.user.service.model.RedPacketLogModel;
import com.loststars.tccbootx.user.service.model.WalletLogModel;
import com.loststars.tccbootx.user.dao.RedPacketLogDOMapper;
import com.loststars.tccbootx.user.dao.UserDOMapper;
import com.loststars.tccbootx.user.dao.WalletLogDOMapper;
import com.loststars.tccbootx.user.dataobject.*;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class DefaultUserServiceAPIImpl implements UserServiceAPI {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private WalletLogDOMapper walletLogDOMapper;

    @Autowired
    private RedPacketLogDOMapper redPacketLogDOMapper;

    @Override
    public boolean isTrueUser(Integer userId) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Compensable(confirmMethod = "confirmWalletRecord", cancelMethod = "cancelWalletRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    public boolean walletRecord(String orderId, Integer userId, BigDecimal walletAmount) {
        System.out.println("DefaultUserServiceAPIImpl walletRecord");
        //在数据库里比较并扣减钱包
        //如果成功，则生成钱包流水，反之，则抛出异常
        int result = userDOMapper.reduceWallet(userId, walletAmount);
        if (result > 0) {
            WalletLogModel walletLogModel = new WalletLogModel();
            walletLogModel.setAmount(walletAmount);
            walletLogModel.setUserId(userId);
            walletLogModel.setOrderId(orderId);
            walletLogModel.setStatus(WalletLogModel.STATUS_DRAFT);

            WalletLogDO walletLogDO = new WalletLogDO();
            BeanUtils.copyProperties(walletLogModel, walletLogDO);

            walletLogDOMapper.insertSelective(walletLogDO);
        } else {
            throw new ArithmeticException("钱包余额不足");
        }

        return true;
    }

    @Transactional
    public boolean confirmWalletRecord(String orderId, Integer userId, BigDecimal walletAmount) {
        System.out.println("DefaultUserServiceAPIImpl confirmWalletRecord");
        //获取钱包流水
        WalletLogDOExample example = new WalletLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<WalletLogDO> walletLogDOs = walletLogDOMapper.selectByExample(example);

        if (walletLogDOs.isEmpty()) return false;
        WalletLogDO walletLogDO = walletLogDOs.get(0);

        //校验钱包流水状态是否为draft，是则将状态更新为confirmed
        if (walletLogDO.getStatus().equals(WalletLogModel.STATUS_DRAFT)) {
            walletLogDO.setStatus(WalletLogModel.STATUS_CONFIRMED);
            walletLogDOMapper.updateByPrimaryKeySelective(walletLogDO);
        }
        return true;
    }

    @Transactional
    public boolean cancelWalletRecord(String orderId, Integer userId, BigDecimal walletAmount) {
        System.out.println("DefaultUserServiceAPIImpl cancelWalletRecord");
        //获取钱包流水
        WalletLogDOExample example = new WalletLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<WalletLogDO> walletLogDOs = walletLogDOMapper.selectByExample(example);

        if (walletLogDOs.isEmpty()) return false;
        WalletLogDO walletLogDO = walletLogDOs.get(0);

        //校验钱包流水状态是否为draft，是则回补用户钱包，再将状态更新为cancel
        if (walletLogDO.getStatus().equals(WalletLogModel.STATUS_DRAFT)) {
            int result = walletLogDOMapper.updateStatus(walletLogDO.getId(), WalletLogModel.STATUS_DRAFT, WalletLogModel.STATUS_CANCEL);
            if (result > 0) {
                userDOMapper.addWallet(userId, walletLogDO.getAmount());
            }
        }
        return false;
    }

    @Override
    @Compensable(confirmMethod = "confirmRedPacketRecord", cancelMethod = "cancelRedPacketRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    public boolean redPacketRecord(String orderId, Integer userId, BigDecimal redWalletAmount) {
        System.out.println("DefaultUserServiceAPIImpl redPacketRecord");
        //在数据库中扣减红包
        //若扣减成功，则生成一条红包流水，反之，则抛出异常
        int result = userDOMapper.reduceRedPacket(userId, redWalletAmount);
        if (result > 0) {
            RedPacketLogModel redPacketLogModel = new RedPacketLogModel();
            redPacketLogModel.setAmount(redWalletAmount);
            redPacketLogModel.setUserId(userId);
            redPacketLogModel.setStatus(RedPacketLogModel.STATUS_DRAFT);
            redPacketLogModel.setOrderId(orderId);

            RedPacketLogDO redPacketLogDO = new RedPacketLogDO();
            BeanUtils.copyProperties(redPacketLogModel, redPacketLogDO);

            redPacketLogDOMapper.insertSelective(redPacketLogDO);
        } else {
            throw new ArithmeticException("红包余额不足");
        }

        return true;
    }

    @Transactional
    public boolean confirmRedPacketRecord(String orderId, Integer userId, BigDecimal redWalletAmount) {
        System.out.println("DefaultUserServiceAPIImpl confirmRedPacketRecord");
        //根据orderId查出红包流水
        RedPacketLogDOExample example = new RedPacketLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<RedPacketLogDO> redPacketLogDOs = redPacketLogDOMapper.selectByExample(example);

        if (redPacketLogDOs.isEmpty()) return false;
        RedPacketLogDO redPacketLogDO = redPacketLogDOs.get(0);

        //若红包流水的状态为draft，则更新为confirmed
        if (redPacketLogDO.getStatus().equals(RedPacketLogModel.STATUS_DRAFT)) {
            redPacketLogDO.setStatus(RedPacketLogModel.STATUS_CONFIRMED);
            redPacketLogDOMapper.updateByPrimaryKeySelective(redPacketLogDO);
        }

        return true;
    }

    @Transactional
    public boolean cancelRedPacketRecord(String orderId, Integer userId, BigDecimal redWalletAmount) {
        System.out.println("DefaultUserServiceAPIImpl cancelRedPacketRecord");
        //根据orderId查询红包流水
        RedPacketLogDOExample example = new RedPacketLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<RedPacketLogDO> redPacketLogDOs = redPacketLogDOMapper.selectByExample(example);

        if (redPacketLogDOs.isEmpty()) return false;
        RedPacketLogDO redPacketLogDO = redPacketLogDOs.get(0);

        //若红包流水为draft，将状态更新为cancel，若成功更新，回补用户红包
        if (redPacketLogDO.getStatus().equals(RedPacketLogModel.STATUS_DRAFT)) {
            int result = redPacketLogDOMapper.updateStatus(redPacketLogDO.getId(), RedPacketLogModel.STATUS_DRAFT, RedPacketLogModel.STATUS_CANCEL);
            if (result > 0) {
                userDOMapper.addRedPacket(userId, redWalletAmount);
            }
        }

        return false;
    }
}
