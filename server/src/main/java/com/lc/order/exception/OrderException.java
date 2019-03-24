package com.lc.order.exception;

import com.lc.order.enums.ResultEnum;

/**
 * @author lc
 * @date 2019/3/18
 */
public class OrderException extends RuntimeException{
    private Integer code;

    public OrderException(Integer code,String message){
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getErrorMsg());
        this.code = resultEnum.getErrorCode();
    }
}
