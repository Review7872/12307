package com.review7872.car.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CarTimeList extends ArrayList<CarTime> implements Serializable {
    public void setRealTime(String route,String readTime){
        Optional<CarTime> opt = this.stream().filter(carTime -> route.equals(carTime.getRoute())).findFirst();
        if (opt.isPresent()){
            CarTime carTime = opt.get();
            int i = this.indexOf(carTime);
            carTime.setRealTime(readTime);
            this.set(i,carTime);
        }else {
            throw new RuntimeException("未找到此站点");
        }
    }
    public void addCarTime(String route,String predictedTime,int seatNumS){
        CarTime carTime = new CarTime();
        carTime.setRoute(route);
        carTime.setPredictedTime(predictedTime);
        List<SeatInfo> seatInfos = new ArrayList<>(seatNumS);
        carTime.setSeats(seatInfos);
        this.add(carTime);
    }
    public void setOcc(String beginRoute,String endRoute,String seatId,long cardId,SeatList seatList){
        AtomicBoolean b = new AtomicBoolean(false);
        this.forEach(i->{
            if (beginRoute.equals(i.getRoute())){
                b.set(true);
            }
            if (b.get()){
                int j = 0;
                String seatLevel = seatId.substring(0, 1);
                for (Seat seat : seatList) {
                    if (seatLevel.equals(seat.getSeatLevel())) {
                        break;
                    } else {
                        j += seat.getNum();
                    }
                }
                SeatInfo seatInfo = i.getSeats().get(j);
                seatInfo.setOccupation(1);
                seatInfo.setCardId(cardId);
                i.getSeats().set(j,seatInfo);
                if (endRoute.equals(i.getRoute())){
                    b.set(false);
                }
            }
        });
    }
}
