package com.review7872.car.mapper;


import com.review7872.car.pojo.SeatList;

public interface SeatListMapper {
    void setRedisData(String key, SeatList seatList);

    SeatList getRedisData(String key);
}
