package com.review7872.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "pay",path = "/pay")
public interface PayFeign {
    @PostMapping("/insert")
    long insertPay();
}
