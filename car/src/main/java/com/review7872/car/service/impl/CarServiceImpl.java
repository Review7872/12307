package com.review7872.car.service.impl;

import com.review7872.car.mapper.CarMapper;
import com.review7872.car.pojo.Car;
import com.review7872.car.pojo.Seat;
import com.review7872.car.service.CarService;
import com.review7872.car.service.CarTimeService;
import com.review7872.car.service.SeatListService;
import com.review7872.car.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CarTimeService carTimeService;
    @Autowired
    private SeatListService seatListService;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public List<Car> selectAll() {
        return carMapper.selectAll();
    }

    @Override
    public List<Car> selectAllByOpen(int open) {
        return carMapper.selectAllByOpen(open);
    }

    @Override
    public List<Car> selectAllByOpenAndCarNum(int open, String carNum) {
        return carMapper.selectAllByOpenAndCarNum(open, carNum);
    }

    @Override
    public List<Car> selectAllByRoute(String beginRoute, String endRoute, int open) {
        return carMapper.selectAllByRoute("-" + beginRoute, "-" + endRoute, open);
    }

    @Override
    @Cacheable(value = "carInfo", key = "'car' + #carId")
    public Car selectOne(long carId) {
        return carMapper.selectOne(carId);
    }

    @Override
    @CacheEvict(value = "carInfo", allEntries = true)
    public long insertCar(List<Map<String,String>> routeAndTime, List<Seat> seatS, String carNum, int open) {
        long carId = snowflakeIdGenerator.nextId();
        StringBuffer strRoute = new StringBuffer();
        routeAndTime.forEach(i -> strRoute.append("-").append(i.get("route")));
        seatListService.createSeat(
                new StringBuffer("seat").append("_").append(carId).append("_").append(carNum).toString()
                , seatS);
        carTimeService.createCatTime(
                new StringBuffer("time").append("_").append(carId).append("_").append(carNum).toString()
                , routeAndTime, seatS);
        return carMapper.insertCar(carId, strRoute.toString(), carNum, open);

    }

    @Override
    @CacheEvict(value = "carInfo", allEntries = true)
    public int updateRoute(List<Map<String,String>> routeAndTime, long carId) {
        StringBuffer strRoute = new StringBuffer();
        routeAndTime.forEach(i -> strRoute.append("-").append(i.get("route")));
        Car car = carMapper.selectOne(carId);
        carTimeService.updateCatTime(
                new StringBuffer("time").append("_").append(carId).append("_").append(car.getCarNum()).toString(),
                routeAndTime);
        return carMapper.updateRoute(strRoute.toString(), carId);

    }

    @Override
    @CacheEvict(value = "carInfo", allEntries = true)
    public int updateCarNum(String carNum, long carId) {
        return carMapper.updateCarNum(carNum, carId);
    }

    @Override
    @CacheEvict(value = "carInfo", allEntries = true)
    public int updateOpen(String open, long carId) {
        return carMapper.updateOpen(open, carId);
    }

    @Override
    public List<Car> selectByCarNum(String carNum) {
        return carMapper.selectByCarNum(carNum);
    }
}
