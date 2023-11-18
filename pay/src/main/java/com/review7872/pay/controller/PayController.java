package com.review7872.pay.controller;

import com.review7872.pay.pojo.Pay;
import com.review7872.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {
    @Autowired
    private PayService payService;

    @GetMapping("/selectAll")
    @Cacheable(value = "pay", key = "#root.methodName")
    public List<Pay> selectAll() {
        log.info("查询所有支付单");
        return payService.selectAll();
    }

    @GetMapping("/selectByPayStat")
    @Cacheable(value = "pay", key = "#root.methodName + #payStat")
    public List<Pay> selectByPayStat(byte payStat) {
        log.info("根据支付状态查询 payStat {}",payStat);
        return payService.selectByPayStat(payStat);
    }

    @GetMapping("/selectByPayWay")
    @Cacheable(value = "pay", key = "#root.methodName + #payWay")
    public List<Pay> selectByPayWay(byte payWay) {
        log.info("根据支付方式查询 payWay {}",payWay);
        return payService.selectByPayWay(payWay);
    }

    @GetMapping("/selectByPayWayAndPayStat")
    @Cacheable(value = "pay", key = "#root.methodName + #payWay + #payStat")
    public List<Pay> selectByPayWayAndPayStat(byte payWay, byte payStat) {
        log.info("根据支付方式与支付状态查询 payWay {}, payStat {}",payWay,payStat);
        return payService.selectByPayWayAndPayStat(payWay, payStat);
    }

    @GetMapping("/selectByPayId")
    public Pay selectByPayId(long payId) {
        log.info("根据支付单号进行精准查询 payId {}",payId);
        return payService.selectByPayId(payId);
    }

    @PostMapping("/insert")
    @CacheEvict(value = "pay", allEntries = true)
    public long insertPay() {
        log.info("新增支付单");
        return payService.insertPay();
    }

    @PostMapping("/updatePayStat")
    @CacheEvict(value = "pay", allEntries = true)
    public Integer updatePayStat(byte payStat, long payId) {
        log.info("更新支付状态 payStat {}, payId {}",payStat,payId);
        return payService.updatePayStat(payStat, payId);
    }

    @PostMapping("/updatePayWay")
    @CacheEvict(value = "pay", allEntries = true)
    public Integer updatePayWay(byte payWay, long payId) {
        log.info("更新支付方式 payWay {}, payId {}",payWay,payId);
        return payService.updatePayWay(payWay, payId);
    }

    @PostMapping("/updatePayWayAndPayStat")
    @CacheEvict(value = "pay", allEntries = true)
    public Integer updatePayWayAndPayStat(byte payWay, byte payStat, long payId) {
        log.info("更新支付方式和支付状态 payWay {}, payStat {}, payId {}",payWay,payStat,payId);
        return payService.updatePayWayAndPayStat(payWay, payStat, payId);
    }
}
