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

    /**
     * 创建一个列车的座位信息
     */
    public void createSeat(String seatCarIdCarNum,List<Seat> seatS){
        writeLock.lock(5, TimeUnit.SECONDS);
        try {
            SeatList seats = new SeatList();
            seatS.forEach(i-> seats.add(new Seat(i.getSeatLevel(),i.getNum(),i.getRemainder(),i.getMoney())));
            seatListMapper.setRedisData(seatCarIdCarNum,seats);
        }finally {
            writeLock.unlock();
        }
    }
    public SeatList select(String seatCarIdCarNum){
        readLock.lock();
        try {
            SeatList redisData = seatListMapper.getRedisData(seatCarIdCarNum);
            return redisData.all();
        }finally {
            readLock.unlock();
        }
    }

}
