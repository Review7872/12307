package com.review7872.car.service.impl;

import com.review7872.car.mapper.SeatListMapper;
import com.review7872.car.pojo.Seat;
import com.review7872.car.pojo.SeatList;
import com.review7872.car.service.SeatListService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class SeatListServiceImpl implements SeatListService {
    @Autowired
    private SeatListMapper seatListMapper;
    @Autowired
    private Redisson redisson;
    private final RReadWriteLock readWriteLock = redisson.getReadWriteLock("Seat");
    private final RLock readLock = readWriteLock.readLock();
    private final RLock writeLock = readWriteLock.writeLock();


    @Override
    public RReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public SeatList get(String key) {
        readLock.lock();
        try {
            return seatListMapper.getRedisData(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void set(String key, SeatList seatList) {
        writeLock.lock(5, TimeUnit.SECONDS);
        try {
            seatListMapper.setRedisData(key, seatList);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 创建一个列车的座位信息
     */
    @Override
    public void createSeat(String seatCarIdCarNum, List<Seat> seatS) {
        writeLock.lock(5, TimeUnit.SECONDS);
        try {
            SeatList seats = new SeatList();
            seatS.forEach(i -> seats.add(new Seat(i.getSeatLevel(), i.getNum(), i.getRemainder(), i.getMoney())));
            seatListMapper.setRedisData(seatCarIdCarNum, seats);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public SeatList select(String seatCarIdCarNum) {
        readLock.lock();
        try {
            SeatList redisData = seatListMapper.getRedisData(seatCarIdCarNum);
            return redisData.all();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int buy(String seatCarIdCarNum, String seatLevel) {
        writeLock.lock(5, TimeUnit.SECONDS);
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
            writeLock.unlock();
        }
    }

    @Override
    public int back(String seatCarIdCarNum, String seatLevel) {
        writeLock.lock(5, TimeUnit.SECONDS);
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
            writeLock.unlock();
        }
    }
}
