package com.review7872.pay.service;

import com.review7872.pay.pojo.Pay;

import java.util.List;

public interface PayService {
    List<Pay> selectAll();

    List<Pay> selectByPayStat(byte payStat);

    List<Pay> selectByPayWay(byte payWay);

    List<Pay> selectByPayWayAndPayStat(byte payWay, byte payStat);

    Pay selectByPayId(long payId);

    long insertPay();

    Integer updatePayStat(byte payStat, long payId);

    Integer updatePayWay(byte payWay, long payId);

    Integer updatePayWayAndPayStat(byte payWay, byte payStat, long payId);
}
