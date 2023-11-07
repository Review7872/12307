package com.review7872.order.service.impl;

import com.review7872.order.mapper.OrderMapper;
import com.review7872.order.pojo.Order;
import com.review7872.order.service.OrderService;
import com.review7872.order.service.PayFeign;
import com.review7872.order.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private SimpleDateFormat simpleDateFormat;
    @Autowired
    private PayFeign payFeign;

    @Override
    public List<Order> selectAllOrder() {
        return orderMapper.selectAllOrder();
    }

    @Override
    public List<Order> selectOrderByCardId(Long cardId) {
        return orderMapper.selectOrderByCardId(cardId);
    }

    @Override
    public long insertOrder(Long cardId, Long carId, String seatId) {
        long orderId = snowflakeIdGenerator.nextId();
        long payId = payFeign.insertPay();
        if (payId == 0){
            throw new RuntimeException("生成支付单号异常");
        }
        Integer i = orderMapper.insertOrder(orderId,
                cardId, carId, seatId,payId,
                simpleDateFormat.format(new Date()));
        if (i == 1) {
            return orderId;
        }else {
            return 0;
        }
    }

    @Override
    public Integer updateOrder(String seatId, Long orderId) {
        return orderMapper.updateOrder(seatId, orderId);
    }

    @Override
    public Integer updatePay(long payId, Long orderId) {
        return orderMapper.updatePay(payId,orderId);
    }
}
