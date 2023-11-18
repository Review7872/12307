package com.review7872.buytickets.feign;

import com.review7872.buytickets.pojo.Pay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "pay", path = "/pay")
public interface PayFeign {
    @GetMapping("/selectAll")
    List<Pay> selectAll();

    @GetMapping("/selectByPayStat")
    List<Pay> selectByPayStat(byte payStat);

    @GetMapping("/selectByPayWay")
    List<Pay> selectByPayWay(byte payWay);

    @GetMapping("/selectByPayWayAndPayStat")
    List<Pay> selectByPayWayAndPayStat(byte payWay, byte payStat);

    @GetMapping("/selectByPayId")
    Pay selectByPayId(long payId);

    @PostMapping("/insert")
    long insertPay();

    @PostMapping("/updatePayStat")
    Integer updatePayStat(byte payStat, long payId);

    @PostMapping("/updatePayWay")
    Integer updatePayWay(byte payWay, long payId);

    @PostMapping("/updatePayWayAndPayStat")
    Integer updatePayWayAndPayStat(byte payWay, byte payStat, long payId);
}
