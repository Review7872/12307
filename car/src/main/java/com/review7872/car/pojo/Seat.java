package com.review7872.car.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat implements Serializable {
    private Integer seatLevel;
    private String seatId;
    private Integer money;
    private Integer occupation;
    private Integer cardId;
}
