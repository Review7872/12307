package com.review7872.car.service;

import com.review7872.car.pojo.Seat;

import java.util.List;
import java.util.Map;

public interface RedisCreate {
    int create(long carId, String carNum, List<Seat> seatS, List<Map<String, String>> routeAndTime);
}
