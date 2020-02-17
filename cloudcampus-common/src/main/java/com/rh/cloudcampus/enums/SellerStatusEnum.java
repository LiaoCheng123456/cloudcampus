package com.rh.cloudcampus.enums;

/**
 * @author liaocheng
 * @date: 2020-02-17 11:27
 */
public enum SellerStatusEnum {

    SHOP_OPEN(1, "开启"), SHOP_CLOSE(2, "关闭"), SHOP_DELETE(3, "删除");

    private int code;

    private String name;

    SellerStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }
}
