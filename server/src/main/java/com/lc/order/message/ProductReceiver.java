package com.lc.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.lc.order.dto.OrderDTO;
import com.lc.order.utils.JsonUtil;
import com.lc.product.common.ProdectInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lc
 * @date 2019/3/21
 */
@Component
@Slf4j
public class ProductReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void inputProcess(String message){
        //message => ProductInfoOutput
        List<ProdectInfoOutput> prodectInfoOutputList=(List<ProdectInfoOutput>)JsonUtil.fromJson(message, new TypeReference<List<ProdectInfoOutput>>() {});
        log.info("从队列【{}】接收到到消息:{}","productInfo",prodectInfoOutputList);

        //存储到redis中
        for (ProdectInfoOutput prodectInfoOutput : prodectInfoOutputList) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,prodectInfoOutput.getProductId()),
                    String.valueOf(prodectInfoOutput.getProductStock()));
        }

    }
}
