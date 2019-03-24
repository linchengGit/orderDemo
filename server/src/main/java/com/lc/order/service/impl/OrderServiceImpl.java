package com.lc.order.service.impl;

import com.lc.order.dataobject.OrderDetail;
import com.lc.order.dataobject.OrderMaster;
import com.lc.order.dto.OrderDTO;
import com.lc.order.enums.OrderStatusEnum;
import com.lc.order.enums.PayStatusEnum;
import com.lc.order.enums.ResultEnum;
import com.lc.order.exception.OrderException;
import com.lc.order.repository.OrderDetailRepository;
import com.lc.order.repository.OrderMasterRepository;
import com.lc.order.service.OrderService;
import com.lc.order.utils.KeyUtils;
import com.lc.product.client.ProductClient;
import com.lc.product.common.DecreaseStockInput;
import com.lc.product.common.ProdectInfoOutput;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lc
 * @date 2019/3/17
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtils.genUniqueKey();
        // 查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProdectInfoOutput> prodectInfoOutputList = productClient.listForOrder(productIdList);
        /**
         * redis锁
         * {
         *     读redis
         *     减库存并将新值重新设置redis
         *
         *
         *
         * }
         *
         * 订单详情入库异常，手动回滚redis
         */




        //T 计算总价
        BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProdectInfoOutput prodectInfoOutput : prodectInfoOutputList) {
                if (orderDetail.getProductId().equals(prodectInfoOutput.getProductId())) {
                    //单价 * 数量
                    totalAmount = prodectInfoOutput.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(totalAmount);
                    BeanUtils.copyProperties(prodectInfoOutput,orderDetail);
                    orderDetail.setDetailId(KeyUtils.genUniqueKey());
                    orderDetail.setOrderId(orderId);
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        // 扣库存(调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(totalAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1.先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if(!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if(orderMaster.getOrderStatus()!=OrderStatusEnum.NEW.getCode()){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);
        //查询订单详情
        List<OrderDetail> orderDetalList = orderDetailRepository.findByOrderId(orderId);
        if(orderDetalList.isEmpty()){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        //组拼结果
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetalList);

        return orderDTO;
    }


}
