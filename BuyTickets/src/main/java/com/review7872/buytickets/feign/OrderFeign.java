package com.review7872.buytickets.feign;

import com.review7872.buytickets.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "order", path = "/order")
public interface OrderFeign {
    @GetMapping("/select")
    List<Order> selectAll();

    @GetMapping("/selectByCardId")
    List<Order> selectByCardId(Long cardId);

    @PostMapping("/update")
    Integer updateSeatByOrderId(String seatId, Long orderId);

    @PostMapping("/insert")
    long insert(Long cardId, Long carId, String seatId);

    @PostMapping("/pay")
    Integer updatePay(long payId, Long orderId);
}
