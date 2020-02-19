package com.rh.cloudcampus.edu.util.mail;

import com.rh.cloudcampus.edu.common.BaseData;
import com.rh.cloudcampus.edu.model.MailAuth;
import com.rh.cloudcampus.edu.model.MailInfo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;


/**   
 * @Description: 简单邮件（不带附件的邮件）发送器  
 * @author rex 
 * @version 1.0
 */     
public class MailUtil extends BaseData {
	
	public static String HOST="smtp.exmail.qq.com";
	public static String PORT="465";
	public static String USERNAME="zhangj@scrhtec.com";
	public static String PASSWORD="kSEdnGGtCJRz5xNS";
	public static String FROM="zhangj@scrhtec.com";
	
	
	/**   
	  * 以文本格式发送邮件   
	  * @param mailInfo 待发送的邮件的信息   
	  * @return boolean
	  */    
    public boolean sendTextMail(MailInfo mailInfo) {
    	try{
		      // 判断是否需要身份认证    
		      MailAuth authenticator = null;
		      Properties pro = mailInfo.getProperties();   
		      if (mailInfo.isValidate()) {    
		      // 如果需要身份认证，则创建一个密码验证器    
		        authenticator = new MailAuth(mailInfo.getUserName(), mailInfo.getPassword());    
		      }   
		      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
		      Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
		      
		      // 根据session创建一个邮件消息    
		      Message mailMessage = new MimeMessage(sendMailSession);    
		      // 创建邮件发送者地址    
		      Address from = new InternetAddress(mailInfo.getFromAddress());    
		      // 设置邮件消息的发送者    
		      mailMessage.setFrom(from);    
		      // 创建邮件的接收者地址，并设置到邮件消息中    
		      Address to = new InternetAddress(mailInfo.getToAddress());    
		      mailMessage.setRecipient(Message.RecipientType.TO,to);    
		      // 设置邮件消息的主题    
		      mailMessage.setSubject(mailInfo.getSubject());    
		      // 设置邮件消息发送的时间    
		      mailMessage.setSentDate(new Date());    
		      // 设置邮件消息的主要内容    
		      String mailContent = mailInfo.getContent();    
		      mailMessage.setText(mailContent);    
		      // 发送邮件    
		      Transport.send(mailMessage); 
    	}catch (Exception e) {
    		e.printStackTrace();
		}
      return true;    
    }    
       
    /**   
      * 以HTML格式发送邮件   
      * @param mailInfo 待发送的邮件信息   
      * @return boolean
      */    
    public  boolean sendHtmlMail(MailInfo mailInfo) {
    	try{
		      // 判断是否需要身份认证    
		      MailAuth authenticator = null;   
		      Properties pro = mailInfo.getProperties();   
		      //如果需要身份认证，则创建一个密码验证器     
		      if (mailInfo.isValidate()) {    
		        authenticator = new MailAuth(mailInfo.getUserName(), mailInfo.getPassword());   
		      }    
		      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
		      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
		        
		      // 根据session创建一个邮件消息    
		      Message mailMessage = new MimeMessage(sendMailSession);    
		      // 创建邮件发送者地址    
		      Address from = new InternetAddress(mailInfo.getFromAddress());    
		      // 设置邮件消息的发送者    
		      mailMessage.setFrom(from);    
		      // 创建邮件的接收者地址，并设置到邮件消息中    
		      Address to = new InternetAddress(mailInfo.getToAddress());    
		      // Message.RecipientType.TO属性表示接收者的类型为TO    
		      mailMessage.setRecipient(Message.RecipientType.TO,to);    
		      // 设置邮件消息的主题    
		      mailMessage.setSubject(mailInfo.getSubject());    
		      // 设置邮件消息发送的时间    
		      mailMessage.setSentDate(new Date());    
		      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
		      Multipart mainPart = new MimeMultipart();    
		      // 创建一个包含HTML内容的MimeBodyPart    
		      BodyPart html = new MimeBodyPart();    
		      // 设置HTML内容    
		      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
		      mainPart.addBodyPart(html);    
		      // 将MiniMultipart对象设置为邮件内容    
		      mailMessage.setContent(mainPart);    
		      // 发送邮件    
		      Transport.send(mailMessage);    
    	}catch (Exception e) {
    		e.printStackTrace();
		}
      return true;    
    }    
 
   
    /**
     * @param SMTP  	邮件服务器
     * @param PORT		端口
     * @param EMAIL		本邮箱账号
     * @param PAW		本邮箱密码
     * @param toEMAIL	对方箱账号
     * @param TITLE		标题
     * @param CONTENT	内容
     * @param TYPE		1：文本格式;2：HTML格式
     */
    public  void sendEmail(String SMTP, String PORT, String EMAIL, String PAW, String toEMAIL, String TITLE, String CONTENT, String TYPE) { 
    	try{
	        //这个类主要是设置邮件   
		     MailInfo mailInfo = new MailInfo();
		     
		     mailInfo.setMailServerHost(SMTP);    
		     mailInfo.setMailServerPort(PORT);    
		     mailInfo.setValidate(true);    
		     mailInfo.setUserName(EMAIL);    
		     mailInfo.setPassword(PAW);   
		     mailInfo.setFromAddress(EMAIL);    
		     mailInfo.setToAddress(toEMAIL);    
		     mailInfo.setSubject(TITLE);    
		     mailInfo.setContent(CONTENT);    
		     //这个类主要来发送邮件   
		  
		     MailUtil sms = new MailUtil();
		     
		    if("1".equals(TYPE)){
		    	sms.sendTextMail(mailInfo);
		    }else{
		    	sms.sendHtmlMail(mailInfo);
		    }
    	}catch (Exception e) {
    		e.printStackTrace();
		}
	   }
    
    
    public static void main(String[] args){   
        //这个类主要是设置邮件   
	     MailInfo mailInfo = new MailInfo();    
	     mailInfo.setMailServerHost(HOST);    
	     mailInfo.setMailServerPort(PORT);    
	     mailInfo.setValidate(true);    
	     mailInfo.setUserName(USERNAME);    
	     mailInfo.setPassword(PASSWORD);//您的邮箱密码    
	     mailInfo.setFromAddress(FROM);    
	     mailInfo.setToAddress("zhangj@scrhtec.com");    
	     mailInfo.setSubject("设置邮箱标题");    
	     mailInfo.setContent("设置邮箱内容");    
	     //这个类主要来发送邮件   
	  
	     MailUtil sms = new MailUtil();   
	     try {
			sms.sendTextMail(mailInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//发送文体格式    
	     //sms.sendHtmlMail(mai	lInfo);//发送html格式  
    	
    	//new MailUtil().sendAlarmMail("注意，服务异常","邮件测试","短信测试","MQ_DeviceReportService_xfDeviceReport");
	 }
    
    	
}   
