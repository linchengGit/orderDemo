package com.lc.order.controller;

import com.lc.order.VO.ResultVO;
import com.lc.order.converter.OrderForm2OrderDTOConverter;
import com.lc.order.dto.OrderDTO;
import com.lc.order.enums.ResultEnum;
import com.lc.order.exception.OrderException;
import com.lc.order.form.OrderForm;
import com.lc.order.service.OrderService;
import com.lc.order.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lc
 * @date 2019/3/17
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 1.参数考验
     * 2.查询商品信息(调用商品服务)
     * 3.计算总价
     * 4.扣库存(调用商品服务)
     * 5.订单入库
     */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
             log.error("【创建订单】参数不正确，orderForm={}",orderForm);
             throw new OrderException(ResultEnum.PARAM_ERROR.getErrorCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        //orderForm ->orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(orderDTO.getOrderDetailList().isEmpty()){
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.PARAM_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);
    }


    @PostMapping("/finish")
    public ResultVO<OrderDTO> finish(@RequestParam("orderId")String orderId){
        return ResultVOUtil.success(orderService.finish(orderId));
    }
}