package com.rh.cloudcampus.edu.util.push.umengPush;

/**
 * Created by gb on 2016/12/5.
 */
public enum UmengEnums {
    IOS_APP_KEY("585369944ad15671d40007aa"), IOS_APP_MASTER_SECRET("d6zwoerydb1ewxhdp7pll2odxuc3ycjj"), ANDROID_APP_KEY("585367d98f4a9d2a9d000ca9"), ANDROID_APP_MASTER_SECRET(
            "xuf4dpdzmcp7yplybwji2fdcuryyawjq");
    String info;

    UmengEnums(String info) {
        this.info = info;
    }

    public String get() {
        return this.info;
    }
}