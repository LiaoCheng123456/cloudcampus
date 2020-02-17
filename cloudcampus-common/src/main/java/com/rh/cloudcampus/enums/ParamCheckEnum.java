package com.rh.cloudcampus.enums;

public enum ParamCheckEnum {

    /**
     * param_exception rsp status enum
     */
    PARAM_LACK(10000, "参数不齐"),

    /**
     * 密码弱口令
     */
    WEAK_PASSWORD(10001, "密码弱口令"),

    /**
     * 长度不够
     */
    LENGTH_FAILED(10002, "参数长度不够"),

    /**
     * 用户名格式错误
     */
    USERNAME_LENGTH_FAILED(10003, "用户名格式错误"),

    /**
     * 密码错误
     */
    PASSWORD_FAILED(10004, "密码错误"),

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS(10005, "用户名已存在");

    private int code;

    private String message;

    ParamCheckEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
