package com.review7872.car.service;

import com.review7872.car.pojo.Car;

import java.util.List;
import java.util.Map;

public interface CarService {
    List<Car> selectAll();

    List<Car> selectAllByOpen(int open);

    List<Car> selectAllByOpenAndCarNum(int open, String carNum);

    List<Car> selectAllByRoute(String beginRoute, String endRoute);

    Car selectOne(long carId);

    int insertCar(long carId, Map<String,String> routeAndTime, String carNum, int open);

    int updateRoute(Map<String,String> routeAndTime, long carId);

    int updateCarNum(String carNum, long carId);

    int updateOpen(String open, long carId);
}
