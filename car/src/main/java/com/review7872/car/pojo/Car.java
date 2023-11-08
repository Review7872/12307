package com.review7872.car.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Long carId;
    private String route;
    private String car_num;
    private Integer open;
    private Integer firstClass;
    private Integer firstClassRem;
    private Integer firstMoney;
    private Integer secondClass;
    private Integer secondClassRem;
    private Integer secondMoney;
}
