package com.review7872.pay.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class MqConfig {
    public static final String EXCHANGE_NAME ="pay_sms";
    public static final String QUEUE_NAME = "sms";
    public static final String ROUTING_KEY="consumer";

    //声明交换机
    @Bean(EXCHANGE_NAME)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.fanoutExchange(EXCHANGE_NAME).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_NAME)
    public Queue QUEUE_INFORM_EMAIL(){
        return new Queue(QUEUE_NAME);
    }

    //队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_NAME) Queue queue,
                                              @Qualifier(EXCHANGE_NAME) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }
}
