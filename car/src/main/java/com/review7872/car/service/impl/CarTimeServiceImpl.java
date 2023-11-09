package com.review7872.car.service.impl;

import com.review7872.car.mapper.CarTimeMapper;
import com.review7872.car.pojo.CarTimeList;
import com.review7872.car.service.CarTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Transactional
@Service
public class CarTimeServiceImpl implements CarTimeService {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    @Autowired
    private CarTimeMapper carTimeMapper;

}
