package com.review7872.car.mapper.impl;

import com.alibaba.fastjson2.JSON;
import com.review7872.car.mapper.SeatListMapper;
import com.review7872.car.pojo.SeatList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SeatListMapperImpl implements SeatListMapper {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void setRedisData(String key, SeatList seatList) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(seatList));
    }

    @Override
    public SeatList getRedisData(String key) {
        return (SeatList) JSON.parse(redisTemplate.opsForValue().get(key));
    }
}
