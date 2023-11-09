package com.review7872.car.mapper;

import com.review7872.car.pojo.CarTimeList;

public interface CarTimeMapper {
    void setRedisData(String key, CarTimeList carTimes);

    CarTimeList getRedisData(String key);
}
