package com.review7872.pay.controller;

import com.rabbitmq.client.Channel;
import com.review7872.pay.service.PayService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqController {
    @Autowired
    private PayService payService;
    @RabbitListener(queues = "")
    public void insertPay(Message message, Channel channel){
        String msg = new String(message.getBody());

    }
}
