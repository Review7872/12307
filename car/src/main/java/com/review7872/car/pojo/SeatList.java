package com.review7872.car.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class SeatList extends ArrayList<Seat> implements Serializable {
    public int remainder(String seatLevel){
        Optional<Seat> opt = this.stream().filter(seat -> seatLevel.equals(seat.getSeatLevel())).findFirst();
        if (opt.isPresent()) {
            return opt.get().getRemainder();
        }else {
            throw new RuntimeException("未找到该等座位");
        }
    }
    public int num(String seatLevel){
        Optional<Seat> opt = this.stream().filter(seat -> seatLevel.equals(seat.getSeatLevel())).findFirst();
        if (opt.isPresent()) {
            return opt.get().getNum();
        }else {
            throw new RuntimeException("未找到该等座位");
        }
    }
    public int money(String seatLevel){
        Optional<Seat> opt = this.stream().filter(seat -> seatLevel.equals(seat.getSeatLevel())).findFirst();
        if (opt.isPresent()) {
            return opt.get().getMoney();
        }else {
            throw new RuntimeException("未找到该等座位");
        }
    }
    public int remainderBySeatId(String seatId){
        String seatLevel = seatId.substring(0,1);
        return remainder(seatLevel);
    }

}
