package com.shop.front.center;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.entity.basic.User;
import com.shop.front.common.UserController;
import com.shop.service.order.OrderService;

/**
 * 用户中心页控制
 * 
 * @version
 * @author zousheng
 * @date:2016年11月24日
 */
@Controller
@RequestMapping(value = "/singapore/center")
public class UserCenterController extends UserController {
	
	@Autowired
	private OrderService orderService;

	/**
	 * 查询用户信息
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
	@RequestMapping(value = "query_user_info", method = RequestMethod.GET)
	@ResponseBody
	public Result queryUserInfo(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession(request);
		model.addAttribute("user", user);

		return super.result(model);
	}

	/**
	 * 根基订单状态查询用户订单数量
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
	@RequestMapping(value = "query_user_order_number")
	@ResponseBody
	public Result queryUserOrderNumber(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession(request);

		String orderStatus = request.getParameter("order_status");

		// 默认没有订单
		Integer orderNum = 0;

		if (user != null && StringUtils.isNotEmpty(orderStatus)) {
			orderNum = orderService.queryOrdeNumByOrderStatus(user.getId(), orderStatus);
		}

		model.addAttribute("orderNum", orderNum);

		return super.result(model);
	}

	/**
	 * 检测用户是否已登录
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("check_isLogin")
	@ResponseBody
	public Result checkIsLogin(Model model, HttpServletRequest request) {

		User user = super.getUserFromSession(request);

		if (user == null) {
			// false代表未登录
			model.addAttribute("isLogin", false);
		} else {
			// true代表已登录
			model.addAttribute("isLogin", true);
		}

		return super.result(model);
	}
}
