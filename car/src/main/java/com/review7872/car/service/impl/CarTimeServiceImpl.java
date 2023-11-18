package com.review7872.car.service.impl;

import com.review7872.car.mapper.CarTimeMapper;
import com.review7872.car.pojo.CarTime;
import com.review7872.car.pojo.CarTimeList;
import com.review7872.car.pojo.Seat;
import com.review7872.car.pojo.SeatInfo;
import com.review7872.car.service.CarTimeService;
import com.review7872.car.service.SeatListService;
import org.redisson.Redisson;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarTimeServiceImpl implements CarTimeService {
    @Autowired
    private CarTimeMapper carTimeMapper;
    @Autowired
    private Redisson redisson;
    public static final String REDIS_LOCK = "CarTime";
    @Autowired
    private SeatListService seatListService;

    @Override
    public RReadWriteLock getReadWriteLock() {
        return redisson.getReadWriteLock(REDIS_LOCK);
    }

    @Override
    public CarTimeList get(String key) {
        redisson.getReadWriteLock(REDIS_LOCK).readLock().lock(5, TimeUnit.SECONDS);
        try {
            return carTimeMapper.getRedisData(key);
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).readLock().unlock();
        }
    }

    @Override
    public void set(String key, CarTimeList carTimes) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            carTimeMapper.setRedisData(key, carTimes);
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public int createCatTime(String key, List<Map<String,String>> routeAndTime, List<Seat> seatList) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            CarTimeList carTimes = new CarTimeList();
            AtomicInteger count = new AtomicInteger();
            seatList.forEach(i -> count.addAndGet(i.getNum()));
            List<SeatInfo> seatInfoArrayList = new ArrayList<>(count.get());
            for (int i = 0; i < count.get(); i++) {
                seatInfoArrayList.add(i, new SeatInfo(0, 0L));
            }
            routeAndTime.forEach(i -> carTimes.add(new CarTime(i.get("route"), i.get("time"), "", seatInfoArrayList)));
            carTimeMapper.setRedisData(key, carTimes);
            return 1;
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public void updateCatTime(String key, List<Map<String,String>> routeAndTime) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(key);
            List<SeatInfo> seatInfoArrayList = redisData.get(0).getSeats();
            routeAndTime.forEach(i -> redisData.add(new CarTime(i.get("route"), i.get("time"), "", seatInfoArrayList)));
            carTimeMapper.setRedisData(key, redisData);
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public int setOcc(String timeCarIdCarNum, String beginRoute, String endRoute, String seatId, long cardId) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(timeCarIdCarNum);
            String seatCarIdCarNum = "seat" + timeCarIdCarNum.substring(4);
            redisData.setOcc(beginRoute, endRoute, seatId, cardId, seatListService.select(seatCarIdCarNum));
            carTimeMapper.setRedisData(timeCarIdCarNum, redisData);
            return 1;
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public int setOccBack(String timeCarIdCarNum, String beginRoute, String endRoute, String seatId) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(timeCarIdCarNum);
            String seatCarIdCarNum = "seat" + timeCarIdCarNum.substring(4);
            redisData.setOccBack(beginRoute, endRoute, seatId, seatListService.select(seatCarIdCarNum));
            carTimeMapper.setRedisData(timeCarIdCarNum, redisData);
            return 1;
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public long getOcc(String timeCarIdCarNum, String beginRoute, String endRoute, String seatId) {
        redisson.getReadWriteLock(REDIS_LOCK).readLock().lock();
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(timeCarIdCarNum);
            String seatCarIdCarNum = "seat" + timeCarIdCarNum.substring(4);
            return redisData.getOcc(beginRoute, endRoute, seatId, seatListService.select(seatCarIdCarNum));
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).readLock().unlock();
        }
    }

    @Override
    public List<SeatInfo> getAllOcc(String timeCarIdCarNum, String route) {
        redisson.getReadWriteLock(REDIS_LOCK).readLock().lock();
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(timeCarIdCarNum);
            String seatCarIdCarNum = "seat" + timeCarIdCarNum.substring(4);
            return redisData.getAllOcc(route, seatListService.select(seatCarIdCarNum));
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).readLock().unlock();
        }
    }

    @Override
    public int setRealTime(String timeCarIdCarNum, String route, String readTime) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(timeCarIdCarNum);
            redisData.setRealTime(route, readTime);
            carTimeMapper.setRedisData(timeCarIdCarNum, redisData);
            return 1;
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public List<Object> getRouteOcc(String timeCarIdCarNum, String beginRoute, String endRoute) {
        redisson.getReadWriteLock(REDIS_LOCK).readLock().lock();
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(timeCarIdCarNum);
            String seatCarIdCarNum = "seat" + timeCarIdCarNum.substring(4);
            return redisData.getRouteOcc(beginRoute, endRoute, seatListService.select(seatCarIdCarNum));
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).readLock().unlock();
        }
    }

    @Override
    public CarTimeList getAllTime(String timeCarIdCarNum) {
        redisson.getReadWriteLock(REDIS_LOCK).readLock().lock();
        try {
            CarTimeList redisData = carTimeMapper.getRedisData(timeCarIdCarNum);
            return redisData.getAllTime();
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).readLock().unlock();
        }
    }


}
