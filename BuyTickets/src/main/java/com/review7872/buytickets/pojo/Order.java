package com.review7872.buytickets.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Long orderId;
    private Long cardId;
    private Long carId;
    private String seatId;
    private Long payId;
    private String orderTime;
}
