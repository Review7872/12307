package com.review7872.order.controller;

import com.review7872.order.config.MqConfig;
import com.review7872.order.pojo.Order;
import com.review7872.order.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/select")
    @Cacheable(value = "order", key = "'orderAll'")
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
        long l = orderService.insertOrder(order.getCardId(), order.getCarId(), order.getSeatId());
        if (l == 0) {
            return 0;
        }
        rabbitTemplate.convertAndSend(MqConfig.EXCHANGE_NAME,"pay",l);
        return l;
    }
    @PostMapping("/pay")
    @CacheEvict(value = "order", allEntries = true)
    public Integer updatePay(long payId, Long orderId) {
        return orderService.updatePay(payId,orderId);
    }
}
