package com.review7872.pay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pay implements Serializable {
    private Long payId;
    private Byte payWay;
    private Byte payStat;
    private String payTime;
}
