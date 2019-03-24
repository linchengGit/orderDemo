package com.lc.order.utils;

import com.lc.order.VO.ResultVO;

/**
 * @author lc
 * @date 2019/3/18
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return  resultVO;
    }
}
