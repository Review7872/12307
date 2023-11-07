package com.review7872.order.controller;

import com.review7872.order.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;
    @GetMapping("test")
    public long test(){
        return snowflakeIdGenerator.nextId();
    }
}
