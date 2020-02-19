package com.rh.cloudcampus.edu.util.push.umengPush.android;

import com.rh.cloudcampus.edu.util.push.umengPush.AndroidNotification;
import org.json.JSONObject;

public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "groupcast");
	}

	public void setFilter(JSONObject filter) throws Exception {
		setPredefinedKeyValue("filter", filter);
	}
}
