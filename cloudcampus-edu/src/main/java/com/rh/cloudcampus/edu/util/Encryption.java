package com.rh.cloudcampus.edu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 数据加密和解密
 *
 * @author rs
 */
public class Encryption {

    static Random random=new Random();
    /**
     *  生成随机的key
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str="abcdefghjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[]chars=str.toCharArray();
        List<Character> resultList = new ArrayList<>(chars.length);
        for (char s : chars) {
            resultList.add(s);
        }
        return disruption(resultList,"",length);
    }
    /**
     * 打乱顺序
     * @param input
     * @param outStr
     * @param length
     * @return
     */
    public static String disruption(List<Character> input, String outStr, int length){
        if(input.size()>0&& outStr.length()<length){
            int number=random.nextInt(input.size());
            outStr=outStr+input.get(number).toString();
            input.remove(number);
            return disruption(input,outStr,length);
        }else{
            return outStr;
        }
    }
    /**
     * 将字符串转换为数字数组
     * @param str
     * @return
     */
    public static String toBinary(String str) {
        //把字符串转成字符数组
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            String s = Integer.toBinaryString(strChar[i]) + " ";
            result += s;
        }
        return result;
    }

    /**
     * 加密
     * @param plaintext
     * @param key
     * @return
     */
    public static String encryption(String plaintext,String key) {
        String[] tempStr = toBinary(plaintext).split(" ");
        StringBuffer re = new StringBuffer();
        for (int i = 0; i < tempStr.length; i++) {
            int intout = BinstrToChar(tempStr[i]);
            String str4 =transRadix(key,String.valueOf(intout));
            re.append(str4+"i");
        }
        return String.valueOf(re);
    }

    /**
     * 解密
     * @param ciphertext
     * @param key
     * @return
     */
    public static String decrypt(String ciphertext,String key) {
        StringBuffer re = new StringBuffer();
        String[] outStrs=ciphertext.split("i");
        for(int i=0;i< outStrs.length;i++) {
            int num =radixTrans(key,outStrs[i]);
            re.append((char)num);
        }
        return re.toString();
    }

    //将二进制字符串转换成int数组
    public static int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    /**
     * 将字符转换为二进制
     * @param binStr
     * @return
     */
    public static int BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return sum;
    }
    /**
     * 转换方法
     *
     * @param num       元数据字符串
     * @return
     */
    public static String transRadix(String chstr, String num) {
        char[] chs = chstr.toCharArray();
        int number = Integer.valueOf(num,10);
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append(chs[number % chs.length]);
            number = number / chs.length;
        }
        return sb.reverse().toString();

    }
    /**
     * 转换方法
     * @param num       元数据字符串
     * @return
     */
    public static int radixTrans(String chstr, String num) {
        char[] chs = chstr.toCharArray();
        int re=0;
        char[] nums = num.toCharArray();
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<chs.length;j++){
                if(nums[i]==chs[j]){
                    re=re+j*power(chs.length,nums.length-i-1);
                }
            }
        }
        return re;
    }
    /**
     * 幂运算
     * @param i
     * @param j
     * @return
     */
    private static int power(int i, int j) {
        int y=0;
        if(j==0){
            y=1;
        }else{
            y=power(i,j/2);
            y=y*y;
            if(j%2!=0){
                y=i*y;
            }
        }
        return y;
    }

}

