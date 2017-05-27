package com.shop.common.exception;

import com.shop.common.logger.LoggerAdapter;
import com.shop.common.logger.LoggerAdapterFacory;

/**
 * 自定义异常抛出exception
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5586130347453716681L;

	private static final LoggerAdapter logger = LoggerAdapterFacory.getLoggerAdapter(ServiceException.class);

	/**
	 * 异常消息
	 */
	private String msg;

	/**
	 * 消息代码
	 */
	private String code;

	/**
	 * 强制调用带有消息的异常
	 */
	@SuppressWarnings("unused")
	private ServiceException() {
	}

	public ServiceException(Message message) {
		super(message.getMsg());
		this.msg = message.getMsg();
		this.code = message.getCode();
		logger.info("服务调用异常,异常代码{},异常内容:{}", message.getCode(), message.getMsg());
	}

	public ServiceException(String code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}
}
