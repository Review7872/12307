package com.review7872.car.service.impl;

import com.review7872.car.mapper.SeatListMapper;
import com.review7872.car.pojo.Seat;
import com.review7872.car.pojo.SeatList;
import com.review7872.car.service.SeatListService;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class SeatListServiceImpl implements SeatListService {
    @Autowired
    private SeatListMapper seatListMapper;
    @Autowired
    private RedissonClient redisson;
    public static final String REDIS_LOCK = "Seat";


    @Override
    public RReadWriteLock getReadWriteLock() {
        return redisson.getReadWriteLock(REDIS_LOCK);
    }

    @Override
    public SeatList get(String key) {
        redisson.getReadWriteLock(REDIS_LOCK).readLock().lock();
        try {
            return seatListMapper.getRedisData(key);
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).readLock().unlock();
        }
    }

    @Override
    public void set(String key, SeatList seatList) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            seatListMapper.setRedisData(key, seatList);
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    /**
     * 创建一个列车的座位信息
     *
     * @return
     */
    @Override
    public int createSeat(String seatCarIdCarNum, List<Seat> seatS) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            SeatList seats = new SeatList();
            seatS.forEach(i -> seats.add(new Seat(i.getSeatLevel(), i.getNum(), i.getRemainder(), i.getMoney())));
            seatListMapper.setRedisData(seatCarIdCarNum, seats);
            return 1;
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public SeatList select(String seatCarIdCarNum) {
        redisson.getReadWriteLock(REDIS_LOCK).readLock().lock();
        try {
            SeatList redisData = seatListMapper.getRedisData(seatCarIdCarNum);
            return redisData.all();
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).readLock().unlock();
        }
    }

    @Override
    public int buy(String seatCarIdCarNum, String seatLevel) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            SeatList redisData = seatListMapper.getRedisData(seatCarIdCarNum);
            redisData.forEach(i -> {
                if (i.getSeatLevel().equals(seatLevel)) {
                    i.setRemainder(i.getRemainder() - 1);
                }
            });
            seatListMapper.setRedisData(seatCarIdCarNum, redisData);
            return 1;
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }

    @Override
    public int back(String seatCarIdCarNum, String seatLevel) {
        redisson.getReadWriteLock(REDIS_LOCK).writeLock().lock(5, TimeUnit.SECONDS);
        try {
            SeatList redisData = seatListMapper.getRedisData(seatCarIdCarNum);
            redisData.forEach(i -> {
                if (i.getSeatLevel().equals(seatLevel)) {
                    i.setRemainder(i.getRemainder() + 1);
                }
            });
            seatListMapper.setRedisData(seatCarIdCarNum, redisData);
            return 1;
        } finally {
            redisson.getReadWriteLock(REDIS_LOCK).writeLock().unlock();
        }
    }
}
