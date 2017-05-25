package com.shop.common.logger;

/**
 * 自定义日志打印
 * @version 
 * @author zousheng 
 * @date:2016年11月23日
 */
public class LoggerAdapterFacory {

	@SuppressWarnings("rawtypes")
	public static LoggerAdapter getLoggerAdapter(Class clazz) {

		return new LoggerAdapter(clazz);
	}
}
