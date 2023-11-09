package com.review7872.car.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatList extends ArrayList<Seat> implements Serializable {
    public long firstSeatNum() {

        return this.stream().filter(seat -> 1 == seat.getSeatLevel()).count();

    }

    public long secondSeatNum() {
        return this.stream().filter(seat -> 2 == seat.getSeatLevel()).count();

    }

    public long thirdSeatNum() {
        return this.stream().filter(seat -> 3 == seat.getSeatLevel()).count();

    }

    public int firstMoney() {
        Optional<Seat> first = this.stream().filter(seat -> 1 == seat.getSeatLevel()).findFirst();
        if (first.isPresent()) {
            Seat seat = first.get();
            return seat.getMoney();
        } else {
            throw new RuntimeException("未找到此等座");
        }
    }

    public int secondMoney() {
        Optional<Seat> second = this.stream().filter(seat -> 2 == seat.getSeatLevel()).findFirst();
        if (second.isPresent()) {
            Seat seat = second.get();
            return seat.getMoney();
        } else {
            throw new RuntimeException("未找到此等座");
        }
    }

    public int thirdMoney() {
        Optional<Seat> third = this.stream().filter(seat -> 3 == seat.getSeatLevel()).findFirst();
        if (third.isPresent()) {
            Seat seat = third.get();
            return seat.getMoney();
        } else {
            throw new RuntimeException("未找到此等座");
        }
    }

    public int isOccupation(String seatId) {
        Optional<Seat> opt = this.stream().filter(seat -> seatId.equals(seat.getSeatId())).findFirst();
        if (opt.isPresent()) {
            Seat seat = opt.get();
            return seat.getOccupation();
        } else {
            throw new RuntimeException("未找到此座位");
        }
    }

    public int occupationCardId(String seatId) {
        Optional<Seat> opt = this.stream().filter(seat -> seatId.equals(seat.getSeatId())).findFirst();
        if (opt.isPresent()) {
            Seat seat = opt.get();
            return seat.getCardId();
        } else {
            throw new RuntimeException("未找到此座位");
        }

    }

    public void occupation(String seatId) {
        Optional<Seat> opt = this.stream().filter(seat -> seatId.equals(seat.getSeatId())).findFirst();
        if (opt.isPresent()) {
            Seat seat = opt.get();
            int i = this.indexOf(seat);
            seat.setOccupation(1);
            this.set(i, seat);
        } else {
            throw new RuntimeException("未找到此座位");
        }
    }

    public void addSeat(Integer seatLevel, String seatId, Integer money) {
        Seat seat = new Seat();
        seat.setSeatLevel(seatLevel);
        seat.setSeatId(seatId);
        seat.setMoney(money);
        this.add(seat);
    }
    public void setOccupation(String oldSeat,String newSeat){
        Optional<Seat> oldS = this.stream().filter(seat -> oldSeat.equals(seat.getSeatId())).findFirst();
        Optional<Seat> newS = this.stream().filter(seat -> newSeat.equals(seat.getSeatId())).findFirst();
        if (oldS.isPresent()&&newS.isPresent()) {
            Seat oldSe = oldS.get();
            Seat newSe = newS.get();
            int oldIndex = this.indexOf(oldSe);
            int newIndex = this.indexOf(newSe);
            oldSe.setOccupation(0);
            newSe.setOccupation(1);
            this.set(oldIndex,oldSe);
            this.set(newIndex,newSe);
        } else {
            throw new RuntimeException("未找到此座位");
        }
    }

    public List<Seat> getNull() {
        return this.stream().filter(seat -> 0 == seat.getOccupation()).toList();
    }
}
