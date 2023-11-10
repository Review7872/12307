package com.review7872.car.service;

import com.review7872.car.pojo.CarTimeList;
import com.review7872.car.pojo.Seat;
import com.review7872.car.pojo.SeatInfo;
import org.redisson.api.RReadWriteLock;

import java.util.List;
import java.util.Map;

public interface CarTimeService {
    RReadWriteLock getReadWriteLock();

    CarTimeList get(String key);

    void set(String key, CarTimeList carTimes);

    void createCatTime(String key, Map<String, String> routeAndTime, List<Seat> seatList);

    void updateCatTime(String key, Map<String, String> routeAndTime);

    int setOcc(String timeCarIdCarNum, String beginRoute, String endRoute, String seatId, long cardId);

    int setOccBack(String timeCarIdCarNum, String beginRoute, String endRoute, String seatId);

    long getOcc(String timeCarIdCarNum, String beginRoute, String endRoute, String seatId);

    List<SeatInfo> getAllOcc(String timeCarIdCarNum, String route);

    int setRealTime(String timeCarIdCarNum, String route, String readTime);

    List<Object> getRouteOcc(String timeCarIdCarNum, String beginRoute, String endRoute);

    CarTimeList getAllTime(String timeCarIdCarNum);
}
