package com.shop.teamgroup.common;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.shop.common.dateutil.DateUtil;
import com.shop.common.exception.Message;
import com.shop.common.exception.ServiceException;
import com.shop.common.token.AppTokenUtil;
import com.shop.entity.basic.User;
import com.shop.service.common.CookieHandlingService;
import com.shop.service.user.UserService;

/**
 * 判断用户登录切面处理
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public abstract class UserController extends BasicController {

	@Autowired
	protected UserService userService;

	@Autowired
	protected CookieHandlingService cookieHandlingService;

	/**
	 * 获取用户信息, 不报错
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年5月26日
	 *
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public User getUserFromSession(HttpServletRequest request) {

		return getUserFromToken(request);
	}

	/**
	 * 获取用户信息,抛出异常错误码:1013
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月23日
	 *
	 * @param request
	 * @return
	 */
	public User getUserFromSession2Exception(HttpServletRequest request) {

		User user = getUserFromToken(request);

		if (null == user) {

			throw new ServiceException(Message.user_loginname_not_exist);
		}

		return user;
	}

	private User getUserFromToken(HttpServletRequest request) {

		String appToken = request.getParameter("appToken");

		// 从外部获取token
		if (StringUtils.isEmpty(appToken)) {
			appToken = super.getUserTokenFromSession(request);
		}

		// 从cook中获取token
		if (StringUtils.isEmpty(appToken)) {
			appToken = cookieHandlingService.getCookie("appToken", request);
		}

		User user = null;

		// token登录
		HashMap<String, String> tokenMap = AppTokenUtil.analysisToken(appToken);
		if (tokenMap != null && !tokenMap.isEmpty()) {
			user = login(request, tokenMap);
		}

		return user;
	}

	/**
	 * 通过appToken 读取用户信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月23日
	 *
	 * @param request
	 * @param appToken
	 * @return
	 */
	public User getUserFromSession(HttpServletRequest request, String appToken) {

		User user = null;

		// token登录
		HashMap<String, String> tokenMap = AppTokenUtil.analysisToken(appToken);
		if (tokenMap != null && !tokenMap.isEmpty()) {
			user = login(request, tokenMap);
		}

		if (null == user) {
			throw new ServiceException(Message.user_loginname_not_exist);
		}

		return user;
	}

	/**
	 * token直接登录
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年1月23日
	 *
	 * @param request
	 * @param phone
	 * @param pwd
	 * @throws ServiceException
	 */
	private User login(HttpServletRequest request, HashMap<String, String> tokenMap) {

		// 当前时间超过失效时间,则验证token失效
		if (tokenMap.get("EXPTIME") == null || DateUtil.getNow().getTime() > Long.valueOf(tokenMap.get("EXPTIME"))) {
			return null;
		}

		return userService.queryUserById(Long.valueOf(tokenMap.get("UserId")));
	}
}
