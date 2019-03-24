package com.lc.order.message;

import com.lc.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lc
 * @date 2019/3/21
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.OUT)
    public String inputProcess(OrderDTO orderDTO){
        log.info("stream In msg:"+orderDTO);
        return "ok";
    }

    @StreamListener(StreamClient.OUT)
    public void outProcess(String result){
        log.info("stream out msg:"+result);
    }

}
