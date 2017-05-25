/** 
 * @fileName RegisterUserController.java
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
import com.shop.common.exception.ServiceException;
import com.shop.common.token.AppTokenUtil;
import com.shop.common.valid.Validators;
import com.shop.entity.basic.User;
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
public class RegisterUserController extends UserController {

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
	@ResponseBody
	@RequestMapping(value = "register_user", method = RequestMethod.POST)
	public Result registerUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		// 登录名称(邮箱)
		String loginName = request.getParameter("user_name");

		if (StringUtils.isEmpty(loginName)) {
			throw new ServiceException(Result.ERROR_CODE, "登录邮箱不能为空");
		}
		
		// 邮箱校验
		if(!Validators.MatchMail(loginName)) {
			throw new ServiceException(Result.ERROR_CODE, "邮箱格式错误");
		}

		// 登录密码
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(password)) {
			throw new ServiceException(Result.ERROR_CODE, "密码不能为空");
		}
		
		// 密码格式校验
		if(!Validators.MatchPwd(password)){
			throw new ServiceException(Result.ERROR_CODE,"密码格式错误");
		}

		if (userService.checkUserIsExisted(loginName)) {
			throw new ServiceException(Result.ERROR_CODE, "登录邮箱已存在");
		}

		User user = userService.addUserByLoginName(loginName, password);

		String appToken = AppTokenUtil.createToken(user.getId().toString(), DateUtil.getNextDay(DateUtil.getNow(), 30));

		// 生成token信息,有效期延后30天
		model.addAttribute("APP_TOKEN", appToken);

		// 将token信息设置在session中
		request.getSession().setAttribute(Sessions.SESSION_USER_TOKEN.value(), appToken);

		return super.result(model);
	}

}
