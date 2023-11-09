package com.review7872.car.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

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
    public void addCarTime(String route,String predictedTime){
        CarTime carTime = new CarTime();
        carTime.setRoute(route);
        carTime.setPredictedTime(predictedTime);
        this.add(carTime);
    }
}
