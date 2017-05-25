/** 
 * @fileName JsSDKConfig.java
 * 
 * @version 
 * @author zousheng 
 * @date:2015年11月19日
 * Copyright © 2015.美眉分期 All rights reserved.
 */
package com.shop.common.wechat.vo;

/**
 * 微信授权接口参数对象
 * 
 * @version
 * @author zousheng
 * @date:2016年12月15日
 */
public class JsSDKConfig {

	public String appId;
	public String timestamp;
	public String nonceStr;
	public String signature;
	public static final String JS_API = "onMenuShareTimeline";

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String[] getJsApiList() {
		return JS_API.split(",");
	}

	@Override
	public String toString() {
		return "appId：" + this.appId + "\n" + "timestamp：" + this.timestamp + "\n" + "nonceStr：" + this.nonceStr + "\n"
				+ "signature：" + this.signature + "\n" + "JS_API：" + JS_API;
	}
}
