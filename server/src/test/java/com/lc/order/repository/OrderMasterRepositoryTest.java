package com.lc.order.repository;

import com.lc.order.OrderApplicationTests;
import com.lc.order.dataobject.OrderMaster;
import com.lc.order.enums.OrderStatusEnum;
import com.lc.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lc
 * @date 2019/3/17
 */
@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12344");
        orderMaster.setBuyerName("电脑支架");
        orderMaster.setBuyerPhone("13265960794");
        orderMaster.setBuyerAddress("中山路");
        orderMaster.setBuyerOpenid("1234124");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreateTime(new Date());
        OrderMaster result = orderMasterRepository.save(orderMaster);

        Assert.assertTrue(result != null);
    }

}