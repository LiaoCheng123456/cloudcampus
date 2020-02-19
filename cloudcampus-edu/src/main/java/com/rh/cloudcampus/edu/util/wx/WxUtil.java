package com.rh.cloudcampus.edu.util.wx;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rh.cloudcampus.edu.common.BaseData;
import com.rh.cloudcampus.edu.model.PageData;
import com.rh.cloudcampus.edu.util.DoGet;
import com.rh.cloudcampus.edu.util.ParameterUtil;
import com.rh.cloudcampus.edu.util.redis.RedisUtil;
import com.rh.cloudcampus.edu.util.wx.HttpRequest;
import com.rh.cloudcampus.edu.util.wx.WxConsts;
import com.wsp.core.WSPCode;
import com.wsp.core.WSPResult;
import com.wsp.utils.WSPDate;
import com.wsp.utils.WSPString;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class WxUtil extends BaseData {
	protected static ParameterUtil isNull = new ParameterUtil();
	/**
     * 微信全局票据 ---->>>> access_token
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String getBaseAccessToken() {
        WSPResult wr = new WSPResult();
        try {
            String value = RedisUtil.getKey("WEIXIN_BASE_ACCESS_TOKEN")==null?"":RedisUtil.getKey("WEIXIN_BASE_ACCESS_TOKEN").toString();
            if (!WSPString.isEmpty(value)) {
                wr.setData(JSON.parseObject(value, PageData.class));
                return json.toJSONString(wr);
            }else{
                synchronized (this) {
                    //缓存中没有、或已经失效
                    String url = WxConsts.GET_CACCESS_TOKEN_URL+"&appid="+WxConsts.APPID+"&secret="+ WxConsts.SECRET;
                    String rs = HttpRequest.sendGet(url);
                    
                    JSONObject obj_content = JSONObject.parseObject(rs);
                    String accessToken = obj_content.getString("access_token");
                    Integer time = Integer.parseInt(obj_content.getString("expires_in").toString());
                    PageData pd =new PageData();
                    pd.put("accessToken", accessToken);
                    //写缓存
                    RedisUtil.setKeyBySecond("WEIXIN_BASE_ACCESS_TOKEN", json.toJSONString(pd), time - 100);
                    wr.setData(pd);
                    return json.toJSONString(wr);
                }
            }
        } catch (Exception e) {
        	wr.setCode(WSPCode.FAIL);
        	wr.setMsg("获取全局票据失败");
        	e.printStackTrace();
        	logger.error("获取微信全局票据出错."+e);
        }
        return null;
    }
    /**
     * 获取微信电子发票授权页链接
     * https://api.weixin.qq.com/card/invoice/getauthurl?access_token={access_token}
     * @param jsonString
     * @return
     */
    public static String getInvoiceAuthUrl(String jsonString) {
        WSPResult scrhResult = new WSPResult();
        PageData getUrlPd = JSON.parseObject(jsonString, PageData.class);
        if (isNull.getResult(getUrlPd.get("money"))  || isNull.getResult(getUrlPd.get("source")) 
         || isNull.getResult(getUrlPd.get("order_id")) || isNull.getResult(getUrlPd.get("type")) ) {
            scrhResult.setCode(WSPCode.SUCESS_OPERATE);
            scrhResult.setMsg(WSPCode.SUCESS_OPERATE_STR);
            return json.toJSONString(scrhResult);
        }
        try {
        	PageData accessToken_PageData = JSON.parseObject(JSON.parseObject(new WxUtil().getBaseAccessToken(), PageData.class).get("data").toString(),PageData.class);
        	String access_token = accessToken_PageData.get("accessToken").toString();
            
            String ticket = getTicket(access_token,"wx_card");
            if (!isNull.getResult(ticket)) {
            	getUrlPd.put("s_pappid", WxConsts.S_PAPPID);
            	getUrlPd.put("timestamp", WSPDate.getCurrentTimestemp());
            	getUrlPd.put("ticket", ticket);
            	scrhResult.setData(JSON.parseObject(DoGet.doPost(WxConsts.GET_AUTH_URL+access_token, json.toJSONString(getUrlPd)), PageData.class));
            }
        } catch (Exception e) {
        	scrhResult.setCode(WSPCode.FAIL);
        	scrhResult.setMsg(WSPCode.FAIL_STR);
            e.printStackTrace();
            logger.error("获取微信电子发票授权页链接报错."+e);
        }
        return json.toJSONString(scrhResult);
    }
    
	
    /***
     * 获取授权页ticket
     * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card
     */
    public static String getTicket(String access_token,String type) {
    	//ticket
        Map<String, String> param = new HashMap<>();
        param.put("access_token",access_token);
        param.put("type", type);
        PageData resultData = JSON.parseObject(DoGet.doGet(WxConsts.GET_TICKET_URL, param), PageData.class);
        if (resultData.get("ticket")!=null && !resultData.get("ticket").toString().equals("")) {
            return resultData.get("ticket").toString();
        }
        logger.error("获取授权页ticket失败.");
        return null;
    }
    /**
     * 设置商户联系方式，商户获取授权链接之前，需要先设置商户的联系方式
     * https://api.weixin.qq.com/card/invoice/setbizattr?action=set_contact&access_token={access_token}
     * @return
     */
    public static String setContact(String access_token,String param){
    	return DoGet.doPost(WxConsts.SET_CONTACT_URL+access_token, param);
    }
    public static String setContact(String param){
    	PageData accessToken_PageData = JSON.parseObject(JSON.parseObject(new WxUtil().getBaseAccessToken(), PageData.class).get("data").toString(),PageData.class);
    	String access_token = accessToken_PageData.get("accessToken").toString();
    	return setContact(access_token, param);
    }
    
    /**
     * 获取设置的商户联系方式
     * https://api.weixin.qq.com/card/invoice/setbizattr?action=get_contact&access_token={access_token}
     * @return
     */
    public static String getContact(){
    	PageData accessToken_PageData = JSON.parseObject(JSON.parseObject(new WxUtil().getBaseAccessToken(), PageData.class).get("data").toString(),PageData.class);
    	String access_token = accessToken_PageData.get("accessToken").toString();
    	return DoGet.doPost(WxConsts.GET_CONTACT_URL+access_token,null);
    }
    
    /**
     * 拒绝开票
     * https://api.weixin.qq.com/card/invoice/rejectinsert?access_token={access_token}
     * @return
     */
    public static String refuseInvoice(String param){
    	WSPResult scrhResult = new WSPResult();
    	PageData paramPd = JSON.parseObject(param,PageData.class);
    	try{
	    	if((paramPd.get("order_id")!=null && !paramPd.get("order_id").toString().equals(""))
	    	  ||(paramPd.get("reason")!=null && !paramPd.get("reason").toString().equals(""))){
	    		PageData accessToken_PageData = JSON.parseObject(JSON.parseObject(new WxUtil().getBaseAccessToken(), PageData.class).get("data").toString(),PageData.class);
	        	String access_token = accessToken_PageData.get("accessToken").toString();
	    		String resultStr = DoGet.doPost(WxConsts.REFUSE_INVOICE+access_token,param);
	    		scrhResult.setData(resultStr);
	    	}else{
	    		scrhResult.setCode(WSPCode.SUCESS_OPERATE);
	            scrhResult.setMsg(WSPCode.SUCESS_OPERATE_STR);
	    	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return json.toJSONString(scrhResult);
    	
    }
    
    
    public static void main(String[] args) {
//    	//获取微信发票授权页链接
//    	PageData pd = new PageData();
//    	pd.put("order_id", WSPGetID.getLongID());
//    	pd.put("money", 1);
//    	pd.put("source", "wxa");
//    	pd.put("redirect_url", "www.baidu,com");
//    	pd.put("type", 1);
//    	String returnStr =getInvoiceAuthUrl(json.toJSONString(pd));
//    	System.out.println("获取发票授权链接："+returnStr);
    	
//    	//获取设置的商户联系方式
//    	String contact=getContact();
//    	System.out.println("获取设置的商户联系方式："+contact);
    	
//    	//获取设置的商户联系方式
//    	String phone ="18702863610";
//    	int timeOut=10000;
//    	String param = "{\"contact\":{\"time_out\":"+timeOut+",\"phone\":\""+phone+"\"}}";
//    	String setResult = setContact(param);
//    	System.out.println("设置商户联系方式："+setResult+",参数："+param);
    	
    	
//    	//获取accessToken
//    	WxUtil wu  =new WxUtil();
//    	System.out.println("accessToken："+wu.getBaseAccessToken());
	}

}
