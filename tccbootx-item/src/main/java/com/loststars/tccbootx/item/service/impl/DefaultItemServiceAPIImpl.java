package com.loststars.tccbootx.item.service.impl;

import com.loststars.tccbootx.api.item.ItemServiceAPI;
import com.loststars.tccbootx.item.service.model.ItemLogModel;
import com.loststars.tccbootx.api.item.model.ItemModel;
import com.loststars.tccbootx.item.dao.ItemDOMapper;
import com.loststars.tccbootx.item.dao.ItemLogDOMapper;
import com.loststars.tccbootx.item.dataobject.ItemDO;
import com.loststars.tccbootx.item.dataobject.ItemLogDO;
import com.loststars.tccbootx.item.dataobject.ItemLogDOExample;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultItemServiceAPIImpl implements ItemServiceAPI {

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemLogDOMapper itemLogDOMapper;

    @Override
    public boolean isTrueItem(Integer itemId) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(itemId);
        if (itemDO != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemModel getItemModelById(Integer itemId) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(itemId);
        if (itemDO == null) return null;
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        return itemModel;
    }

    @Override
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    public boolean record(String orderId, Integer itemId, Integer itemAmount) {
        System.out.println("DefaultItemServiceAPIImpl record");
        //扣库存
        int result = itemDOMapper.reduceStock(itemId, itemAmount);

        //若成功则生成一条库存流水，反之，则抛出异常
        if (result > 0) {
            ItemLogModel itemLogModel = new ItemLogModel();
            itemLogModel.setAmount(itemAmount);
            itemLogModel.setStatus(ItemLogModel.STATUS_DRAFT);
            itemLogModel.setOrderId(orderId);
            itemLogModel.setItemId(itemId);

            ItemLogDO itemLogDO = new ItemLogDO();
            BeanUtils.copyProperties(itemLogModel, itemLogDO);

            itemLogDOMapper.insertSelective(itemLogDO);
        } else {
            throw new ArithmeticException("库存不足");
        }
        return true;
    }

    @Transactional
    public boolean confirmRecord(String orderId, Integer itemId, Integer itemAmount) {
        System.out.println("DefaultItemServiceAPIImpl confirmRecord");
        //根据订单id查询库存流水
        ItemLogDOExample example = new ItemLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);

        List<ItemLogDO> itemLogDOs = itemLogDOMapper.selectByExample(example);
        if (itemLogDOs.isEmpty()) return false;

        //如果库存流水状态为draft，则更新为confirmed
        ItemLogDO itemLogDO = itemLogDOs.get(0);
        if (itemLogDO.getStatus().equals(ItemLogModel.STATUS_DRAFT)) {
            itemLogDO.setStatus(ItemLogModel.STATUS_CONFIRMED);
            itemLogDOMapper.updateByPrimaryKeySelective(itemLogDO);
        }

        return true;
    }

    @Transactional
    public boolean cancelRecord(String orderId, Integer itemId, Integer itemAmount) {
        System.out.println("DefaultItemServiceAPIImpl cancelRecord");
        //根据订单id查询库存流水
        ItemLogDOExample example = new ItemLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);

        List<ItemLogDO> itemLogDOs = itemLogDOMapper.selectByExample(example);
        if (itemLogDOs.isEmpty()) return false;

        //如果库存流水状态为draft，则更新为cancel，并回补库存
        ItemLogDO itemLogDO = itemLogDOs.get(0);
        if (itemLogDO.getStatus().equals(ItemLogModel.STATUS_DRAFT)) {
            int result = itemLogDOMapper.updateStatus(itemLogDO.getId(), ItemLogModel.STATUS_DRAFT, ItemLogModel.STATUS_CANCEL);
            if (result > 0) {
                itemDOMapper.addStock(itemId, itemAmount);
            }
        }

        return false;
    }
}
