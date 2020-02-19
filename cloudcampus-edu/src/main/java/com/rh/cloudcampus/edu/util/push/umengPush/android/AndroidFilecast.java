package com.rh.cloudcampus.edu.util.push.umengPush.android;

import com.rh.cloudcampus.edu.util.push.umengPush.AndroidNotification;

public class AndroidFilecast extends AndroidNotification {
	public AndroidFilecast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "filecast");
	}

	public void setFileId(String fileId) throws Exception {
		setPredefinedKeyValue("file_id", fileId);
	}
}