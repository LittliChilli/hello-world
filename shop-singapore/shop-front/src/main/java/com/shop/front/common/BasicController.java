package com.shop.front.common;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.shop.common.Result;
import com.shop.common.enums.Sessions;
import com.shop.common.logger.LoggerAdapter;
import com.shop.common.logger.LoggerAdapterFacory;

/**
 * 基础Action
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public class BasicController {

	private LoggerAdapter logger = LoggerAdapterFacory.getLoggerAdapter(BasicController.class);

	public String getUserTokenFromSession(HttpServletRequest request) {

		logger.debug("获取用户token信息" + Sessions.SESSION_USER_TOKEN.value());
		return (String) request.getSession().getAttribute(Sessions.SESSION_USER_TOKEN.value());
	}

	public boolean isRobot(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isNotBlank(userAgent)) {
			if (userAgent.toLowerCase().contains("spider") || userAgent.toLowerCase().contains("bot")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	protected void send301(HttpServletResponse response, String redirectUrl) {
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", redirectUrl);
		response.setHeader("Connection", "close");
	}

	protected String getReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	@InitBinder
	// 必须有一个参数WebDataBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));

		binder.registerCustomEditor(int.class, new PropertyEditorSupport() {

			@Override
			public String getAsText() {
				return getValue().toString();
			}

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				int value = 0;
				if (org.apache.commons.lang3.StringUtils.isNotBlank(text)) {
					try {
						value = Integer.parseInt(text.replaceAll("[^\\d]", ""));
					} catch (Exception e) {

					}
				}
				setValue(value);
			}
		});
	}

	/**
	 * result返回处理
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param model
	 * @return
	 */
	public Result result(Model model) {

		Result result = new Result();
		result.setData(model);

		return result;
	}

	/**
	 * result返回处理
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param code
	 * @param msg
	 * @return
	 */
	public Result result(String code, String msg) {

		Result result = new Result();
		result.setResult(code);
		result.setMsg(msg);

		logger.debug("错误信息提示:" + msg);
		return result;
	}
}
