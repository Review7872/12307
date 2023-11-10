package com.review7872.car.service;

import com.review7872.car.pojo.Seat;
import com.review7872.car.pojo.SeatList;
import org.redisson.api.RReadWriteLock;

import java.util.List;

public interface SeatListService {
    RReadWriteLock getReadWriteLock();

    SeatList get(String key);

    void set(String key, SeatList seatList);

    void createSeat(String seatCarIdCarNum, List<Seat> seatS);

    SeatList select(String seatCarIdCarNum);

    int buy(String seatCarIdCarNum, String seatLevel);

    int back(String seatCarIdCarNum, String seatLevel);
}
