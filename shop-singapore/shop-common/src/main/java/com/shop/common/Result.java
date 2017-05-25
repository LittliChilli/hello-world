package com.shop.common;

import org.springframework.ui.Model;

/**
 * 消息返回对象
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public class Result {

	public static final String ERROR_CODE = "1";

	/**
	 * 结果编号
	 */
	public String result = "0";

	/**
	 * 返回消息
	 */
	public String msg;

	/**
	 * 返回数据
	 */
	public Model data;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Model getData() {
		return data;
	}

	public void setData(Model data) {
		this.data = data;
	}
}
