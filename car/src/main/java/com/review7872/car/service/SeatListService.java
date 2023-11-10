package com.review7872.car.service;

import com.review7872.car.pojo.Seat;
import com.review7872.car.pojo.SeatList;

import java.util.List;

public interface SeatListService {
    void createSeat(String seatCarIdCarNum,List<Seat> seatS);

    SeatList select(String seatCarIdCarNum);
}
