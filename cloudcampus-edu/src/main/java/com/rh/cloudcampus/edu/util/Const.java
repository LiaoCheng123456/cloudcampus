package com.rh.cloudcampus.edu.util;

import com.rh.cloudcampus.edu.util.oss.AliyunOSSUtil;

import java.util.Properties;

/**
 * 项目常量
 * 
 * @author:rex
 * 
 */
public class Const {


	/**
	 * 操作失败
	 */
	public static final String FAIL_STR = "操作失败!";
	/**
	 * 参数缺失
	 */
	public static final String FAIL_PARAMETER = "参数缺失!";
	/**
	 * 登录超时
	 */
	public static final String FAIL_AUTH_STR = "登录超时！";
	private static Properties props = new Properties();
	/**
	 * 云存储的类型
	 */
	public static String OSS_ALIYUN="aliyun";
	public static String OSS_QINIU="qiniu";
	public static String OSS_TYY="tyy";
	public static String QINIU_URL="";
	static{
		
		try {
			props.load(AliyunOSSUtil.class.getClassLoader().getResourceAsStream("oss.properties"));
			if(props.getProperty("oss.type").equals(Const.OSS_ALIYUN)){
				QINIU_URL=props.getProperty("aliyun.image.url");
			}else if(props.getProperty("oss.type").equals(Const.OSS_TYY)){
				QINIU_URL=props.getProperty("tyy.image.url");
			}else if(props.getProperty("oss.type").equals(Const.OSS_QINIU)){
				QINIU_URL=props.getProperty("qiniu.image.url");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * redis拉取市局信息版本时间key值
	 */
	public static final String VERSION_TIME = "VERSION_TIME";

	public static final String sdm_A_HASH_SCHOOLDATA = "sdm_A_HASH_SCHOOLDATA"; //学校信息 redis
	public static final String REPORT_MANAGERCARD = "REPORT_MANAGERCARD"; //需要上报的管理卡 redis

	/**
	 * 云平台接口
	 */
	public static final String REPORT_AUTHCHANGE = "resident/addResident";  //识别信息变更上报云平台
	public static final String REPORT_ADD_LOCK = "school/reportSchool"; //新增门锁需向云平台上报schoolId绑定
	public static final String REPORT_PERSON_INFO = "resident/personData";  //上报住户信息
	public static final String REPORT_NBCARD = "nbCard/addNBCard";  //上报NB卡
	public static final String REPORT_STATUS = "device/uploadverison";  //上报WIFI锁实时状态

	public static final String PULL_REGIST_INFO = "school/checkIMEI";  //获取门锁注册信息
	public static final String PULL_OPERATE_LOG = "device/getOperatorLog";  //拉取操作日志
	public static final String PULL_UPDATED_VERSION = "device/checkIsSuccess"; //获取门锁识别信息下发状态
	public static final String PULL_DEVICE_STATUS = "device/getDeviceInfo"; //拉取设备状态
	public static final String PULL_DEVICE_LOG = "device/getDeviceLog"; //获取设备通讯日志

	/**
	 * 设备上报处理相关
	 */
	//wifi门锁socket 服务端端口
	public static final int LOCK_PORT = 8766;
	//Socket服务端解决TCP粘包拆包的分隔符
	public static final String SEPARATOR=";";
	//TCP传输的最长字节
	public static final int LOCK_TRANS_SIZE = 8192;

}
