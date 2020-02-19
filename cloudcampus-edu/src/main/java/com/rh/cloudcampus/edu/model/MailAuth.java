package com.rh.cloudcampus.edu.model;

import javax.mail.*;

/**   
 *  @Description: 发送邮件账户密码验证
 *  @author rex
 *  @version 1.0
 */ 
public class MailAuth extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MailAuth(){   
    }   
    public MailAuth(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}   
