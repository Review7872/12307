package com.review7872.order.mapper;

import com.review7872.order.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("")
    @Results(id = "orderRes", value = {
            @Result(id = true, column = "order_id", property = "orderId"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "car_id", property = "carId"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "pay_id", property = "payId"),
            @Result(column = "order_time", property = "orderTime")
    })
    Order result();

    @Select("""
            select order_id,card_id,car_id,seat_id,pay_id,order_time from order_t order by order_time desc
            """)
    @ResultMap("orderRes")
    List<Order> selectAllOrder();

    @Select("""
            select order_id,card_id,car_id,seat_id,pay_id,order_time from order where card_id = #{cardId} order by order_time desc
            """)
    @ResultMap("orderRes")
    List<Order> selectOrderByCardId(Long cardId);

    @Insert("""
            insert into order(order_id,card_id,car_id,seat_id,order_time) values(#{orderId},#{cardId},#{carId},#{seatId},#{orderTime})
            """)
    Integer insertOrder(Long orderId, Long cardId, Long carId, String seatId, String orderTime);

    @Update("""
            update order set seat_id=#{seatId} where order_id = #{orderId}
            """)
    Integer updateOrder(String seatId, Long orderId);

    @Update("""
            update order set pay_id=#{payId} where order_id = #{orderId}
            """)
    Integer updatePay(long payId, Long orderId);
}
