package com.review7872.pay.service.impl;

import com.review7872.pay.mapper.PayMapper;
import com.review7872.pay.pojo.Pay;
import com.review7872.pay.service.PayService;
import com.review7872.pay.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PayServiceImpl implements PayService {
    @Autowired
    private PayMapper payMapper;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private SimpleDateFormat simpleDateFormat;

    /**
     * 查询所有支付订单
     *
     * @return 查询结果
     */
    @Override
    public List<Pay> selectAll() {
        return payMapper.selectAll();
    }

    /**
     * 根据支付状态查询所有支付订单
     *
     * @param payStat 支付状态
     * @return 查询结果
     */
    @Override
    public List<Pay> selectByPayStat(byte payStat) {
        if (payStat > 3 || payStat < 0) {
            throw new RuntimeException("无效的支付状态");
        }
        return payMapper.selectByPayStat(payStat);
    }

    /**
     * 根据支付方式查询所有支付订单
     *
     * @param payWay 支付方式
     * @return 查询结果
     */
    @Override
    public List<Pay> selectByPayWay(byte payWay) {
        if (payWay > 2 || payWay < 1) {
            throw new RuntimeException("无效的支付方式");
        }
        return payMapper.selectByPayWay(payWay);
    }

    /**
     * 根据支付方式与支付结果查询
     *
     * @param payWay  支付方式
     * @param payStat 支付结果
     * @return 查询结果
     */
    @Override
    public List<Pay> selectByPayWayAndPayStat(byte payWay, byte payStat) {
        if (payWay > 2 || payWay < 1) {
            throw new RuntimeException("无效的支付方式");
        }
        if (payStat > 3 || payStat < 0) {
            throw new RuntimeException("无效的支付状态");
        }
        return payMapper.selectByPayWayAndPayStat(payWay, payStat);
    }

    /**
     * 根据支付单号查询
     *
     * @param payId 单号
     * @return 查询结果
     */
    @Override
    public Pay selectByPayId(long payId) {
        if (payId < 1) {
            throw new RuntimeException("无效的支付单号");
        }
        return payMapper.selectByPayId(payId);
    }

    /**
     * 新建支付订单
     *
     * @return 新建的支付单号
     */
    @Override
    public long insertPay() {
        long l = snowflakeIdGenerator.nextId();
        Integer i = payMapper.insertPay(l);
        if (i != 1) {
            throw new RuntimeException("新建支付订单失败");
        }
        return l;
    }

    /**
     * 修改支付状态
     *
     * @param payStat 支付状态
     * @param payId   支付单号
     * @return 是否成功
     */
    @Override
    public Integer updatePayStat(byte payStat, long payId) {
        if (payStat > 3 || payStat < 0) {
            throw new RuntimeException("无效的支付状态");
        }
        if (payId < 1) {
            throw new RuntimeException("无效的支付单号");
        }
        return payMapper.updatePayStat(payStat, payId, simpleDateFormat.format(new Date()));
    }

    /**
     * 修改支付方式
     *
     * @param payWay 支付方式
     * @param payId  支付单号
     * @return 是否成功
     */
    @Override
    public Integer updatePayWay(byte payWay, long payId) {
        if (payWay > 2 || payWay < 1) {
            throw new RuntimeException("无效的支付方式");
        }
        if (payId < 1) {
            throw new RuntimeException("无效的支付单号");
        }
        return payMapper.updatePayWay(payWay, payId);
    }

    /**
     * 根据支付单号修改支付方式与支付结果
     *
     * @param payWay  支付方式
     * @param payStat 支付结果
     * @param payId   支付单号
     * @return 是否成功
     */
    @Override
    public Integer updatePayWayAndPayStat(byte payWay, byte payStat, long payId) {
        if (payWay > 2 || payWay < 1) {
            throw new RuntimeException("无效的支付方式");
        }
        if (payStat > 3 || payStat < 0) {
            throw new RuntimeException("无效的支付状态");
        }
        if (payId < 1) {
            throw new RuntimeException("无效的支付单号");
        }
        return payMapper.updatePayWayAndPayStat(payWay, payStat, payId);
    }
}
