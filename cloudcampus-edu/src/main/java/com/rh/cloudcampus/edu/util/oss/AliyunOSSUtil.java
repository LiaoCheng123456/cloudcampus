package com.rh.cloudcampus.edu.util.oss;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
//import com.qiniu.common.QiniuException;
import com.rh.cloudcampus.edu.common.BaseData;
import com.rh.cloudcampus.edu.model.PageData;
import com.wsp.core.WSPResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 阿里云存储
 * @author Rex
 */
public class AliyunOSSUtil extends BaseData {
	//解析上传成功的结果
    private JSONObject json = new JSONObject();
	private static Properties props = new Properties();
	private static String bucketName= "";

	static{
		
		try {
			props.load(AliyunOSSUtil.class.getClassLoader().getResourceAsStream("oss.properties"));
			bucketName=props.getProperty("aliyun.bucket");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static String ak =props.getProperty("aliyun.access.key");
    private static String sk =props.getProperty("aliyun.secret.key");
    private static String endPoint =props.getProperty("aliyun.endpoint");
    private static String stsEndPoint =props.getProperty("aliyun.sts.endpoint");
    private static String stsRoleArn =props.getProperty("aliyun.sts.rolearn");
    private static String stsRoleSessionName =props.getProperty("aliyun.sts.rolesessionname");
    private static String imageUrl =props.getProperty("aliyun.image.url");
    private static String region =props.getProperty("aliyun.region");
    
    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public OSSClient getOSSClient(){
        return new OSSClient(endPoint, ak, sk);
    }
    /**
     * 获取上传参数
     * @return 上传 Token
     */
    public String getUploadToken(){
        WSPResult wsp= new WSPResult();
        PageData pd= new PageData();
        try {
        	ProtocolType protocolType = ProtocolType.HTTPS;
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "", "Sts", stsEndPoint);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", ak, sk);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(stsRoleArn);
            request.setRoleSessionName(stsRoleSessionName);
            request.setProtocol(protocolType);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            pd.put("Expiration", response.getCredentials().getExpiration());
            pd.put("AccessKeyId", response.getCredentials().getAccessKeyId());
            pd.put("AccessKeySecret", response.getCredentials().getAccessKeySecret());
            pd.put("SecurityToken", response.getCredentials().getSecurityToken());
            pd.put("Bucket", bucketName);
            pd.put("EndPoint", endPoint);
            pd.put("Region", region);
            pd.put("ImgUrl", imageUrl);
            pd.put("type", "aliyun");
            wsp.setData(pd);
        } catch (ClientException e) {
        	e.printStackTrace();
    		logger.error("OSSOSS获取上传签名异常（getUploadToken）报错，原因："+e);
        }
    	return json.toJSONString(wsp);
    }
    /**
     * 文件上传
     * 字节数组（常用）
     * @param uploadBytes 图片二进制数组
     * @param key	自定义的文件名，默认传null
     * @param Persistent  该字段废弃
     * @return 返回空串则表示上传失败，上传成功则返回图片名
     */
    public String uploadImage(byte[] uploadBytes,String key){
    	try {

    		// 创建OSSClient实例。
    		OSSClient ossClient = getOSSClient();
    		ObjectMetadata meta = new ObjectMetadata();
    		// 设置MD5校验。
    		meta.setContentMD5(BinaryUtil.toBase64String(BinaryUtil.calculateMd5(uploadBytes)));
    		meta.setContentType("image/jpeg");
    		ossClient.putObject(bucketName, key, new ByteArrayInputStream(uploadBytes), meta);
    		// 关闭OSSClient。
    		ossClient.shutdown();
    	    return imageUrl+key;
    	} catch (Exception ex) {
    		logger.error("OSS文件上传（uploadImage）报错，原因："+ex);
    		
    	}
		return "";
    }
   
    /**
     * OSS删除图片
     */
    public String deleteImage(String key){
    	try {
    		// 创建OSSClient实例。
    		OSSClient ossClient = getOSSClient();
    		ossClient.deleteObject(bucketName, key);
    		// 关闭OSSClient。
    		ossClient.shutdown();
    	} catch (Exception ex) {
    		logger.error("OSS删除图片异常（deleteImage）报错，原因："+ex);
    	}
		return "";
    }
//    /**
//     * 获取图片请求的域名
//     */
//    public String getImageUrl(){
//		return imageUrl;
//    }
//    public static void main(String[] args) throws QiniuException {
//    	AliyunOSSUtil oss = new AliyunOSSUtil();
////    	System.out.println(oss.getUploadToken());
//    	System.out.println(oss.uploadImage(ImageUtil.image2byte("/Users/zhangjing/Desktop/熊猫停车/车牌图片示例.jpeg"),"CP_WEFHU4895GNIG9451"));
////    	oss.deleteImage("车牌图片示例.jpeg");
//    }


}
