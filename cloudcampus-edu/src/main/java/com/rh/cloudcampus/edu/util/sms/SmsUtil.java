package com.rh.cloudcampus.edu.util.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rh.cloudcampus.edu.common.BaseData;
import com.rh.cloudcampus.edu.model.PageData;
import com.wsp.core.WSPCode;
import com.wsp.core.WSPResult;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component("smsUtil")
public class SmsUtil extends BaseData {

	public static String  uid = "104299"; //用户名
	public static String  pwd = "MmX6nn"; //密码
	public static String  srcphone = "106910134299"; //接入号
	public static String  httpUrl = "http://119.23.114.82:6666/cmppweb/sendsms";
	
	public static String  PR = "【众成仁华】"; //前缀
	public static String  SMSMSG = "您的验证码为#SMSCODE#，如非本人操作，请忽略本短信。"; //前缀
	
	public static JSONObject json = new JSONObject();

	/**
	 * 发送短信验证码
	 * @param paramStr 手机号、验证码
	 * @return
	 */
	public static String sendSmsCode(String paramStr) {
		String resultStr = "";
		PageData pd =JSON.parseObject(paramStr,PageData.class);
		if(pd.get("mobile")!=null && pd.get("code")!=null){
			resultStr =send(pd.get("mobile").toString(),SMSMSG.replace("#SMSCODE#", pd.get("code").toString()));
		}else{
			WSPResult result = new WSPResult();
			result.setCode(WSPCode.SUCESS_OPERATE);
			result.setMsg(WSPCode.SUCESS_OPERATE_STR);
			resultStr=json.toJSONString(result);
		}
		return resultStr;
	}
	/**
	 * 发送普通短信
	 * @param paramStr  手机号、内容
	 * @return
	 */
	public static String sendSms(String paramStr) {
		String resultStr = "";
		PageData pd =JSON.parseObject(paramStr,PageData.class);
		if(pd.get("mobile")!=null && pd.get("msg")!=null){
			resultStr =send(pd.get("mobile").toString(), pd.get("msg").toString());
		}else{
			WSPResult result = new WSPResult();
			result.setCode(WSPCode.SUCESS_OPERATE);
			result.setMsg(WSPCode.SUCESS_OPERATE_STR);
			resultStr=json.toJSONString(result);
		}
		return resultStr;
	}
	
	public static String send(String tele,String msg) {
		WSPResult result = new WSPResult();
		StringBuffer httpArg = new StringBuffer();
		httpArg.append("uid=").append(uid).append("&");
		httpArg.append("pwd=").append(md5(pwd)).append("&");
		httpArg.append("mobile=").append(tele).append("&");
		httpArg.append("srcphone=").append(srcphone).append("&");
		httpArg.append("msg=").append(encodeUrlString(PR+msg, "UTF-8"));
		
		String resultStr = request(httpUrl, httpArg.toString());
		if(!resultStr.substring(0, 1).equals("0")){
			result.setCode(WSPCode.FAIL);
			result.setMsg(WSPCode.FAIL_STR);
			logger.error("调用"+httpUrl+"发送短信失败，手机号："+tele+",内容:"+msg+",返回："+resultStr);
		}
		return JSON.toJSONString(result);
	}

	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = reader.readLine();
			if (strRead != null) {
				sbf.append(strRead);
				while ((strRead = reader.readLine()) != null) {
					sbf.append("\n");
					sbf.append(strRead);
				}
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String md5(String plainText) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
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

	public static String encodeUrlString(String str, String charset) {
		String strret = null;
		if (str == null)
			return str;
		try {
			strret = java.net.URLEncoder.encode(str, charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return strret;
	}
	
}
