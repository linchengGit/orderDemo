package com.lc.order.controller;

import com.lc.order.dto.OrderDTO;
import com.lc.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author lc
 * @date 2019/3/21
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void process(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("234324");
        String msg = "now "+new Date();
        streamClient.input().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
