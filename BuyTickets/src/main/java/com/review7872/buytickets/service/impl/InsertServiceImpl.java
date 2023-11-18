package com.review7872.buytickets.service.impl;

import com.review7872.buytickets.feign.CarFeign;
import com.review7872.buytickets.feign.OrderFeign;
import com.review7872.buytickets.feign.PayFeign;
import com.review7872.buytickets.service.InsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertServiceImpl implements InsertService {
    @Autowired
    private CarFeign carFeign;
    private OrderFeign orderFeign;
    private PayFeign payFeign;
    public int buyTicketSale(long cardId,long carId,String seatId,String beginRoute,String endRoute){
        long orderId = orderFeign.insert(cardId, carId, seatId);
        if (orderId < 1){
            throw new RuntimeException("order组件异常");
        }
        int carTF = carFeign.buyTicket(carId, beginRoute,endRoute,cardId, seatId);
        if (carTF != 1 ){
            throw new RuntimeException("car组件异常");
        }
        long payId = payFeign.insertPay();
        if (payId < 1){
            throw new RuntimeException("pay组件异常");
        }
        Integer i = orderFeign.updatePay(payId, orderId);
        if (i == 1) {
            return 1;
        }else {
            return 0;
        }
    }
}
