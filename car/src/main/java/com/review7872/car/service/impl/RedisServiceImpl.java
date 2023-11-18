package com.review7872.car.service.impl;

import com.review7872.car.pojo.Car;
import com.review7872.car.pojo.CarTimeList;
import com.review7872.car.pojo.SeatList;
import com.review7872.car.service.CarService;
import com.review7872.car.service.CarTimeService;
import com.review7872.car.service.RedisService;
import com.review7872.car.service.SeatListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    private CarService carService;
    @Autowired
    private CarTimeService carTimeService;
    @Autowired
    private SeatListService seatListService;

    @Override
    public int buyTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId) {
        carTimeService.getReadWriteLock().writeLock().lock(5, TimeUnit.SECONDS);
        seatListService.getReadWriteLock().writeLock().lock(5, TimeUnit.SECONDS);
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer().append("_").append(carId).append("_").append(car.getCarNum()).toString();
        String timeCarIdCarNum = "time" + carIdCarNum;
        String seatCarIdCarNum = "seat" + carIdCarNum;
        CarTimeList carTimes = carTimeService.get(timeCarIdCarNum);
        SeatList seats = seatListService.get(seatCarIdCarNum);
        try {
            if (carTimeService.getOcc(timeCarIdCarNum, beginRoute, endRoute, seatId) == 0) {
                int i1 = carTimeService.setOcc(timeCarIdCarNum, beginRoute, endRoute, seatId, cardId);
                int i2 = seatListService.buy(seatCarIdCarNum, seatId.substring(0, 1));
                if (i1 + i2 == 2) {
                    return 1;
                } else {
                    log.error(i1 + "--" + i2);
                    carTimeService.set(timeCarIdCarNum, carTimes);
                    seatListService.set(seatCarIdCarNum, seats);
                    throw new RuntimeException("错误，请检查日志");
                }
            }
            return 0;
        } finally {
            carTimeService.getReadWriteLock().writeLock().unlock();
            seatListService.getReadWriteLock().writeLock().unlock();
        }
    }

    @Override
    public int backTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId) {
        carTimeService.getReadWriteLock().writeLock().lock(5, TimeUnit.SECONDS);
        seatListService.getReadWriteLock().writeLock().lock(5, TimeUnit.SECONDS);
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer().append("_").append(carId).append("_").append(car.getCarNum()).toString();
        String timeCarIdCarNum = "time" + carIdCarNum;
        String seatCarIdCarNum = "seat" + carIdCarNum;
        CarTimeList carTimes = carTimeService.get(timeCarIdCarNum);
        SeatList seats = seatListService.get(seatCarIdCarNum);
        try {
            if (carTimeService.getOcc(timeCarIdCarNum, beginRoute, endRoute, seatId) == cardId) {
                int i1 = carTimeService.setOccBack(timeCarIdCarNum, beginRoute, endRoute, seatId);
                int i2 = seatListService.back(seatCarIdCarNum, seatId.substring(0, 1));
                if (i1 + i2 == 2) {
                    return 1;
                } else {
                    log.error(i1 + "--" + i2);
                    carTimeService.set(timeCarIdCarNum, carTimes);
                    seatListService.set(seatCarIdCarNum, seats);
                    throw new RuntimeException("错误，请检查日志");
                }
            }
            return 0;
        } finally {
            carTimeService.getReadWriteLock().writeLock().unlock();
            seatListService.getReadWriteLock().writeLock().unlock();
        }
    }
}
