package com.review7872.car.mapper;

import com.review7872.car.pojo.Car;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarMapper {
    @Select("")
    @Results(id = "carRes", value = {
            @Result(id = true, column = "car_id", property = "carId"),
            @Result(column = "route", property = "route"),
            @Result(column = "car_num", property = "carNum"),
            @Result(column = "open", property = "open")
    })
    Car carRes();

    @Select("""
            select car_id,route,car_num,open from car
            """)
    @ResultMap("carRes")
    List<Car> selectAll();

    @Select("""
            select car_id,route,car_num,open from car where open = #{open}
            """)
    @ResultMap("carRes")
    List<Car> selectAllByOpen(int open);

    @Select("""
            select car_id,route,car_num,open from car where open = #{open} and car_num = #{carNum}
            """)
    @ResultMap("carRes")
    List<Car> selectAllByOpenAndCarNum(int open, String carNum);

    @Select("""
            select car_id,route,car_num,open from car where route like '%'#{beginRoute}'%'#{endRoute}'%'
            """)
    @ResultMap("carRes")
    List<Car> selectAllByRoute(String beginRoute, String endRoute);

    @Select("""
            select car_id,route,car_num,open from car where car_id = #{carId}
            """)
    @ResultMap("carRes")
    Car selectOne(long carId);

    @Insert("""
            insert into car values(#{carId},#{route},#{carNum},#{open})
            """)
    int insertCar(long carId, String route, String carNum, int open);

    @Update("""
            update car set route = #{route} where car_id = #{carId}
            """)
    int updateRoute(String route, long carId);
    @Update("""
            update car set car_num = #{carNum} where car_id = #{carId}
            """)
    int updateCarNum(String carNum, long carId);
    @Update("""
            update car set open = #{open} where car_id = #{carId}
            """)
    int updateOpen(String open, long carId);
}
