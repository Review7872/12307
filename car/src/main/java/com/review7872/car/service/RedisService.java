package com.review7872.car.service;

import com.review7872.car.pojo.Seat;

import java.util.List;
import java.util.Map;

public interface RedisService {
    int buyTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId);

    int backTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId);
}
