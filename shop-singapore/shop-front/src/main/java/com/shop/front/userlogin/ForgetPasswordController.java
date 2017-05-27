/** 
 * @fileName ForgetPasswordController.java
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.common.exception.ServiceException;
import com.shop.front.common.UserController;

/**
 * 用户注册模块控制
 * 
 * @version
 * @author zousheng
 * @date:2016年11月24日
 */
@Controller
@RequestMapping(value = "/singapore/userlogin")
public class ForgetPasswordController extends UserController {

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
	@RequestMapping(value = "forget_password")
	@ResponseBody
	public Result registerUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		// 登录名称
		String loginName = request.getParameter("login_name");

		if (StringUtils.isEmpty(loginName)) {
			throw new ServiceException(Result.ERROR_CODE, "登录名不能为空");
		}

		// 登录密码
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(password)) {
			throw new ServiceException(Result.ERROR_CODE, "密码不能为空");
		}

		if (!userService.checkUserIsExisted(loginName)) {
			throw new ServiceException(Result.ERROR_CODE, "登录名不存在");
		}

		// 用户重置密码
		userService.updateUserPassword(loginName, password);

		return super.result(model);
	}

}
