package com.lc.order.service;

import com.lc.order.dto.OrderDTO;

/**
 * @author lc
 * @date 2019/3/17
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
