package com.review7872.order.controller;

import com.review7872.order.pojo.Order;
import com.review7872.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping("/select")
    @Cacheable(value = "orderSelect", key = "#root.methodName")
    public List<Order> selectAll() {
        return orderService.selectAllOrder();
    }

    @GetMapping("/selectByCardId")
    public List<Order> selectByCardId(@RequestParam Long cardId) {
        return orderService.selectOrderByCardId(cardId);
    }

    @PostMapping("/update")
    @CacheEvict(value = "order", allEntries = true)
    public Integer updateSeatByOrderId(@RequestBody Order order) {
        return orderService.updateOrder(order.getSeatId(), order.getOrderId());
    }

    @PostMapping("/insert")
    @CacheEvict(value = "order", allEntries = true)
    public long insert(@RequestBody Order order) {
        log.info(new StringBuffer().append(order.getCardId()).append("下单成功，车次是").append(order.getCarId()).append("，座位是").append(order.getSeatId()).toString());
        return orderService.insertOrder(order.getCardId(), order.getCarId(), order.getSeatId());
    }
    @PostMapping("/pay")
    @CacheEvict(value = "order", allEntries = true)
    public Integer updatePay(long payId, Long orderId) {
        return orderService.updatePay(payId,orderId);
    }
}
