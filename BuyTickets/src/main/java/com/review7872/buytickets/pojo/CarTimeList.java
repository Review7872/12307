package com.review7872.buytickets.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarTimeList extends ArrayList<CarTime> implements Serializable {
    public CarTimeList getAllTime() {
        CarTimeList carTimes = this;
        carTimes.forEach(i -> i.setSeats(null));
        return carTimes;
    }

    public void setRealTime(String route, String readTime) {
        Optional<CarTime> opt = this.stream().filter(carTime -> route.equals(carTime.getRoute())).findFirst();
        if (opt.isPresent()) {
            CarTime carTime = opt.get();
            int i = this.indexOf(carTime);
            carTime.setRealTime(readTime);
            this.set(i, carTime);
        } else {
            throw new RuntimeException("未找到此站点");
        }
    }

    public void addCarTime(String route, String predictedTime, int seatNumS) {
        CarTime carTime = new CarTime();
        carTime.setRoute(route);
        carTime.setPredictedTime(predictedTime);
        List<SeatInfo> seatInfos = new ArrayList<>(seatNumS);
        carTime.setSeats(seatInfos);
        this.add(carTime);
    }

    /**
     * 这个方法的作用是在用户买票之后在用户坐车的站点内将座位设置为有人并且存入用户的证件号码
     *
     * @param beginRoute 用户起点
     * @param endRoute   用户终点
     * @param seatId     用户座位
     * @param cardId     用户证件号
     * @param seatList   车辆信息
     */
    public void setOcc(String beginRoute, String endRoute, String seatId, long cardId, SeatList seatList) {
        AtomicBoolean b = new AtomicBoolean(false);
        this.forEach(i -> {
            if (beginRoute.equals(i.getRoute())) {
                b.set(true);
            }
            if (b.get()) {
                if (endRoute.equals(i.getRoute())) {
                    b.set(false);
                }else {
                    int index = getIndex(seatId, seatList);
                    SeatInfo seatInfo = i.getSeats().get(index);
                    seatInfo.setOccupation(1);
                    seatInfo.setCardId(cardId);
                    i.getSeats().set(index, seatInfo);
                }
            }
        });
    }

    public void setOccBack(String beginRoute, String endRoute, String seatId, SeatList seatList) {
        AtomicBoolean b = new AtomicBoolean(false);
        this.forEach(i -> {
            if (beginRoute.equals(i.getRoute())) {
                b.set(true);
            }
            if (b.get()) {
                if (endRoute.equals(i.getRoute())) {
                    b.set(false);
                }else {
                    int index = getIndex(seatId, seatList);
                    SeatInfo seatInfo = i.getSeats().get(index);
                    seatInfo.setOccupation(0);
                    seatInfo.setCardId(0L);
                    i.getSeats().set(index, seatInfo);
                }
            }
        });
    }

    /**
     * 这个方法的作用是检查从 beginRoute 到 endRoute 这个区间 某个座位人的cardId
     *
     * @param beginRoute 起点
     * @param endRoute   终点
     * @param seatId     座位id
     * @param seatList   车辆信息
     * @return 返回0既是没人
     */
    public long getOcc(String beginRoute, String endRoute, String seatId, SeatList seatList) {
        AtomicBoolean b = new AtomicBoolean(false);
        for (CarTime carTime : this) {
            if (beginRoute.equals(carTime.getRoute())) {
                b.set(true);
            }
            if (b.get()) {
                if (endRoute.equals(carTime.getRoute())) {
                    b.set(false);
                }else {
                    int index = getIndex(seatId, seatList);
                    SeatInfo seatInfo = carTime.getSeats().get(index);
                    if (seatInfo.getOccupation() == 1) {
                        return seatInfo.getCardId();
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 返回从起点到站点所有的座位信息
     *
     * @param beginRoute 起点
     * @param endRoute   终点
     * @param seatList   列车类型
     * @return 很多的站点的座位信息
     */
    public List<Object> getRouteOcc(String beginRoute, String endRoute, SeatList seatList) {
        AtomicBoolean b = new AtomicBoolean(false);
        List<Object> list = new ArrayList<>();
        this.forEach(carTime -> {
            if (beginRoute.equals(carTime.getRoute())) {
                b.set(true);
            }
            if (b.get()) {
                List<SeatInfo> seats = carTime.getSeats();
                list.add(seats);
                if (endRoute.equals(carTime.getRoute())) {
                    b.set(false);
                }
            }
        });
        return list;
    }

    /**
     * 这个方法的作用是返回指定站点座位情况
     *
     * @param route    站点名
     * @param seatList 车辆信息
     * @return 座位情况
     */

    public List<SeatInfo> getAllOcc(String route, SeatList seatList) {
        Optional<CarTime> opt = this.stream().filter(carTime -> route.equals(carTime.getRoute())).findFirst();
        return opt.map(CarTime::getSeats).orElse(null);
    }

    /**
     * 返回指定站点指定座位的证件号
     *
     * @param route    站点
     * @param seatId   座位id
     * @param seatList 车辆类型
     * @return 证件号
     */
    public long getCardId(String route, String seatId, SeatList seatList) {
        List<SeatInfo> allOcc = getAllOcc(route, seatList);
        int index = getIndex(seatId, seatList);
        return allOcc.get(index).getCardId();
    }

    /**
     * 这个方法可以根据座位号与车辆信息，拿到这个座位在 List<SeatInfo> 中的下标
     *
     * @param seatId   座位号
     * @param seatList 车辆类型
     * @return 下标
     */
    public int getIndex(String seatId, SeatList seatList) {
        int j = 0;
        String seatLevel = seatId.substring(0, 1);
        int index = Integer.parseInt(seatId.substring(1));
        for (Seat seat : seatList) {
            if (seatLevel.equals(seat.getSeatLevel())) {
                j += index;
                return j;
            } else {
                j += seat.getNum();
            }
        }
        return j;
    }

}
