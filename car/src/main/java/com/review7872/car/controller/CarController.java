package com.review7872.car.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
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
        log.info("购票行为 carId {}, beginRoute {}, endRoute {}, cardId {}, seatId {}",
                carId,beginRoute,endRoute,cardId,seatId);
        return redisService.buyTicket(carId, beginRoute, endRoute, cardId, seatId);
    }

    @PostMapping("/back")
    @CacheEvict(value = "carCon", allEntries = true)
    public int backTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId) {
        log.info("退票行为 carId {}, beginRoute {}, endRoute {}, cardId {}, seatId {}",
                carId,beginRoute,endRoute,cardId,seatId);
        return redisService.backTicket(carId, beginRoute, endRoute, cardId, seatId);
    }

    @GetMapping("/selectAllCar")
    public List<Car> selectAllCar() {
        log.info("查询所有车次信息行为");
        return carService.selectAll();
    }

    @GetMapping("/selectOneCar")
    public Car selectOneCar(long carId) {
        log.info("根据车次id查询车辆信息 carId {}", carId);
        return carService.selectOne(carId);
    }

    @GetMapping("/selectAllCarNum")
    public List<Car> selectAllCarNum(String carNum) {
        log.info("查询所有的车牌号 carNum {}",carNum);
        return carService.selectByCarNum(carNum);
    }

    @GetMapping("/selectByOpen")
    public List<Car> selectByOpen(int open) {
        log.info("根据是否开启查询列车信息 open {}",open);
        return carService.selectAllByOpen(open);
    }

    @GetMapping("/route")
    @Cacheable(value = "carCon", key = "#root.methodName+#beginRoute+#endRoute+#open")
    public List<Car> select(String beginRoute, String endRoute, int open) {
        log.info("根据旅程查询 beginRoute {}, endRoute {}, open {}",
                beginRoute,endRoute,open);
        return carService.selectAllByRoute(beginRoute, endRoute, open);
    }

    @GetMapping("/selectSeatByBeginEnd")
    @Cacheable(value = "carCon", key = "#root.methodName+#carId+#beginRoute+#endRoute")
    public List<Object> selectSeatByBeginEnd(long carId, String beginRoute, String endRoute) {
        log.info("根据旅程及车次id查询座位号 carId {}, beginRoute {}, endRoute {}",
                carId,beginRoute,endRoute);
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.getRouteOcc(carIdCarNum, beginRoute, endRoute);
    }

    @GetMapping("/selectTime")
    @Cacheable(value = "carTime", key = "#root.methodName+#carId")
    public CarTimeList selectTime(long carId) {
        log.info("根据车次id查询时间 carId {}",carId);
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.getAllTime(carIdCarNum);
    }

    @GetMapping("/selectSeatByRoute")
    public List<SeatInfo> selectSeatByRoute(long carId, String route) {
        log.info("根据站点及车次id查询座位信息 carId {}, route {}",carId,route);
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.getAllOcc(carIdCarNum, route);
    }

    @GetMapping("/selectAllByOpenAndCarNum")
    public List<Car> selectAllByOpenAndCarNum(int open, String carNum) {
        log.info("根据车牌号与是否打开查询 open {}, carNum {}",open,carNum);
        return carService.selectAllByOpenAndCarNum(open, carNum);
    }

    @PostMapping("/setRealTime")
    @CacheEvict(value = "carTime", allEntries = true)
    public int setRealTime(long carId, String route, String time) {
        log.info("设置时间 carId {}, route {}, time {}",carId,route,time);
        Car car = carService.selectOne(carId);
        String carIdCarNum = new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return carTimeService.setRealTime(carIdCarNum, route, time);
    }

    @PostMapping("/createCar")
    @CacheEvict(value = "carCon", allEntries = true)
    public long createCar(String routeAndTime, String seatS, String carNum, int open) {
        log.info("创建列车 routeAndTime {}, seatS {}, carNum {}, open {}",routeAndTime,seatS,carNum,open);
        if (routeAndTime != null && !routeAndTime.isEmpty() && seatS != null && !seatS.isEmpty()) {
            List<Map<String,String>> j1 = JSON.parseObject(routeAndTime, new TypeReference<>() {
            });
            List<Seat> j2 = JSON.parseObject(seatS, new TypeReference<>() {
            });
            return carService.insertCar(j1, j2, carNum, open);
        }
        return 0;
    }

    @PostMapping("/updateOpen")
    @CacheEvict(value = "carCon", allEntries = true)
    public int updateOpen(String open, long carId) {
        log.info("更新列车状态 open {}, carId {}",open,carId);
        return carService.updateOpen(open, carId);
    }

    @GetMapping("/selectSeatInfo")
    @Cacheable(value = "carCon", key = "#root.methodName+#carId")
    public SeatList selectSeatInfo(long carId) {
        log.info("根据车次查询所有座位信息 carId {}",carId);
        Car car = carService.selectOne(carId);
        String seatCarIdCarNum = new StringBuffer("seat").append("_").append(carId).append("_").append(car.getCarNum()).toString();
        return seatListService.select(seatCarIdCarNum);
    }
}
