package com.review7872.order.controller;

import com.review7872.order.pojo.Order;
import com.review7872.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping("/select")
    @Cacheable(value = "order", key = "#root.methodName")
    public List<Order> selectAll() {
        log.info("查询所有订单信息");
        return orderService.selectAllOrder();
    }

    @GetMapping("/selectByCardId")
    public List<Order> selectByCardId( Long cardId) {
        log.info("根据证件号查询所有订单信息 cardId {}",cardId);
        return orderService.selectOrderByCardId(cardId);
    }

    @PostMapping("/update")
    @CacheEvict(value = "order", allEntries = true)
    public Integer updateSeatByOrderId(String seatId,Long orderId ) {
        log.info("更新座位信息 seatId {}, orderId {}",seatId,orderId);
        return orderService.updateOrder(seatId, orderId);
    }

    @PostMapping("/insert")
    @CacheEvict(value = "order", allEntries = true)
    public long insert( Long cardId,Long carId,String seatId ) {
        log.info("新增订单信息 cardId {}, carId {}, seatId {}",cardId,carId,seatId);
        return orderService.insertOrder(cardId, carId, seatId);
    }

    @PostMapping("/pay")
    @CacheEvict(value = "order", allEntries = true)
    public Integer updatePay(long payId, Long orderId) {
        log.info("更新支付单号 payId {}, orderId {}",payId,orderId);
        return orderService.updatePay(payId, orderId);
    }
}
