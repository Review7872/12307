package com.review7872.car.service;

public interface RedisService {
    int buyTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId);

    int backTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId);
}
