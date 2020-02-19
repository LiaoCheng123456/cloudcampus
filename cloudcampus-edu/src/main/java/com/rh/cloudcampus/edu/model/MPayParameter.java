package com.rh.cloudcampus.edu.model;

public class MPayParameter {

	/**
	 * @支付方式 P_ALIPAY／P_WECHAT／P_DPS／P_COD
	 */
	private String type;

	/**
	 * @商户订单号,又名out_trade_no
	 */
	private String orderId;

	/**
	 * @用户端发起支付时的IP地址
	 */
	private String ip;

	/**
	 * @附加信息，此信息会在回调时一摸一样返回
	 */
	private String attach;

	/**
	 * @汇率
	 */
	private float rate;

	/**
	 * @订单总金额，注意：只能保留两位小数
	 */
	private float totalAmount;

	/**
	 * @回调地址，支付宝，微信，dps都必须有
	 */
	private String notifyUrlSuccess;

	/**
	 * @下单平台，如果为微信支付，此字段不能为空，示例H5，ANDROID，IOS
	 */
	private String platform;

	/**
	 * @微信的openId，如果为微信支付，此字段不能为空
	 */
	private String openId;

	/**
	 * @当为dps支付时，此字段不能为空，表示意思为支付失败时dps的回调地址
	 */
	private String notifyUrlFail;

	/**
	 * @当为dps支付时，此字段为选填
	 */
	private String dpsBillingId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNotifyUrlSuccess() {
		return notifyUrlSuccess;
	}

	public void setNotifyUrlSuccess(String notifyUrlSuccess) {
		this.notifyUrlSuccess = notifyUrlSuccess;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNotifyUrlFail() {
		return notifyUrlFail;
	}

	public void setNotifyUrlFail(String notifyUrlFail) {
		this.notifyUrlFail = notifyUrlFail;
	}

	public String getDpsBillingId() {
		return dpsBillingId;
	}

	public void setDpsBillingId(String dpsBillingId) {
		this.dpsBillingId = dpsBillingId;
	}

}
