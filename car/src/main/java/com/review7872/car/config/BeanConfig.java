package com.review7872.car.config;

import com.review7872.car.utils.SnowflakeIdGenerator;
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
    @Value("${snow.beginTime}")
    private long beginTime;
    @Value("${snow.serverId}")
    private long serverId;
    @Value("${snow.hostId}")
    private long hostId;

    @Bean
    public Redisson getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+redisHost + ":" + redisPort)
                .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

    @Bean
    public SnowflakeIdGenerator getSnow() {
        return new SnowflakeIdGenerator(serverId, hostId, beginTime);
    }
}
