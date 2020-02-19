package com.rh.cloudcampus.edu.util.push;


import com.rh.cloudcampus.edu.util.push.BPush;

public class PushService{

	/**
	 * 发送推送
	 * 
	 * @param inparamInparamJson
	 * @return
	 */
	public String sendPush(String inparamInparamJson) {
		return new BPush().sendPush(inparamInparamJson);
	}
}
