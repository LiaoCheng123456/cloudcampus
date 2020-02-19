package com.rh.cloudcampus.edu.util.push;

import com.alibaba.fastjson.JSON;
import com.rh.cloudcampus.edu.common.BaseData;
import com.rh.cloudcampus.edu.model.MPushInparam;
import com.rh.cloudcampus.edu.util.push.umengPush.AppKey;
import com.rh.cloudcampus.edu.util.push.umengPush.FriendPush;
import com.wsp.core.WSPCode;
import com.wsp.core.WSPResult;
import org.springframework.stereotype.Component;

@Component("bPush")
public class BPush extends BaseData {
	
	/**
	 * WSP用户端 Android 推送对象
	 */
	private FriendPush WSP_android_user_FriendPush = new FriendPush(AppKey.WSP_Android_USER_AppKey, AppKey.WSP_Android_USER_Secret);
	/**
	 * WSP用户端 IOS 推送对象
	 */
	private FriendPush WSP_ios_user_FriendPush = new FriendPush(AppKey.WSP_IOS_USER_AppKey, AppKey.WSP_IOS_USER_Secret);
	
	
	/**
	 * WSP管理员 Android 推送对象
	 */
	private FriendPush WSP_android_gly_FriendPush = new FriendPush(AppKey.WSP_Android_GLY_AppKey, AppKey.WSP_Android_GLY_Secret);
	/**
	 * WSP巡查员 Android 推送对象
	 */
	private FriendPush WSP_android_xcy_FriendPush = new FriendPush(AppKey.WSP_Android_XCY_AppKey, AppKey.WSP_Android_XCY_Secret);

	
	
	
	
