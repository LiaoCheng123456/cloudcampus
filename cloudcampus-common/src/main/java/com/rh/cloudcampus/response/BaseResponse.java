package com.rh.cloudcampus.response;

import java.io.Serializable;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
public class BaseResponse implements Serializable {

    private int status = 200;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {
    }
}
