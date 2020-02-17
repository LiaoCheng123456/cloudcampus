package com.rh.cloudcampus.exception;

import com.rh.cloudcampus.enums.ParamCheckEnum;

/**
 * @author liaocheng
 * @date: 2020-02-17 15:21
 */
public class ParamException extends RuntimeException{

    private ParamCheckEnum paramCheckEnum;

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(ParamCheckEnum rspStatusEnum) {
        super(rspStatusEnum.getMessage());
        this.paramCheckEnum = rspStatusEnum;
    }

    public ParamException(ParamCheckEnum rspStatusEnum, Throwable cause) {
        super(rspStatusEnum.getMessage(), cause);
        this.paramCheckEnum = rspStatusEnum;
    }

    public ParamCheckEnum getRspStatusEnum() {
        return paramCheckEnum;
    }

    public void setRspStatusEnum(ParamCheckEnum rspStatusEnum) {
        this.paramCheckEnum = rspStatusEnum;
    }
}
