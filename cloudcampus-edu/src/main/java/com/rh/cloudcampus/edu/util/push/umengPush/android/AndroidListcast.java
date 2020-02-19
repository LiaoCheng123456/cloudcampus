package com.rh.cloudcampus.edu.util.push.umengPush.android;

import com.rh.cloudcampus.edu.util.push.umengPush.AndroidNotification;

public class AndroidListcast extends AndroidNotification {
	public AndroidListcast(String appkey, String appMasterSecret, String device_tokens) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");
		this.setPredefinedKeyValue("device_tokens", device_tokens);

	}
}
