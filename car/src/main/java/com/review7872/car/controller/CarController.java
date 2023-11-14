package com.review7872.car.controller;

import com.review7872.car.pojo.*;
import com.review7872.car.service.CarService;
import com.review7872.car.service.CarTimeService;
import com.review7872.car.service.RedisService;
import com.review7872.car.service.SeatListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/car")
@RestController
@Slf4j
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarTimeService carTimeService;
    @Autowired
    private SeatListService seatListService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/buy")
    @CacheEvict(value = "carCon", allEntries = true)
    public int buyTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId) {
        return redisService.buyTicket(carId, beginRoute, endRoute, cardId, seatId);
    }

    @PostMapping("/back")
    @CacheEvict(value = "carCon", allEntries = true)
    public int backTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId) {
        return redisService.backTicket(carId, beginRoute, endRoute, cardId, seatId);
    }

    @GetMapping("/selectAllCar")
    public List<Car> selectAllCar() {
        return carService.selectAll();
    }

    @GetMapping("/selectOneCar")
    public Car selectOneCar(long carId) {
        return carService.selectOne(carId);
    }

    @GetMapping("/selectAllCarNum")
    public List<Car> selectAllCarNum(String carNum) {
        return carService.selectByCarNum(carNum);
    }

    @GetMapping("/selectByOpen")
    public List<Car> selectByOpen(int open) {
        return carService.selectAllByOpen(open);
    }

    @GetMapping("/route")
    @Cacheable(value = "carCon", key = "#root.methodName+#beginRoute+#endRoute+#open")
    public List<Car> select(String beginRoute, String endRoute, int open) {
        return carService.selectAllByRoute(beginRoute, endRoute, open);
    }

    @GetMapping("/selectSeatByBeginEnd")
    @Cacheable(value = "carCon", key = "#root.methodName+#carId+#beginRoute+#endRoute")
    public List<Object> selectSeatByBeginEnd(long carId, String beginRoute, String endRoute) {
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.getRouteOcc(carIdCarNum, beginRoute, endRoute);
    }

    @GetMapping("/selectTime")
    @Cacheable(value = "carTime", key = "#root.methodName+#carId")
    public CarTimeList selectTime(long carId) {
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.getAllTime(carIdCarNum);
    }

    @GetMapping("/selectSeatByRoute")
    public List<SeatInfo> selectSeatByRoute(long carId, String route) {
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.getAllOcc(carIdCarNum, route);
    }

    @GetMapping("/selectAllByOpenAndCarNum")
    public List<Car> selectAllByOpenAndCarNum(int open, String carNum) {
        return carService.selectAllByOpenAndCarNum(open, carNum);
    }

    @PostMapping("/setRealTime")
    @CacheEvict(value = "carTime", allEntries = true)
    public int setRealTime(long carId, String route, String time) {
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.setRealTime(carIdCarNum, route, time);
    }

    @PostMapping("/createCar")
    @CacheEvict(value = "carCon", allEntries = true)
    public long createCar(Map<String, String> routeAndTime, List<Seat> seatS, String carNum, int open) {
        return carService.insertCar(routeAndTime, seatS, carNum, open);
    }

    @PostMapping("/updateOpen")
    @CacheEvict(value = "carCon", allEntries = true)
    public int updateOpen(String open, long carId) {
        return carService.updateOpen(open, carId);
    }

    @GetMapping("/selectSeatInfo")
    @Cacheable(value = "carCon", key = "#root.methodName+#carId")
    public SeatList selectSeatInfo(long carId) {
        Car car = carService.selectOne(carId);
        String seatCarIdCarNum = new StringBuffer("seat").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return seatListService.select(seatCarIdCarNum);
    }
}
