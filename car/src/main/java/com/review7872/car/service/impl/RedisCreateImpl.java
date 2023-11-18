package com.review7872.car.service.impl;

import com.review7872.car.pojo.CarTimeList;
import com.review7872.car.pojo.Seat;
import com.review7872.car.pojo.SeatList;
import com.review7872.car.service.CarTimeService;
import com.review7872.car.service.RedisCreate;
import com.review7872.car.service.SeatListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCreateImpl implements RedisCreate {
    @Autowired
    private CarTimeService carTimeService;
    @Autowired
    private SeatListService seatListService;
    @Override
    public int create(long carId, String carNum, List<Seat> seatS, List<Map<String, String>> routeAndTime){
        carTimeService.getReadWriteLock().writeLock().lock(5, TimeUnit.SECONDS);
        seatListService.getReadWriteLock().writeLock().lock(5, TimeUnit.SECONDS);
        String carIdCarNum = new StringBuffer().append("_").append(carId).append("_").append(carNum).toString();
        String timeCarIdCarNum = "time" + carIdCarNum;
        String seatCarIdCarNum = "seat" + carIdCarNum;
        CarTimeList carTimes = carTimeService.get(timeCarIdCarNum);
        SeatList seats = seatListService.get(seatCarIdCarNum);
        try {
            int seat = seatListService.createSeat(
                    seatCarIdCarNum, seatS);
            int catTime = carTimeService.createCatTime(
                    timeCarIdCarNum, routeAndTime, seatS);
            if (seat+catTime == 2){
                return 1;
            } else {
                carTimeService.set(timeCarIdCarNum, carTimes);
                seatListService.set(seatCarIdCarNum, seats);
                throw new RuntimeException("错误，请检查日志");
            }
        }finally {
            carTimeService.getReadWriteLock().writeLock().unlock();
            seatListService.getReadWriteLock().writeLock().unlock();
        }
    }
}
