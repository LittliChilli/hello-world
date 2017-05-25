/** 
 * @fileName LoginUserController.java
 * 
 * @version 
 * @author zousheng 
 * @date:2016年11月24日
 * Copyright © 2016.美眉分期 All rights reserved.
 */
package com.shop.front.userlogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.common.dateutil.DateUtil;
import com.shop.common.enums.Sessions;
import com.shop.common.exception.Message;
import com.shop.common.exception.ServiceException;
import com.shop.common.token.AppTokenUtil;
import com.shop.entity.basic.User;
import com.shop.front.common.UserController;

/**
 * 用户登录模块控制
 * 
 * @version
 * @author zousheng
 * @date:2016年11月24日
 */
@Controller
@RequestMapping(value = "/singapore/userlogin")
public class LoginUserController extends UserController {

	/**
	 * 用户登录
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login_user", method = RequestMethod.POST)
	@ResponseBody
	public Result loginUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		// 登录名称
		String loginName = request.getParameter("login_name");

		// 登录密码
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(loginName) || !userService.checkUserIsExisted(loginName)) {
			throw new ServiceException(Message.user_loginname_error);
		}

		User user = userService.queryUserByLoginName(loginName, password);

		String appToken = AppTokenUtil.createToken(user.getId().toString(), DateUtil.getNextDay(DateUtil.getNow(), 30));

		// 生成token信息,有效期延后30天
		model.addAttribute("APP_TOKEN", appToken);

		// 将token信息设置在session中
		request.getSession().setAttribute(Sessions.SESSION_USER_TOKEN.value(), appToken);

		return super.result(model);
	}

	/**
	 * 用户登出
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "loginOut_user", method = RequestMethod.POST)
	@ResponseBody
	public Result loginOutUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		super.getUserFromSession2Exception(request);

		request.getSession().invalidate();

		return super.result(model);
	}
}
