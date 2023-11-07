package com.review7872.order.service.impl;

import com.review7872.order.mapper.OrderMapper;
import com.review7872.order.pojo.Order;
import com.review7872.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> selectAllOrder() {
        return orderMapper.selectAllOrder();
    }

    @Override
    public List<Order> selectOrderByCardId(Long cardId) {
        return orderMapper.selectOrderByCardId(cardId);
    }

    @Override
    public Integer insertOrder(Long orderId, Long cardId, Long carId, String seatId, String orderTime) {
        return orderMapper.insertOrder(orderId, cardId, carId, seatId, orderTime);
    }

    @Override
    public Integer updateOrder(String seatId, Long orderId) {
        return orderMapper.updateOrder(seatId, orderId);
    }
}
