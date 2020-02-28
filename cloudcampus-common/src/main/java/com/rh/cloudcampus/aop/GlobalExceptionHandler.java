package com.rh.cloudcampus.aop;

import com.rh.cloudcampus.enums.RspStatusEnum;
import com.rh.cloudcampus.response.ObjectResponse;
import com.rh.cloudcampus.exception.DefaultException;
import com.rh.cloudcampus.exception.ParamException;
import com.rh.cloudcampus.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author liaocheng
 * @date: 2020-01-16 09:24
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LogManager.getLogger();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ObjectResponse exceptionHandler(Exception e){
        e.printStackTrace();
        ObjectResponse objectResponse = new ObjectResponse<>();
        objectResponse.setStatus(RspStatusEnum.FAIL.getCode());
        objectResponse.setMessage(RspStatusEnum.FAIL.getMessage());
        return objectResponse;
    }

    /**
     * 默认异常
     * @param e
     * @return
     */
    @ExceptionHandler(DefaultException.class)
    @ResponseBody
    public ObjectResponse defaultException(DefaultException e){
        e.printStackTrace();
        ObjectResponse objectResponse = new ObjectResponse<>();
        objectResponse.setStatus(RspStatusEnum.FAIL.getCode());
        objectResponse.setMessage(RspStatusEnum.FAIL.getMessage());
        return objectResponse;
    }

    /**
     * 自定义参数异常
     * @param e ParamException
     * @return
     */
    @ExceptionHandler(ParamException.class)
    @ResponseBody
    public ObjectResponse paramException(ParamException e){
        e.printStackTrace();
        ObjectResponse objectResponse = new ObjectResponse<>();
        objectResponse.setStatus(e.getRspStatusEnum().getCode());
        objectResponse.setMessage(e.getRspStatusEnum().getMessage());
        logger.error(String.format("参数异常, 错误码：%s，错误提示：%s，时间：%s", e.getRspStatusEnum().getCode(), e.getRspStatusEnum().getMessage(), CommonUtils.getNowFormatTime()));
        return objectResponse;
    }
}
