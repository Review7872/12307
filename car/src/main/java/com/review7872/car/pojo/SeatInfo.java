package com.review7872.car.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatInfo implements Serializable {
    private Integer occupation;
    private Long cardId;
}
