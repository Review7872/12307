package com.review7872.buytickets.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    private Long carId;
    private String route;
    private String carNum;
    private Integer open;
}
