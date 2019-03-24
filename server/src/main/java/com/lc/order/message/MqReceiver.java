package com.lc.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lc
 * @date 2019/3/21
 */
@Slf4j
@Component
public class MqReceiver {

    //1.    @RabbitListener(queues = "myQueue")
    //2. 自动创建队列@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3. 自动创建，Exchange和Queue
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("MqReceiver:{}",message);
    }

    /**
     * 数码
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key="computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message){
        log.info("computer MqReceiver:{}",message);
    }

    /**
     * 水果
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key="fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruit(String message){
        log.info("fruit MqReceiver:{}",message);
    }
}
