package com.rh.cloudcampus.utils;

import com.rh.cloudcampus.dto.TSeller;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author liaocheng
 * @date: 2020-02-17 11:39
 */
public class CommonUtils {

    /**
     * 时间格式化 年-月-日 时:分:秒
     */
    private static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 一亿
     */
    private static final long MAX = 100000000;

    /**
     * 年月日
     */
    private static final String YMD = "yyMMdd";

    /**
     * 当前时间戳
     */
    private static final Long NOWTIME = System.currentTimeMillis();

    /**
     * 获取格式化过后的当前时间
     *
     * @return 2020-02-17 11:44:31
     */
    public static String getNowFormatTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMDHMS);
        return simpleDateFormat.format(NOWTIME);
    }

    /**
     * 获取当前时间戳
     *
     * @return 1581911390
     */
    public static Long getTimeStamp() {
        return NOWTIME / 1000;
    }

    /**
     * 获取id
     *
     * @return
     */
    public static Long getId() {
        String ipAddress = "";

        try {
            //获取服务器IP地址
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String uuid = ipAddress + "$" + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        long suffix = Math.abs(uuid.hashCode() % MAX);

        SimpleDateFormat sdf = new SimpleDateFormat(YMD);

        String time = sdf.format(new Date(System.currentTimeMillis()));

        long prefix = Long.parseLong(time) * MAX;

        return Long.parseLong(String.valueOf(prefix + suffix));
    }

    /**
     * md5 工具
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            StringBuilder buf = new StringBuilder("");

            for (int b1 : b) {
                int i = b1;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return str;
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }
}
