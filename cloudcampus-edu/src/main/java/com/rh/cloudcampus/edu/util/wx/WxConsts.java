package com.rh.cloudcampus.edu.util.wx;
/**
 * 微信相关常量
 * @author Rex
 */
public class WxConsts {
	/**
	 * 电子发票相关
	 */
	//设置商户联系方式url，获取电子发票授权页链接前调通
	public static String SET_CONTACT_URL="https://api.weixin.qq.com/card/invoice/setbizattr?action=set_contact&access_token=";
	//获取设置商户联系方式url
	public static String GET_CONTACT_URL="https://api.weixin.qq.com/card/invoice/setbizattr?action=get_contact&access_token=";
	//获取授权页ticket url
	public static String GET_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	//获取授权链接 url
	public static String GET_AUTH_URL="https://api.weixin.qq.com/card/invoice/getauthurl?access_token=";
	//电子发票开票平台提供的
	public static String S_PAPPID="d3hmZTI2ZTAxZjY1ZmE3MjcxX7KRybhUvfXOKVTxVnmpx5KEkN78T7QzqjsTfgZGdnSl";
	//拒绝开票url
	public static String REFUSE_INVOICE="https://api.weixin.qq.com/card/invoice/rejectinsert?access_token=";
	//微信添加图片消息的api
	public static String ADD_IMAGE="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=";
	
	/**
	 * access_token相关
	 */
	//设置商户联系方式，获取电子发票授权页链接前调通
	public static String GET_CACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	//公众号
	public static String APPID="wx991a55d98e4683f4";
	public static String SECRET="d7c3919d75db556754259178cbfb9807";

//	public static String APPID="wx0dd4cd0a145e1229";
//	public static String SECRET="a75fdbdd02306eabb6cc4afadf4d34ff";
	
}
