package com.shop.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义日志输出
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public class LoggerAdapter {

	private Logger logger;

	@SuppressWarnings("rawtypes")
	public LoggerAdapter(Class clazz) {
		logger = LoggerFactory.getLogger(clazz != null ? clazz.getName() : " ");
	}

	public void debug(String msg, Throwable ex) {
		logger.debug(msg, ex);
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(Throwable ex) {
		if (ex == null) {
			logger.debug(null);
		} else {
			logger.debug(ex.getMessage(), ex);
		}
	}

	public void debug(String msg, Object... params) {
		logger.debug(msg, params);
	}

	public void error(String msg, Throwable ex) {
		logger.error(msg, ex);
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(Throwable ex) {
		if (ex == null) {
			logger.error(null);
		} else {
			logger.error(ex.getMessage(), ex);
		}
	}

	public void error(String msg, Object... params) {
		logger.error(msg, params);
	}

	public void info(String msg, Throwable ex) {
		logger.info(msg, ex);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(Throwable ex) {
		if (ex == null) {
			logger.info(null);
		} else {
			logger.info(ex.getMessage(), ex);
		}
	}

	public void info(String msg, Object... params) {
		logger.info(msg, params);
	}

	public void warn(String msg, Throwable ex) {
		logger.warn(msg, ex);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(Throwable ex) {
		if (ex == null) {
			logger.warn(null);
		} else {
			logger.warn(ex.getMessage(), ex);
		}
	}

	public void warn(String msg, Object... params) {
		logger.warn(msg, params);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
}
