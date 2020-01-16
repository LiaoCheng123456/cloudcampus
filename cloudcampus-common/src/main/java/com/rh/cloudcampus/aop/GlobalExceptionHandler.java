package com.rh.cloudcampus.aop;

import com.rh.cloudcampus.enums.RspStatusEnum;
import com.rh.cloudcampus.exception.DefaultException;
import com.rh.cloudcampus.response.ObjectResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liaocheng
 * @date: 2020-01-16 09:24
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ObjectResponse exceptionHandler(Exception e){

        ObjectResponse objectResponse = new ObjectResponse<>();
        objectResponse.setStatus(RspStatusEnum.FAIL.getCode());
        objectResponse.setMessage(RspStatusEnum.FAIL.getMessage());
        return objectResponse;
    }

    @ExceptionHandler(DefaultException.class)
    @ResponseBody
    public ObjectResponse defaultException(DefaultException e){
        ObjectResponse objectResponse = new ObjectResponse<>();
        objectResponse.setStatus(RspStatusEnum.FAIL.getCode());
        objectResponse.setMessage(RspStatusEnum.FAIL.getMessage());
        return objectResponse;
    }
}
