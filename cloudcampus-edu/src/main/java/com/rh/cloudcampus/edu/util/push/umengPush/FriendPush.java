package com.rh.cloudcampus.edu.util.push.umengPush;

import com.rh.cloudcampus.edu.model.MPushInparam;
import com.rh.cloudcampus.edu.util.push.umengPush.android.*;
import com.rh.cloudcampus.edu.util.push.umengPush.ios.*;
import com.rh.cloudcampus.edu.util.push.umengPush.AndroidNotification;
import com.rh.cloudcampus.edu.util.push.umengPush.PushClient;
import com.rh.cloudcampus.edu.util.push.umengPush.android.*;
import com.rh.cloudcampus.edu.util.push.umengPush.ios.*;
import com.wsp.core.WSPResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FriendPush {
	private String appkey = null;

	private String appMasterSecret = null;

	private String timestamp = null;

	private PushClient client = new PushClient();

	public FriendPush(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 
	 * TODO 广播.
	 * 
	 * @param target
	 * @param type
	 * @param name
	 * @param content
	 * @throws Exception
	 */
	public WSPResult sendAndroidBroadcast(MPushInparam mPushInparam) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
		broadcast.setTicker(mPushInparam.getTitle());
		broadcast.setTitle(mPushInparam.getTitle());
		broadcast.setText(mPushInparam.getContent());
		// broadcast.goAppAfterOpen();
		// if (notifyType == 0 || notifyType == 2) {
		// broadcast.goAppAfterOpen();
		// } else if (notifyType == 1) {
		// broadcast.goUrlAfterOpen(url);
		// } else {
		// broadcast.goCustomAfterOpen("test");
		// }
		broadcast.goCustomAfterOpen("test");
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		//broadcast.setTestMode();
		 broadcast.setProductionMode();
		// Set customized fields
		if (mPushInparam.getType()!=null) {
			broadcast.setExtraField("type", mPushInparam.getType());
		}
		if (mPushInparam.getTarget()!=null) {
			broadcast.setExtraField("target", mPushInparam.getTarget());
		}
		if (mPushInparam.getSubType()!=null) {
			broadcast.setExtraField("subType", mPushInparam.getSubType());
		}
		if (mPushInparam.getExpireHour() != null && mPushInparam.getExpireHour() > 0) {
			// 设置超时时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			// 几个小时超时
			calendar.add(Calendar.MINUTE, mPushInparam.getExpireHour() * 60);
			Date date = new Date(calendar.getTimeInMillis());
			String expireTime = sdf.format(date);
			broadcast.setExpireTime(expireTime);
		}
		return client.sendNew(broadcast);
	}

	/**
	 * 
	 * TODO Android单播.
	 * 
	 * @param deviceToken
	 * @param target
	 * @param type
	 * @param name
	 * @param content
	 * @throws Exception
	 */
	public WSPResult sendAndroidUnicast(MPushInparam mPushInparam) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(mPushInparam.getDeviceToken());
		unicast.setTicker(mPushInparam.getTitle()); // 通知栏提示信息
		unicast.setTitle(mPushInparam.getTitle());
		unicast.setText(mPushInparam.getContent());
		unicast.goCustomAfterOpen("test");
		unicast.setDescription(mPushInparam.getDescription());
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);

		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		// unicast.setProductionMode();
		// 测试模式
		//unicast.setTestMode();
		 unicast.setProductionMode();
		// Set customized fields
		if (mPushInparam.getType()!=null) {
			unicast.setExtraField("type", mPushInparam.getType());
		}
		if (mPushInparam.getTarget()!=null) {
			unicast.setExtraField("target", mPushInparam.getTarget());
		}
		if (mPushInparam.getSubType()!=null) {
			unicast.setExtraField("subType", mPushInparam.getSubType());
		}

		if (mPushInparam.getExpireHour() != null && mPushInparam.getExpireHour() > 0) {
			// 设置超时时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			// 几个小时超时
			calendar.add(Calendar.MINUTE, mPushInparam.getExpireHour() * 60);
			Date date = new Date(calendar.getTimeInMillis());
			String expireTime = sdf.format(date);
			unicast.setExpireTime(expireTime);
		}
		return client.sendNew(unicast);
	}

	/**
	 * 
	 * TODO 组波.
	 * 
	 * @throws Exception
	 */
	public void sendAndroidGroupcast() throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"test"}, {"tag":"Test"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "TagTest");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		groupcast.setFilter(filterJson);
		groupcast.setTicker("Android groupcast ticker");
		groupcast.setTitle("中文的title");
		groupcast.setText("Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		// groupcast.setTestMode();
		groupcast.setProductionMode();
		client.send(groupcast);
	}

	public void sendAndroidCustomizedcast() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias("Test", "SINA_WEIBO");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidCustomizedcastFile() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	/**
	 *
	 * TODO Android列播.
	 *
	 * @param deviceToken
	 * @param target
	 * @param type
	 * @param name
	 * @param content
	 * @throws Exception
	 */
	public WSPResult sendAndroidFilecast(MPushInparam mPushInparam) throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
		String[] deviceTokensList = mPushInparam.getDeviceToken().split(",");

		StringBuffer contentBuffer = new StringBuffer();
		for (String deviceToken : deviceTokensList) {
			contentBuffer.append(deviceToken);
			contentBuffer.append("\n"); // don't forget the return character
		}
		// String fileId = client.uploadContents(appkey, appMasterSecret, "aa" +
		// "\n" + "bb");
		String contents = contentBuffer.substring(0, contentBuffer.length() - 1).toString();
		String fileId = client.uploadContents(appkey, appMasterSecret, contents);
		filecast.setFileId(fileId);
		filecast.setTicker(mPushInparam.getTitle());
		filecast.setTitle(mPushInparam.getTitle());
		filecast.setText(mPushInparam.getContent());
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		filecast.setDescription(mPushInparam.getDescription());
		// 测试模式
		//filecast.setTestMode();
		 filecast.setProductionMode();
		// Set customized fields
		if (mPushInparam.getType()!=null) {
			filecast.setExtraField("type", mPushInparam.getType());
		}
		if (mPushInparam.getTarget()!=null) {
			filecast.setExtraField("target", mPushInparam.getTarget());
		}
		if (mPushInparam.getSubType()!=null) {
			filecast.setExtraField("subType", mPushInparam.getSubType());
		}

		if (mPushInparam.getExpireHour() != null && mPushInparam.getExpireHour() > 0) {
			// 设置超时时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			// 几个小时超时
			calendar.add(Calendar.MINUTE, mPushInparam.getExpireHour() * 60);
			Date date = new Date(calendar.getTimeInMillis());
			String expireTime = sdf.format(date);
			filecast.setExpireTime(expireTime);
		}
		return client.sendNew(filecast);
	}

	/**
	 * 
	 * TODO IOS单播.
	 * 
	 * @param deviceToken
	 * @param target
	 * @param type
	 * @param name
	 * @param content
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public WSPResult sendIOSUnicast(MPushInparam mPushInparam) throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(mPushInparam.getDeviceToken());
		HashMap map = new HashMap();
		map.put("body", mPushInparam.getContent());
		map.put("title", mPushInparam.getTitle());
		unicast.setAlert(map);
		// unicast.setAlert(name);
		unicast.setBadge(0);
		if (mPushInparam.getSound() != null && mPushInparam.getSound() != "") {
			unicast.setSound(mPushInparam.getSound());
		} else {
			unicast.setSound("default");
		}
		unicast.setDescription(mPushInparam.getDescription());
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		//unicast.setTestMode();	//测试模式
		unicast.setProductionMode();	//生成模式
		if (mPushInparam.getTarget()!=null) {
			unicast.setCustomizedField("target", mPushInparam.getTarget());
		}
		if (mPushInparam.getType()!=null) {
			unicast.setCustomizedField("type", mPushInparam.getType());
		}
		if (mPushInparam.getSubType()!=null) {
			unicast.setCustomizedField("subType", mPushInparam.getSubType());
		}

		if (mPushInparam.getExpireHour() != null && mPushInparam.getExpireHour() > 0) {
			// 设置超时时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			// 几个小时超时
			calendar.add(Calendar.MINUTE, mPushInparam.getExpireHour() * 60);
			Date date = new Date(calendar.getTimeInMillis());
			String expireTime = sdf.format(date);
			unicast.setExpireTime(expireTime);
		}
		return client.sendNew(unicast);
	}

	/**
	 * 
	 * TODO IOS广播.
	 * 
	 * @param target
	 * @param type
	 * @param name
	 * @param content
	 * @throws Exception
	 */
	// mPushInparam.getTarget(), mPushInparam.getType(),
	// mPushInparam.getContent(), mPushInparam.getDescription(),
	// mPushInparam.getSubType()
	// String target, String type, String content, String description, String
	// subType
	public WSPResult sendIOSBroadcast(MPushInparam mPushInparam) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey, appMasterSecret);
		broadcast.setAlert(mPushInparam.getContent());
		broadcast.setBadge(0);
		if (mPushInparam.getSound() != null && mPushInparam.getSound() != "") {
			broadcast.setSound(mPushInparam.getSound());
		} else {
			broadcast.setSound("default");
		}
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		//broadcast.setTestMode();
		 broadcast.setProductionMode();
		// Set customized fields
		// 测试模式
		broadcast.setDescription(mPushInparam.getDescription());
		// Set customized fields
		if (mPushInparam.getTarget()!=null) {
			broadcast.setCustomizedField("target", mPushInparam.getTarget());
		}
		if (mPushInparam.getType()!=null) {
			broadcast.setCustomizedField("type", mPushInparam.getType());
		}
		if (mPushInparam.getSubType()!=null) {
			broadcast.setCustomizedField("subType", mPushInparam.getSubType());
		}
		if (mPushInparam.getExpireHour() != null && mPushInparam.getExpireHour() > 0) {
			// 设置超时时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			// 几个小时超时
			calendar.add(Calendar.MINUTE, mPushInparam.getExpireHour() * 60);
			Date date = new Date(calendar.getTimeInMillis());
			String expireTime = sdf.format(date);
			broadcast.setExpireTime(expireTime);
		}
		return client.sendNew(broadcast);
	}

	public void sendIOSGroupcast() throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"iostest"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "测试");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge(0);
		groupcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		// groupcast.setTestMode();
		groupcast.setProductionMode();
		client.send(groupcast);
	}

	public void sendIOSCustomizedcast() throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them
		// if there are multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias("Test", "SINA_WEIBO");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge(0);
		customizedcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		// customizedcast.setTestMode();
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	/**
	 *
	 * TODO IOS 列播.
	 *
	 * @param deviceToken
	 * @param target
	 * @param type
	 * @param name
	 * @param content
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WSPResult sendIOSFilecast(MPushInparam mPushInparam) throws Exception {
		IOSFilecast filecast = new IOSFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		HashMap map = new HashMap();
		map.put("body", mPushInparam.getContent());
		map.put("title", mPushInparam.getTitle());
		String[] deviceTokensList = mPushInparam.getDeviceToken().split(",");
		StringBuffer contentBuffer = new StringBuffer();
		for (String deviceToken : deviceTokensList) {
			contentBuffer.append(deviceToken);
			contentBuffer.append("\n"); // don't forget the return character
		}
		// String fileId = client.uploadContents(appkey, appMasterSecret, "aa" +
		// "\n" + "bb");
		String contents = contentBuffer.substring(0, contentBuffer.length() - 1).toString();
		// 调用文件上传，将token上传，返回一个文件id
		String fileId = client.uploadContents(appkey, appMasterSecret, contents);
		filecast.setFileId(fileId);
		filecast.setAlert(map);
		filecast.setBadge(0);
		if (mPushInparam.getSound() != null && mPushInparam.getSound() != "") {
			filecast.setSound(mPushInparam.getSound());
		} else {
			filecast.setSound("default");
		}
		filecast.setDescription(mPushInparam.getDescription());
		// 测试模式
		//filecast.setTestMode();
		 filecast.setProductionMode();
		// Set customized fields
		 if (mPushInparam.getTarget()!=null) {
			filecast.setCustomizedField("target", mPushInparam.getTarget());
		}
		 if (mPushInparam.getType()!=null) {
			filecast.setCustomizedField("type", mPushInparam.getType());
		}
		 if (mPushInparam.getSubType()!=null) {
			filecast.setCustomizedField("subType", mPushInparam.getSubType());
		}

		if (mPushInparam.getExpireHour() != null && mPushInparam.getExpireHour() > 0) {
			// 设置超时时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			// 几个小时超时
			calendar.add(Calendar.MINUTE, mPushInparam.getExpireHour() * 60);
			Date date = new Date(calendar.getTimeInMillis());
			String expireTime = sdf.format(date);
			filecast.setExpireTime(expireTime);
		}
		return client.sendNew(filecast);
	}

}
