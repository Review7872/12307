package com.review7872.pay.controller;

import com.review7872.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PayService payService;
    @PostMapping("/insert")
    public long insertPay(){
        return payService.insertPay();
    }
}
