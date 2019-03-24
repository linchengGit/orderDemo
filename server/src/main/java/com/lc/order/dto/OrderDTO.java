package com.lc.order.dto;

import com.lc.order.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lc
 * @date 2019/3/17
 */
@Data
public class OrderDTO {

    /** 订单Id */
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态 */
    private Integer orderStatus;

    /** 支付状态 */
    private Integer payStatus;

    private List<OrderDetail> orderDetailList;
}
