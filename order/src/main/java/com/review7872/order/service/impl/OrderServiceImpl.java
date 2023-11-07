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

    /**
     * 查询所有订单
     * @return 查询结果
     */
    @Override
    public List<Order> selectAllOrder() {
        return orderMapper.selectAllOrder();
    }

    /**
     * 根据证件号查询订单
     * @param cardId 证件号
     * @return 查询结果
     */
    @Override
    public List<Order> selectOrderByCardId(Long cardId) {
        if (cardId<1){
            throw new RuntimeException("无效的证件号");
        }
        return orderMapper.selectOrderByCardId(cardId);
    }

    /**
     * 新增订单，
     * @param cardId 证件号
     * @param carId 车次id
     * @param seatId 车座
     * @return 订单号
     */
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
            throw new RuntimeException("新增订单失败");
        }
    }

    /**
     * 换座
     * @param seatId 座位号
     * @param orderId 订单号
     * @return 是否成功
     */
    @Override
    public Integer updateOrder(String seatId, Long orderId) {
        return orderMapper.updateOrder(seatId, orderId);
    }

    /**
     * 更新支付单号
     * @param payId 支付单号
     * @param orderId 订单号
     * @return 结果
     */
    @Override
    public Integer updatePay(long payId, Long orderId) {
        return orderMapper.updatePay(payId,orderId);
    }
}
