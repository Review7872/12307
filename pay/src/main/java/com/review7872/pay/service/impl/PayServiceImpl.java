package com.review7872.pay.service.impl;

import com.review7872.pay.mapper.PayMapper;
import com.review7872.pay.pojo.Pay;
import com.review7872.pay.service.PayService;
import com.review7872.pay.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private PayMapper payMapper;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private SimpleDateFormat simpleDateFormat;
    @Override
    public List<Pay> selectAll() {
        return payMapper.selectAll();
    }

    @Override
    public List<Pay> selectByPayStat(byte payStat) {
        return payMapper.selectByPayStat(payStat);
    }

    @Override
    public List<Pay> selectByPayWay(byte payWay) {
        return payMapper.selectByPayWay(payWay);
    }

    @Override
    public Pay selectByPayId(long payId) {
        return payMapper.selectByPayId(payId);
    }

    @Override
    public Integer insertPay( byte payWay, byte payStat) {
        return payMapper.insertPay(snowflakeIdGenerator.nextId(),
                payWay,payStat,
                simpleDateFormat.format(new Date()));
    }

    @Override
    public Integer updatePayStat(byte payStat, long payId) {
        return payMapper.updatePayStat(payStat,payId);
    }
}
