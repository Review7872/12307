package com.review7872.order.service;


import com.review7872.order.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> selectAllOrder();

    List<Order> selectOrderByCardId(Long cardId);

    long insertOrder(Long cardId, Long carId, String seatId);

    Integer updateOrder(String seatId, Long orderId);

    Integer updatePay(long payId, Long orderId);
}
