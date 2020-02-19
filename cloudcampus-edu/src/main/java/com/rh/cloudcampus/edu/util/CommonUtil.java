package com.rh.cloudcampus.edu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rh.cloudcampus.edu.model.PageData;

import com.rh.cloudcampus.edu.util.Const;
import com.rh.cloudcampus.edu.util.DoGet;
import com.rh.cloudcampus.edu.util.ParameterUtil;
import com.wsp.utils.WSPDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {

    private String secret = "MTIyMzMyMzQzNDU1";

    protected static Logger logger = LogManager.getLogger();

    protected JSONObject json = new JSONObject();



    /**
     * 统一异常打logo返回方法
     */
    public static void addLogger(String methodName, Object parameter, Object e) {
        String errorStr = "执行方法" + methodName;
        if (!ParameterUtil.getResult(parameter)) {
            errorStr = errorStr + "参数为：" + JSON.toJSONString(parameter);
        }
        if (!ParameterUtil.getResult(e)) {
            errorStr = errorStr + "出现异常：" + JSON.toJSONString(e);
        }
        logger.error(errorStr);
    }

    /**
     *  将一个层级机构，转换为list
     */
    public List<PageData> childrenToList(PageData inputData) {
        List<PageData> resultList = new ArrayList<>();
        if (!ParameterUtil.getResult(inputData.get("children")) && !(inputData.get("children").toString()).equals("[]")) {
            List<PageData> childrenList = JSON.parseArray(JSON.toJSONString(inputData.get("children")), PageData.class);
            for (PageData children : childrenList) {
                children.put("pid", inputData.get("id"));
                List<PageData> childrens = this.childrenToList(children);
                resultList.removeAll(childrens);
                resultList.addAll(childrens);
                children.remove("children");
                resultList.remove(children);
                resultList.add(children);
            }
            inputData.remove("children");
            resultList.remove(inputData);
            resultList.add(inputData);
        } else {
            resultList.remove(inputData);
            resultList.add(inputData);
        }
        return resultList;
    }

    /**
     * 将一个list转换为层级结构
     */
    public List<PageData> listToChildren(List<PageData> inputList, String pid) {
        List<PageData> resultList = new ArrayList<>();
        if (inputList.size() > 0) {
            for (PageData input : inputList) {
                if (pid == null) {
                    if (ParameterUtil.getResult(input.get("pid"))) {
                        PageData oneChildren = input;
                        List<PageData> childrens = this.listToChildren(inputList, input.get("id").toString());
                        if (childrens.size() > 0) {
                            oneChildren.put("children", childrens);
                        }
                        resultList.add(oneChildren);
                    }
                } else {
                    if (!ParameterUtil.getResult(input.get("pid"))) {
                        if ((input.get("pid").toString()).equals(pid)) {
                            PageData oneChildren = input;
                            List<PageData> childrens = this.listToChildren(inputList, input.get("id").toString());
                            if (childrens.size() > 0) {
                                oneChildren.put("children", childrens);
                            }
                            resultList.add(oneChildren);
                        }
                    }
                }
            }
        }
        return resultList;
    }


    /**
     * 七六云持久化图片
     */
    public String savePc(String imgName) {
        return Const.QINIU_URL + imgName;
//        String qiniuString = qiniuUtil.Persistent(imgName);
////        if ("SUCCESS".equals(qiniuString)) {
////            return Const.QINIU_URL + imgName;
////        } else {
////            return JSON.toJSONString(ResultUtils.getFAILResult(WSPCode.FAIL, "七牛持久化失败"));
////        }
    }

    /**
     * 比较两个对象，将两个对象不同的参数返回
     */
    public List<PageData> comparisonObject(PageData oldOb, PageData newOb) {

        List<PageData> result = new ArrayList<>();
        for (Object key : oldOb.keySet()) {
            if (ParameterUtil.getResult(newOb.get(key))) {
                PageData re = new PageData();
                re.put("key", key);
                re.put("oldValue", oldOb.get(key));
                re.put("newValue", null);
                result.add(re);
            } else {
                if (!oldOb.get(key).equals(newOb.get(key))) {
                    PageData re = new PageData();
                    re.put("key", key);
                    re.put("oldValue", oldOb.get(key));
                    re.put("newValue", newOb.get(key));
                    result.add(re);
                }
            }
        }
        for (Object key : newOb.keySet()) {
            if (ParameterUtil.getResult(oldOb.get(key))) {
                PageData re = new PageData();
                re.put("key", key);
                re.put("oldValue", null);
                re.put("newValue", newOb.get(key));
                result.add(re);
            }
        }
        return result;
    }

    /**
     * hash MD5
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (string.isEmpty()) {
            return "";
        }
        StringBuffer buf = null;
        MessageDigest md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    /**
     * 将日期和时间字符串拼接起来，转换成long型
     *
     * @param day     string 日期
     * @param daytime string 时辰
     * @return long
     */
    public long getTime(String day, String daytime) {
        String time;
        if (daytime.length() == 5) {
            time = day + " " + daytime + ":00";
        } else {
            time = day + " " + daytime;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lTime = 0;
        try {
            Date date = sdf.parse(time);
            lTime = date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("执行方法getTime,参数为：" + day + daytime + "发生错误" + e);
        }
        return lTime;
    }


    /**
     * 获取一个时间段的第一天的日期
     */

    public String getStartDay(String time) {
        if (time.length() == 4) {
            return time + "-01-01";
        }
        ////如果time长度为7表示，time输入的是年和月
        else if (time.length() == 7) {
            return time + "-01";
        }
        ////如果time长度为7表示，time输入的是年月日
        return time;
    }

    //获取一段时间最后一天的日期
    public String getEndDay(String time) throws ParseException {
        if (time.length() == 4) {
            return time + "-12-31";
        }
        ////如果time长度为7表示，time输入的是年和月
        else if (time.length() == 7) {
            //判断月份是几月份
            DateFormat format = new SimpleDateFormat("yyyy-MM");
            Date timel = format.parse(time);
            SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
            SimpleDateFormat MM = new SimpleDateFormat("MM");
            String year = yyyy.format(timel);
            String month = MM.format(timel);
            int yearM = Integer.parseInt(year);
            int monthM = Integer.parseInt(month);
            if ((monthM % 2 != 0 && monthM < 8) || (monthM % 2 == 0 && monthM > 7)) {
                return time + "-31";
            } else if (monthM == 2) {
                if ((yearM % 400 == 0) || (yearM % 4 == 0) && (yearM % 100 != 0)) {
                    return time + "-29";
                } else {
                    return time + "-28";
                }
            } else {
                return time + "-30";
            }
        }
        ////如果time长度为7表示，time输入的是年月日
        return time;
    }

    /**
     * 时间戳转时间
     *
     * @param dateFormat
     * @param millSec
     * @return
     */
    public String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /*
      参数校验
     */
    public Boolean checkParameter(List<String> CheckNameList, PageData pd) {
        if (!ParameterUtil.getResult(CheckNameList)) {
            if (CheckNameList.size() > 0) {
                for (String CheckName : CheckNameList) {
                    if (ParameterUtil.getResult(pd.get(CheckName))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 将某个pd list 按其中某个键值key分类
     *
     * @param reList
     * @param key
     * @return
     */
    public Map<String, Object> classification(List<PageData> reList, String key) {
        Map<String, Object> reMap = new HashMap<>();
        for (PageData re : reList) {
            if (!ParameterUtil.getResult(re.get(key))) {
                String keyName = re.get(key).toString();
                if (ParameterUtil.getResult(reMap.get(keyName))) {
                    List<PageData> res = new ArrayList<>();
                    res.add(re);
                    reMap.put(keyName, res);
                } else {
                    List<PageData> res = JSON.parseArray(JSON.toJSONString(reMap.get(keyName)), PageData.class);
                    res.remove(re);
                    res.add(re);
                    reMap.put(keyName, res);
                }
            }
        }
        return reMap;
    }

    /**
     * @param data
     * @return
     * @throws Exception
     * @Method: encrypt
     * @Description: 加密数据
     * @date 2016年7月26日
     */
    public static String encrypt(String key, String data) {  //对string进行BASE64Encoder转换
        byte[] bt = new byte[0];
        try {
            bt = encryptByKey(data.getBytes("utf-8"), key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BASE64Encoder base64en = new BASE64Encoder();
        String strs = new String(base64en.encode(bt));
        return strs;
    }

    /**
     * @param data
     * @return
     * @throws Exception
     * @Method: encrypt
     * @Description: 解密数据
     * @date 2016年7月26日
     */
    public static String decryptor(String key, String data) throws Exception {  //对string进行BASE64Encoder转换
        sun.misc.BASE64Decoder base64en = new sun.misc.BASE64Decoder();
        byte[] base64enStr = base64en.decodeBuffer(data);
        byte[] bt = decrypt(base64enStr, key);
        String strs = new String(bt, "utf-8");
        return strs;
    }

    /**
     * 加密
     *
     * @param datasource byte[]
     * @param
     * @return byte[]
     */
    private static byte[] encryptByKey(byte[] datasource, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src byte[]
     * @param
     * @return byte[]
     * @throws Exception
     */
    private static byte[] decrypt(byte[] src, String key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    /**
     * 数据参数整理
     */
    public PageData encryptionParameter(String time, String appsecret, String schoolId, String appid, PageData parameter) {
        //1.将schoolId  和appid 加密
        PageData keyPd = new PageData();
        keyPd.put("schoolId", schoolId);
        keyPd.put("appid", appid);
        keyPd.put("appsecret", appsecret);
        keyPd.put("time", time);
        String encryptionString = encrypt(secret, JSON.toJSONString(keyPd));
        encryptionString = encryptionString.replaceAll("\n", "");
        encryptionString = encryptionString.replaceAll("\r", "");
        PageData param = new PageData();
        param.put("time", time);
        param.put("key", encryptionString);
        param.put("parameter", parameter);
        return param;
    }

    /**
     * 从对应平台获取数据get
     * 参数：URl,schoolId,appid,method,parameter
     */
    public String gainData(String url, String appsecret, String schoolId, String appid, PageData parameter) {
        PageData param = this.encryptionParameter(String.valueOf(WSPDate.getCurrentTimestemp()), appsecret, schoolId, appid, parameter);
        return com.rh.cloudcampus.edu.util.DoGet.doGet(url, JSON.parseObject(JSON.toJSONString(param.get("parameter")), PageData.class), param.get("key").toString(), param.get("time").toString());
    }

    /**
     * 向对应平台发送数据post
     */
    public String SendData(String url, String appsecret, String schoolId, String appid, PageData parameter) {
        PageData param = this.encryptionParameter(String.valueOf(WSPDate.getCurrentTimestemp()), appsecret, schoolId, appid, parameter);
        return DoGet.doPost(url, JSON.toJSONString(param.get("parameter")), param.get("key").toString(), param.get("time").toString());
    }

    /**
     * 接收请求
     */
    public Boolean ReceiveSend(String jsonString) {
        PageData param = JSON.parseObject(jsonString, PageData.class);
        if (!ParameterUtil.getResult(param.get("key"))) {
            //对key进行解密
            PageData keyPd = new PageData();
            try {
                keyPd = JSON.parseObject(decryptor(secret, param.get("key").toString()), PageData.class);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (!ParameterUtil.getResult(keyPd)) {
                if ((keyPd.get("time").toString()).equals(param.get("time").toString())) {
                    return true;
                }
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        byte[] b = "123".getBytes();;
//        for (int i = 0; i < b.length; i++) {
//            System.out.println( b[i]);
//
//        }
//    }


    public static byte[] classToC(PageData pd, int num) throws UnsupportedEncodingException {
        int le = 0;
        //计算有多少位
        List<Integer> numList = JSON.parseArray(pd.get("versionData").toString(), Integer.class);
        if (num == 0) {
            byte[] re = new byte[64];
            String version = pd.get("version").toString();
            byte[] vB = version.getBytes();
            int cursor = 0;
            for (int i = 0; i < vB.length; i++) {
                re[i] = vB[i];
            }
            cursor = 32;
            int checkNum = 0;
            for (int k = 0; k < numList.size(); k++) {
                checkNum = checkNum + numList.get(k);
            }
            re[cursor] = (byte) (checkNum & 0xFF);
            re[cursor + 1] = (byte) ((checkNum >> 8) & 0xFF);
            re[cursor + 2] = (byte) ((checkNum >> 16) & 0xFF);
            re[cursor + 3] = (byte) ((checkNum >> 24) & 0xFF);
            return re;
        } else {
            byte[] re = new byte[1024];
            if(numList.size()>(num-1)*1024){
                int i=0;
                for (int key = (num-1) * 1024;key < numList.size()&&key<num*1024; key++) {
                    re[i]=numList.get(key).byteValue();
                    i++;
                }
            }else {
                return re;
            }
            return re;
        }
    }

}


