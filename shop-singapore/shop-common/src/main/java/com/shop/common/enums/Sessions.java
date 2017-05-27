package com.shop.common.enums;

/**
 * 登陆session枚举
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public enum Sessions {

	/**
	 * 登录用户token
	 */
	SESSION_USER_TOKEN("loginUserToken"),

	/**
	 * 登录管理员token
	 */
	SESSION_Admin_TOKEN("loginAdminToken");

	private String value;

	Sessions(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}
