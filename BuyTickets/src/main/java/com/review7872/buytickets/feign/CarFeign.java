package com.review7872.buytickets.feign;

import com.review7872.buytickets.pojo.Car;
import com.review7872.buytickets.pojo.CarTimeList;
import com.review7872.buytickets.pojo.SeatInfo;
import com.review7872.buytickets.pojo.SeatList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "car", path = "/car")
public interface CarFeign {
    @PostMapping("/buy")
    int buyTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId);

    @PostMapping("/back")
    int backTicket(long carId, String beginRoute, String endRoute, long cardId, String seatId);

    @GetMapping("/selectAllCar")
    List<Car> selectAllCar();

    @GetMapping("/selectOneCar")
    Car selectOneCar(long carId);

    @GetMapping("/selectAllCarNum")
    List<Car> selectAllCarNum(String carNum);

    @GetMapping("/selectByOpen")
    List<Car> selectByOpen(int open);

    @GetMapping("/route")
    List<Car> select(String beginRoute, String endRoute, int open);

    @GetMapping("/selectSeatByBeginEnd")
    List<Object> selectSeatByBeginEnd(long carId, String beginRoute, String endRoute);

    @GetMapping("/selectTime")
    CarTimeList selectTime(long carId);

    @GetMapping("/selectSeatByRoute")
    List<SeatInfo> selectSeatByRoute(long carId, String route);

    @GetMapping("/selectAllByOpenAndCarNum")
    List<Car> selectAllByOpenAndCarNum(int open, String carNum);

    @PostMapping("/setRealTime")
    int setRealTime(long carId, String route, String time);

    @PostMapping("/createCar")
    long createCar(String routeAndTime, String seatS, String carNum, int open);

    @PostMapping("/updateOpen")
    int updateOpen(String open, long carId);

    @GetMapping("/selectSeatInfo")
    SeatList selectSeatInfo(long carId);
}
