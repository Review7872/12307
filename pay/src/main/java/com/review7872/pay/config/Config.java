package com.review7872.pay.config;


import com.review7872.pay.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class Config {
    @Value("${snow.beginTime}")
    private long beginTime;
    @Value("${snow.serverId}")
    private long serverId;
    @Value("${snow.hostId}")
    private long hostId;

    @Bean
    public SnowflakeIdGenerator getSnow() {
        return new SnowflakeIdGenerator(serverId, hostId, beginTime);
    }

    @Bean
    public SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
