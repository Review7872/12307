package com.review7872.car.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarTime implements Serializable {
    private String route;
    private String predictedTime;
    private String realTime;
    private List<SeatInfo> seats;
}
