/** 
 * @fileName
 * 
 * @version 
 * @author chenhui 
 * @date:2015年12月11日
 * Copyright © 2016.美眉分期 All rights reserved.
 */
package com.shop.common.readconfig;

import java.io.IOException;
import java.util.Properties;

/**
 * @version
 * @author chenhui
 * @date:2015年12月11日
 */
public class PropertyPlaceholderConfigurer extends
		org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	private static Properties props;

	public Properties mergeProperties() throws IOException {
		props = super.mergeProperties();
		Property.init(props);
		return props;
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);

	}
}
