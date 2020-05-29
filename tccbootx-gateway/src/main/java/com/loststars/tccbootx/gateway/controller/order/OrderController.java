package com.loststars.tccbootx.gateway.controller.order;

import com.loststars.tccbootx.api.item.ItemServiceAPI;
import com.loststars.tccbootx.api.item.model.ItemModel;
import com.loststars.tccbootx.api.order.OrderServiceAPI;
import com.loststars.tccbootx.api.user.UserServiceAPI;
import com.loststars.tccbootx.gateway.gateway.order.OrderGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.loststars.tccbootx.gateway.controller.vo.ResponseVO;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderGateway orderGateway;

    @Autowired
    private UserServiceAPI userServiceAPI;

    @Autowired
    private ItemServiceAPI itemServiceAPI;

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    @GetMapping("/hello")
    public String hello() {
        return "Hello gateway";
    }

    @GetMapping("/createOrder")
    public ResponseVO createOrder(@RequestParam("userId") Integer userId, @RequestParam("itemId") Integer itemId, @RequestParam("itemAmount") Integer itemAmount,
                                  @RequestParam("redPacketAmount") String redPacketAmount) {
        //参数校验
        if (userId == null || itemId == null || StringUtils.isEmpty(redPacketAmount)) return ResponseVO.fail("参数不合法");

        //验证用户、商品是否存在
        if (! userServiceAPI.isTrueUser(userId)) return ResponseVO.fail("用户不存在");

        ItemModel itemModel = itemServiceAPI.getItemModelById(itemId);
        if (itemModel == null) return ResponseVO.fail("商品不存在");

        //校验红包小于或等于商品总价
        BigDecimal redPacketAmountDecimal = new BigDecimal(redPacketAmount);
        BigDecimal totalPrice = itemModel.getPrice().multiply(new BigDecimal(itemAmount));

        if (redPacketAmountDecimal.compareTo(totalPrice) == 1) return ResponseVO.fail("使用红包的金额不能超过商品总价");

        //创建订单，状态为draft
        String orderId = orderServiceAPI.createOrder(userId, itemId, itemAmount, redPacketAmountDecimal);
        if (orderId == null) return ResponseVO.fail("创建订单失败，请稍后重试");

        //调用TCC中的资源预留
        try {
            orderGateway.record(orderId, userId, itemId, itemAmount, redPacketAmountDecimal);
        } catch (ArithmeticException e) {
            return ResponseVO.fail("处理订单失败，原因为：" + e.getMessage());
        }

        //Try
        //修改状态为draft的订单状态为paying
        //扣减资金，如果结果小于0，则抛出异常，否则生成一条资金扣减流水，状态为draft
        //扣减红包，如果结果小于0，则抛出异常，否则生成一条红包扣减流水，状态为draft
        //扣减库存，如果结果小于0，则抛出异常，否则生成一条库存扣减流水，状态为draft

        //Confirm
        //修改状态为paying的订单状态为confirmed
        //将流水状态为draft的资金流水的状态修改为confirmed
        //将流水状态为draft的红包流水的状态修改为confirmed
        //将流水状态为draft的库存流水的状态修改为confirmed

        //Cancel
        //将状态为paying的订单修改为pay_failed
        //根据资金流水，回补资金，将流水状态修改为pay_failed
        //根据红包流水，回补红包，将流水状态修改为pay_failed
        //根据库存流水，回补库存，将流水状态修改为pay_failed

        return ResponseVO.success(null);
    }
}
