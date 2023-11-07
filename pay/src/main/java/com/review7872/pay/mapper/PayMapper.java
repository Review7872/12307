package com.review7872.pay.mapper;

import com.review7872.pay.pojo.Pay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PayMapper {
    @Select("")
    @Results(id = "payRes", value = {
            @Result(id = true, column = "pay_id", property = "payId"),
            @Result(column = "pay_way", property = "payWay"),
            @Result(column = "pay_stat", property = "payStat"),
            @Result(column = "pay_time", property = "payTime")
    })
    Pay payRes();

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay
            """)
    @ResultMap("payRes")
    List<Pay> selectAll();

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay where pay_stat = #{payStat}
            """)
    @ResultMap("payRes")
    List<Pay> selectByPayStat(byte payStat);

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay where pay_way = #{payWay}
            """)
    @ResultMap("payRes")
    List<Pay> selectByPayWay(byte payWay);

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay where pay_id = #{payId}
            """)
    @ResultMap("payRes")
    Pay selectByPayId(long payId);

    @Insert("""
            insert into pay values(#{payId},#{payWay},#{payStat},#{payTime})
            """)
    Integer insertPay(long payId, byte payWay, byte payStat, String payTime);

    @Update("""
            update pay set pay_stat = #{payStat} where pay_id = #{payId}
            """)
    Integer updatePayStat(byte payStat, long payId);
}
