/** 
 * @fileName
 * 
 * @version 
 * @author chenhui 
 * @date:2015年12月11日
 * Copyright © 2016.美眉分期 All rights reserved.
 */
package com.shop.common.readconfig;

/**
 * @version
 * @author chenhui
 * @date:2015年12月11日
 */
public class Property {

	private static java.util.Properties property;

	private Property() {
	}

	static void init(java.util.Properties props) {
		property = props;
	}

	public static String getProperty(String key) {
		return property.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return property.getProperty(key, defaultValue);

	}
}
