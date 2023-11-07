package com.review7872.pay.service;

import com.review7872.pay.pojo.Pay;

import java.util.List;

public interface PayService {
    List<Pay> selectAll();

    List<Pay> selectByPayStat(byte payStat);

    List<Pay> selectByPayWay(byte payWay);

    Pay selectByPayId(long payId);

    Integer insertPay( byte payWay, byte payStat);

    Integer updatePayStat(byte payStat,long payId);
}