	/**
	 * 发送推送.
	 */
	public String sendPush(String inparamInparamJson) {
		WSPResult WSPResult = new WSPResult();
//		WSPResult.setCode(WSPCode.SUCESS_OPERATE);
//		logger.info("sendPush推送的数据参数为：" + inparamInparamJson);
		try {
			MPushInparam mPushInparam = JSON.parseObject(inparamInparamJson, MPushInparam.class);

			// 业务类型 1：WSP用户端
			if (mPushInparam.getBusinessTypeID() == 1) {
					// 0:单播，1：列播，2：广播，3：组播
					switch (mPushInparam.getCastType()) {
					case 0:
						// 0: android设备，
						if (mPushInparam.getDeviceType() == 0) {
							WSPResult = WSP_android_user_FriendPush.sendAndroidUnicast(mPushInparam);
						}
						// 1:ios设备
						if (mPushInparam.getDeviceType() == 1) {
							WSPResult = WSP_ios_user_FriendPush.sendIOSUnicast(mPushInparam);
						}
						break;
					case 1:
						// 0: android设备
						if (mPushInparam.getDeviceType() == 0) {
							WSPResult = WSP_android_user_FriendPush.sendAndroidFilecast(mPushInparam);
						}
						// 1:ios设备
						if (mPushInparam.getDeviceType() == 1) {
							WSPResult = WSP_ios_user_FriendPush.sendIOSFilecast(mPushInparam);
						}
						break;
					case 2:
						// 1:两个设备同时推送
						WSPResult = WSP_android_user_FriendPush.sendAndroidBroadcast(mPushInparam);
						WSPResult = WSP_ios_user_FriendPush.sendIOSBroadcast(mPushInparam);
						break;
					case 3:
						break;
					default:
						break;
					}
			} else if (mPushInparam.getBusinessTypeID() == 2) {// 业务类型 2：WSP管理员端
					// 0:单播，1：列播，2：广播，3：组播
					switch (mPushInparam.getCastType()) {
					case 0:
						// 0: android设备，
						if (mPushInparam.getDeviceType() == 0) {
							WSPResult = WSP_android_gly_FriendPush.sendAndroidUnicast(mPushInparam);
						}
						break;
					case 1:
						// 0: android设备
						if (mPushInparam.getDeviceType() == 0) {
							WSPResult = WSP_android_gly_FriendPush.sendAndroidFilecast(mPushInparam);
						}
						break;
					case 2:
						// 1:两个设备同时推送
						WSPResult = WSP_android_gly_FriendPush.sendAndroidBroadcast(mPushInparam);
						break;
					case 3:
						break;
					default:
						break;
					}
			}else if (mPushInparam.getBusinessTypeID() == 3) {// 业务类型 3：WSP巡查员端
				// 0:单播，1：列播，2：广播，3：组播
				switch (mPushInparam.getCastType()) {
				case 0:
					// 0: android设备，
					if (mPushInparam.getDeviceType() == 0) {
						WSPResult = WSP_android_xcy_FriendPush.sendAndroidUnicast(mPushInparam);
					}
					break;
				case 1:
					// 0: android设备
					if (mPushInparam.getDeviceType() == 0) {
						WSPResult = WSP_android_xcy_FriendPush.sendAndroidFilecast(mPushInparam);
					}
					break;
				case 2:
					// 1:两个设备同时推送
					WSPResult = WSP_android_xcy_FriendPush.sendAndroidBroadcast(mPushInparam);
					break;
				case 3:
					break;
				default:
					break;
				}
		}

		} catch (Exception e) {
			logger.error("sendPush请求参数为：" + inparamInparamJson + "umeng推送异常信息" + e.getMessage() + ", umeng推送自定义信息:"
					+ WSPResult.getMsg());
			WSPResult.setMsg("umeng推送异常信息" + e.getMessage() + ", umeng推送自定义信息:" + WSPResult.getMsg());
			WSPResult.setCode(WSPCode.FAIL);
//			WSPResult.setCode(WSPCode.SUCESS_OPERATE);
		}
		// 不为空，说明发送失败，记录一个异常日志
		if (WSPResult.getMsg()!=null) {
			logger.error("sendPush请求参数为：" + inparamInparamJson + "umeng推送自定义信息:" + WSPResult.getMsg());
			WSPResult.setMsg("umeng推送自定义信息:" + WSPResult.getMsg());
			WSPResult.setCode(WSPCode.FAIL);
//			WSPResult.setCode(WSPCode.SUCESS_OPERATE);
		}
		return json.toJSONString(WSPResult);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// // nzbdt android OK
		// String a =
		// "{\"deviceToken\":\"AmXcCfqHAqDwUhhPOZxsoZTGzh_oFrnqgb8rXz_UYUpH\",\"target\":\"{\\\"type\\\":\\\"TYPE_TAKE_OUT\\\",\\\"id\\\":\\\"BA606EA8-3DD9-4BA2-AF61-72EA94B72765\\\"}\",\"type\":\"TYPE_ORDER\",\"title\":\"待配送，Ugo-er未接单\",\"content\":\"配送员接单超时，订单自动取消\",\"description\":\"\",\"subType\":\"\",\"notifyType\":0,\"deviceType\":0,\"castType\":0,\"businessTypeID\":0}";
		// BPush bPush = new BPush();
		// String result = bPush.sendPush(a);
		// System.out.println(result);

		// ios OK
		// String ios =
		// "{\"deviceToken\":\"cca0f326ad39616c35c01af65f32e08a81ad7ae4d5420ece9aee0ee2c9eb90e3\",\"target\":\"{\\\"type\\\":\\\"TYPE_TAKE_OUT\\\",\\\"id\\\":\\\"BA606EA8-3DD9-4BA2-AF61-72EA94B72765\\\"}\",\"type\":\"TYPE_ORDER\",\"title\":\"待配送，Ugo-er未接单\",\"content\":\"配送员接单超时，订单自动取消\",\"description\":\"\",\"subType\":\"\",\"notifyType\":0,\"deviceType\":1,\"castType\":0,\"businessTypeID\":0}";
		// BPush bPush = new BPush();
		// String result = bPush.sendPush(ios);
		// System.out.println(result);

		// Seller android OK
		// String a =
		// "{\"deviceToken\":\"Akdtfimi_-WUAu3E-R20X5MU9K6YCUYVJxkBnmR8G2Ia\",\"target\":\"{\\\"id\\\":\\\"30309905\\\",\\\"peopleNum\\\":4,\\\"isRoom\\\":true,\\\"bookingName\\\":\\\"rex\\\",\\\"mobile\\\":\\\"18702873671\\\",\\\"sex\\\":\\\"S_MALE\\\",\\\"dueCharge\\\":182,\\\"mealAt\\\":1234233884,\\\"addAt\\\":1732323889,\\\"type\\\":\\\"T_BOOKING\\\"}\",\"type\":\"T_ORDER\",\"title\":\"待配送，Ugo-er未接单\",\"content\":\"配送员接单超时，订单自动取消\",\"description\":\"\",\"subType\":\"\",\"notifyType\":0,\"deviceType\":0,\"castType\":0,\"businessTypeID\":2}";
		// String a =
		// "{\"businessTypeID\":\"2\",\"castType\":0,\"content\":\"您有新的订座订单啦！\",\"description\":\"\",\"deviceToken\":\"Akdtfimi_-WUAu3E-R20X5MU9K6YCUYVJxkBnmR8G2Ia\",\"deviceType\":0,\"notifyType\":0,\"sound\":\"notification_voice.caf\",\"subType\":\"\",\"target\":\"{\\\"addAt\\\":1504084206,\\\"bookingName\\\":\\\"蒲鹏\\\",\\\"dueCharge\\\":29,\\\"id\\\":30817119,\\\"isRoom\\\":1,\\\"mealAt\\\":\\\"1410715640579\\\",\\\"mobile\\\":\\\"+8618782962282\\\",\\\"peopleNum\\\":\\\"29\\\",\\\"sex\\\":\\\"S_FEMALE\\\",\\\"type\\\":\\\"T_DISH\\\"}\",\"title\":\"订单\",\"type\":\"T_ORDER\",\"url\":\"\"}";
		// BPush bPush = new BPush();
		// String result = bPush.sendPush(a);
		// System.out.println(result);

		// Seller IOS OK
		// String a =
		// "{\"deviceToken\":\"e51efbee427561dbd315fbb021247dde31a132084b2b151144e734962192d04d\",\"target\":\"{\\\"id\\\":\\\"30309905\\\",\\\"peopleNum\\\":4,\\\"isRoom\\\":true,\\\"bookingName\\\":\\\"rex\\\",\\\"mobile\\\":\\\"18702873671\\\",\\\"sex\\\":\\\"S_MALE\\\",\\\"dueCharge\\\":182,\\\"mealAt\\\":1234233884,\\\"addAt\\\":1732323889,\\\"type\\\":\\\"T_BOOKING\\\"}\",\"type\":\"T_ORDER\",\"sound\":\"notification_voice.caf\",\"title\":\"待配送，Ugo-er未接单\",\"content\":\"配送员接单超时，订单自动取消\",\"description\":\"\",\"subType\":\"\",\"notifyType\":0,\"deviceType\":1,\"castType\":0,\"businessTypeID\":2}";
		// String a =
		// "{\"deviceToken\":\"85930a41b16ef81af135948045cb17e6cf9a744365dcebbd8aedcb73765450f6\",\"target\":\"{\\\"addAt\\\":1504084206,\\\"bookingName\\\":\\\"蒲鹏\\\",\\\"dueCharge\\\":29,\\\"id\\\":30817119,\\\"isRoom\\\":1,\\\"mealAt\\\":\\\"1410715640579\\\",\\\"mobile\\\":\\\"+8618782962282\\\",\\\"peopleNum\\\":\\\"29\\\",\\\"sex\\\":\\\"S_FEMALE\\\",\\\"type\\\":\\\"T_DISH\\\"}\",\"type\":\"T_ORDER\",\"sound\":\"notification_voice.caf\",\"title\":\"订座\",\"content\":\"您有新的订座订单啦！\",\"description\":\"\",\"subType\":\"\",\"notifyType\":0,\"deviceType\":1,\"castType\":0,\"businessTypeID\":2}";
		// String a =
		// "{\"businessTypeID\":\"2\",\"castType\":0,\"content\":\"您有新的点餐订单啦！\",\"description\":\"\",\"deviceToken\":\"85930a41b16ef81af135948045cb17e6cf9a744365dcebbd8aedcb73765450f6\",\"deviceType\":1,\"notifyType\":0,\"sound\":\"notification_voice.caf\",\"subType\":\"\",\"target\":\"{\\\"addAt\\\":1504159736,\\\"bookingName\\\":\\\"测试12345\\\",\\\"dueCharge\\\":19,\\\"id\\\":30893976,\\\"mealAt\\\":\\\"\\\",\\\"mobile\\\":\\\"18328412017\\\",\\\"peopleNum\\\":\\\"\\\",\\\"type\\\":\\\"T_DISH\\\"}\",\"title\":\"点餐\",\"type\":\"T_ORDER\",\"url\":\"\"}";
		String a = "{\"businessTypeID\":\"0\",\"castType\":0,\"content\":\"WSP测试推送\",\"description\":\"\",\"deviceToken\":\"ApZJ4Y1yzPj7ij3o6texRsPITroHoM8BG7vFebMLySFl\",\"deviceType\":0,\"notifyType\":0,\"sound\":\"takeout_notification_voice.caf\",\"subType\":\"\",\"target\":\"{\\\"order\\\":{\\\"addAt\\\":1508245060,\\\"addBy\\\":33366056,\\\"addIP\\\":\\\"220.166.230.160\\\",\\\"addressId\\\":32772968,\\\"addressVer\\\":1,\\\"appointmentAt\\\":\\\"10:30-11:30\\\",\\\"appointmentStamp\\\":13242343535435,\\\"commentStatus\\\":\\\"CS_NOT\\\",\\\"count\\\":1,\\\"dueCharge\\\":15.000,\\\"dueDistributionCharge\\\":0.000,\\\"id\\\":30349667998364,\\\"isFirstOrder\\\":0,\\\"isRefund\\\":0,\\\"mealBoxCharge\\\":0.000,\\\"number\\\":654,\\\"platfrom\\\":\\\"P_IOS\\\",\\\"rate\\\":4.960000,\\\"realCharge\\\":15.000,\\\"realDistributionCharge\\\":0.000,\\\"remarks\\\":\\\"张静测试\\\",\\\"shopId\\\":30150029,\\\"status\\\":\\\"S_WAITRECEIVE\\\",\\\"ugoToShopAt\\\":1508246860,\\\"ugoToShopAtStr\\\":\\\"10:00~10:30\\\"},\\\"type\\\":\\\"T_TAKEOUT\\\"}\",\"title\":\"外卖\",\"type\":\"T_ORDER\",\"url\":\"\"}";
		BPush bPush = new BPush();
		String result = bPush.sendPush(a);
		System.out.println(result);
	}

}
