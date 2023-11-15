package com.review7872.car.service;

import com.review7872.car.pojo.Car;
import com.review7872.car.pojo.Seat;

import java.util.List;
import java.util.Map;

public interface CarService {
    List<Car> selectAll();

    List<Car> selectAllByOpen(int open);

    List<Car> selectAllByOpenAndCarNum(int open, String carNum);

    List<Car> selectAllByRoute(String beginRoute, String endRoute, int open);

    Car selectOne(long carId);

    long insertCar(List<Map<String,String>> routeAndTime, List<Seat> seatS, String carNum, int open);

    int updateRoute(List<Map<String,String>> routeAndTime, long carId);

    int updateCarNum(String carNum, long carId);

    int updateOpen(String open, long carId);

    List<Car> selectByCarNum(String carNum);
}
