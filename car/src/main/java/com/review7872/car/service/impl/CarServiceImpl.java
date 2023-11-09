package com.review7872.car.service.impl;

import com.review7872.car.mapper.CarMapper;
import com.review7872.car.pojo.Car;
import com.review7872.car.service.CarService;
import com.review7872.car.service.CarTimeService;
import com.review7872.car.service.SeatListService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Car> selectAllByRoute(String beginRoute, String endRoute) {
        return carMapper.selectAllByRoute("-" + beginRoute, "-" + endRoute);
    }

    @Override
    public Car selectOne(long carId) {
        return carMapper.selectOne(carId);
    }

    @Override
    public int insertCar(long carId, Map<String,String> routeAndTime, String carNum, int open) {
        StringBuffer strRoute = new StringBuffer();
        routeAndTime.forEach((m,n)-> strRoute.append("-").append(m));
        /*carTimeService.addTime(new StringBuffer().append("seat_").append(carId).append("_").
                        append(carNum).toString()
                ,routeAndTime);*/
        // todo
        return carMapper.insertCar(carId, strRoute.toString(), carNum, open);

    }

    @Override
    public int updateRoute(Map<String,String> routeAndTime, long carId) {
        StringBuffer strRoute = new StringBuffer();
        routeAndTime.forEach((m,n)-> strRoute.append("-").append(m));
        /*carTimeService.addTime(new StringBuffer().append("seat_").append(carId).append("_").
                        append(this.selectOne(carId).getCarNum()).toString()
                ,routeAndTime);*/
        // todo
        return carMapper.updateRoute(strRoute.toString(), carId);

    }

    @Override
    public int updateCarNum(String carNum, long carId) {
        return carMapper.updateCarNum(carNum, carId);
    }

    @Override
    public int updateOpen(String open, long carId) {
        return carMapper.updateOpen(open, carId);
    }
}
