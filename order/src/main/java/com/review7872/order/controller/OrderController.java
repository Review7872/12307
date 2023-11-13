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
    @Cacheable(value = "order", key = "#root.methodName")
    public List<Order> selectAll() {
        return orderService.selectAllOrder();
    }

    @GetMapping("/selectByCardId")
    public List<Order> selectByCardId( Long cardId) {
        return orderService.selectOrderByCardId(cardId);
    }

    @PostMapping("/update")
    @CacheEvict(value = "order", allEntries = true)
    public Integer updateSeatByOrderId(String seatId,Long orderId ) {
        return orderService.updateOrder(seatId, orderId);
    }

    @PostMapping("/insert")
    @CacheEvict(value = "order", allEntries = true)
    public long insert( Long cardId,Long carId,String seatId ) {
        log.info(new StringBuffer().append(cardId).append("下单成功，车次是").append(carId).append("，座位是").append(seatId).toString());
        return orderService.insertOrder(cardId, carId, seatId);
    }

    @PostMapping("/pay")
    @CacheEvict(value = "order", allEntries = true)
    public Integer updatePay(long payId, Long orderId) {
        return orderService.updatePay(payId, orderId);
    }
}
