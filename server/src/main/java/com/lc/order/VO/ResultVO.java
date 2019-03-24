package com.lc.order.VO;

import lombok.Data;

/**
 * @author lc
 * @date 2019/3/18
 */
@Data
public class ResultVO<T> {
    private  Integer code;

    private String msg;

    private T data;
}
