package com.rh.cloudcampus.utils;

/**
 * @author liaocheng
 * @date: 2020-02-17 15:06
 */
public class Validation {

    /**
     * 用户名验证 4 - 16 位
     */
    public static final String USERNAME = "[a-zA-Z0-9_-]{4,16}";

    public static final String PASSWORD = "(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";


    public static void main(String[] args) {
        System.out.println("1222".matches(USERNAME));
    }
}
