/** 
 * @fileName WeixinParamProperty.java
 * 
 * @version 
 * @author zousheng 
 * @date:2015年12月15日
 * Copyright ? 2015.美眉分期 All rights reserved.
 */
package com.shop.common.wechat;

import com.shop.common.readconfig.Property;

/**
 * 读取配置文件对应的微信参数
 * 
 * @version
 * @author zousheng
 * @date:2016年12月15日
 */
public interface WeixinParamProperty {

	/**
	 * 微信服务号AppId
	 */
	String SHOP_APPID = Property.getProperty("SHOP_APPID");

	/**
	 * 微信服务号应用密钥
	 */
	String SHOP_SECRET = Property.getProperty("SHOP_SECRET");
}
