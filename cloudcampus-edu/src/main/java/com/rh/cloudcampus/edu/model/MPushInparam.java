/*
 * 文件名：MPushInparam.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MPushInparam.java
 * 修改人：Administrator
 * 修改时间：2017年4月28日
 * 修改内容：新增
 */
package com.rh.cloudcampus.edu.model;

/**
 * TODO 推送入参.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author Administrator
 */
public class MPushInparam {

	/**
	 * 手机相关的，每个手机都是一定的
	 */
	private String deviceToken = "";

	/**
	 * Json串（附加业务参数）
	 */
	private String target = "";

	/**
	 * 类型 （附加业务参数）
	 */
	private String type = "";

	/**
	 * 声音 （附加业务参数，ios）
	 */
	private String sound = "";

	/**
	 * 标题
	 */
	private String title = "";

	/**
	 * 内容
	 */
	private String content = "";

	/**
	 * 描述
	 */
	private String description = "";

	/**
	 * 摘要类型（附加业务参数）
	 */
	private String subType = "";

	/**
	 * 0:仅通知（打开应用），1：跳转链接，2：系统消息（打开应用） ,3:自定义消息
	 */
	private int notifyType = 0;

	/**
	 * 0: android设备，1:ios设备
	 */
	private int deviceType = 0;

	/**
	 * 0:单播，1：列播，2：广播，3：组播
	 */
	private int castType = 0;

	/**
	 * 跳转链接URL
	 */
	private String url;

	/**
	 * 业务类型 0：本地通，1：Ugo，2：Seller
	 */
	private int businessTypeID = 0;

	/**
	 * 设置多少小时过期
	 */
	private Integer expireHour;

	/**
	 * 设置url.
	 * 
	 * @return 返回url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 获取url.
	 * 
	 * @param url
	 *            要设置的url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 设置notifyType.
	 * 
	 * @return 返回notifyType
	 */
	public int getNotifyType() {
		return notifyType;
	}

	/**
	 * 获取notifyType.
	 * 
	 * @param notifyType
	 *            要设置的notifyType
	 */
	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	/**
	 * 设置castType.
	 * 
	 * @return 返回castType
	 */
	public int getCastType() {
		return castType;
	}

	/**
	 * 获取castType.
	 * 
	 * @param castType
	 *            要设置的castType
	 */
	public void setCastType(int castType) {
		this.castType = castType;
	}

	/**
	 * 设置deviceToken.
	 * 
	 * @return 返回deviceToken
	 */
	public String getDeviceToken() {
		return deviceToken;
	}

	/**
	 * 获取deviceToken.
	 * 
	 * @param deviceToken
	 *            要设置的deviceToken
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	/**
	 * 设置target.
	 * 
	 * @return 返回target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 获取target.
	 * 
	 * @param target
	 *            要设置的target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 设置type.
	 * 
	 * @return 返回type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获取type.
	 * 
	 * @param type
	 *            要设置的type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 设置title.
	 * 
	 * @return 返回title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 获取title.
	 * 
	 * @param title
	 *            要设置的title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设置content.
	 * 
	 * @return 返回content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 获取content.
	 * 
	 * @param content
	 *            要设置的content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 设置description.
	 * 
	 * @return 返回description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 获取description.
	 * 
	 * @param description
	 *            要设置的description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 设置subType.
	 * 
	 * @return 返回subType
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * 获取subType.
	 * 
	 * @param subType
	 *            要设置的subType
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * 设置deviceType.
	 * 
	 * @return 返回deviceType
	 */
	public int getDeviceType() {
		return deviceType;
	}

	/**
	 * 获取deviceType.
	 * 
	 * @param deviceType
	 *            要设置的deviceType
	 */
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * 获取 businessTypeID 0：本地通，1：Ugo
	 * 
	 * @return
	 */
	public int getBusinessTypeID() {
		return businessTypeID;
	}

	/**
	 * 设置businessTypeID 0：本地通，1：Ugo
	 * 
	 * @param businessTypeID
	 */
	public void setBusinessTypeID(int businessTypeID) {
		this.businessTypeID = businessTypeID;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public Integer getExpireHour() {
		return expireHour;
	}

	public void setExpireHour(Integer expireHour) {
		this.expireHour = expireHour;
	}

}
