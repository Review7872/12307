package com.review7872.buytickets.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat implements Serializable {
    private String seatLevel;
    private Integer num;
    private Integer remainder;
    private Integer money;


}
