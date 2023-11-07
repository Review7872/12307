package com.review7872.order.service;


import com.review7872.order.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> selectAllOrder();

    List<Order> selectOrderByCardId(Long cardId);

    Integer insertOrder(Long orderId, Long cardId, Long carId, String seatId, String orderTime);

    Integer updateOrder(String seatId, Long orderId);
}
