package com.lc.order.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author lc
 * @date 2019/3/18
 */
@Getter
public enum ResultEnum {
    PARAM_ERROR(1,"参数错误"),
    PARAM_EMPTY(2,"购物车为空"),
    ORDER_NOT_EXIST(3,"订单不存在"),
    ORDER_STATUS_ERROR(4,"订单状态错误"),
    ORDER_DETAIL_NOT_EXIST(5,"订单详情不存在"),
        ;

    private Integer ErrorCode;

    private String ErrorMsg;

    ResultEnum(Integer errorCode, String errorMsg) {
        ErrorCode = errorCode;
        ErrorMsg = errorMsg;
    }
}
