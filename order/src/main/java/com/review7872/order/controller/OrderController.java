package com.review7872.order.controller;

import com.review7872.order.pojo.Order;
import com.review7872.order.service.OrderService;
import com.review7872.order.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private SimpleDateFormat simpleDateFormat;

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
    public Integer insert(@RequestBody Order order) {
        Integer i = orderService.insertOrder(snowflakeIdGenerator.nextId(),
                order.getCardId(), order.getCarId(), order.getSeatId(), simpleDateFormat.format(new Date()));
        if (i == 1) {
            //TODO 发送mq消息
        }
        return 0;
    }
}
