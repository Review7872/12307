package com.review7872.car.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private String redisPort;

    @Bean
    public Redisson getRedisson(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisHost+":"+redisPort)
                .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
