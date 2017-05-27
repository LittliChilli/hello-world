package com.shop.common.exception;

/**
 * 提示信息message
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public enum Message {

	user_loginname_error("1001", "登录邮箱错误"),
	user_pwd_error("1002", "密码错误"), 
	user_loginname_not_exist("1013","未登陆或登录已失效,请重新登录"),
	user_address_not_exist("1014","请先添加默认收货地址"),
	sys_error("1","系统忙碌中,请稍后重试");

	private String code;
	private String msg;
	private boolean loged;

	private Message(String code, String msg) {
		this.code = code;
		this.msg = msg;
		loged = true;
	}

	private Message(String code, String msg, boolean loged) {
		this.code = code;
		this.msg = msg;
		this.loged = loged;
	}

	public Message setMessage(String code, String msg) {
		this.code = code;
		this.msg = msg;
		loged = true;

		return this;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isLoged() {
		return loged;
	}

}
