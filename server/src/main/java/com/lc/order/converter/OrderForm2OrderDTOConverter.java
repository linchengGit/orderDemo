package com.lc.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lc.order.dataobject.OrderDetail;
import com.lc.order.dto.OrderDTO;
import com.lc.order.enums.ResultEnum;
import com.lc.order.exception.OrderException;
import com.lc.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lc
 * @date 2019/3/18
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTo = new OrderDTO();
        orderDTo.setBuyerName(orderForm.getName());
        orderDTo.setBuyerPhone(orderForm.getPhone());
        orderDTo.setBuyerAddress(orderForm.getAddress());
        orderDTo.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【json转换】错误，string={}",orderForm.getItems());
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
        orderDTo.setOrderDetailList(orderDetailList);

        return  orderDTo;
    }
}
