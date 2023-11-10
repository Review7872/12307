package com.review7872.car.mapper.impl;

import com.alibaba.fastjson2.JSON;
import com.review7872.car.mapper.CarTimeMapper;
import com.review7872.car.pojo.CarTimeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarTimeMapperImpl implements CarTimeMapper {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setRedisData(String key, CarTimeList carTimes) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(carTimes));
    }

    public CarTimeList getRedisData(String key) {
        return (CarTimeList) JSON.parse(redisTemplate.opsForValue().get(key));
    }
}
