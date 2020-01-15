package com.rh.cloudcampus.response;

import com.rh.cloudcampus.enums.RspStatusEnum;

import java.io.Serializable;

public class ObjectResponse<T> extends BaseResponse implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ObjectResponse(int status, String message) {
        super(status, message);
    }

    public ObjectResponse() {
        super(RspStatusEnum.SUCCESS.getCode(), RspStatusEnum.SUCCESS.getMessage());
    }
}
