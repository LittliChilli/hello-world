package com.shop.ops.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.shop.common.dateutil.DateUtil;
import com.shop.entity.basic.Admin;
import com.shop.entity.basic.UserReceiptAddress;
import com.shop.service.address.RecAddressService;
import com.shop.service.user.UserService;

/**
 * 
 * 用户相关操作Controller
 * 
 * @author lion
 * @date 2016-11-25
 * 
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	public UserService userService;

	@Autowired
	public RecAddressService recAddressService;

	/**
	 * 
	 * 跳转到用户列表页面
	 * 
	 * lion 2016年11月28日
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toUserList", method = RequestMethod.GET)
	public String toUserList(HttpServletRequest request,
			HttpServletResponse response) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		if (admin == null) {
			return "/back/user/userList";
		}
		return "/back/user/userList";
	}

	/**
	 * 
	 * 查询用户列表
	 * 
	 * @author lion
	 * @date 2016-11-25
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryUserListByPage", method = RequestMethod.GET)
	public Result queryUserListByPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String loginName = request.getParameter("loginName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String pageNum = request.getParameter("pageNum");
		if (StringUtils.isEmpty(pageNum)) {
			pageNum = "1";
		}
		startDate = DateUtil.getStartDate(startDate, 3);
		endDate = DateUtil.getEndDate(endDate);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("loginName", loginName);
		queryMap.put("startDate", startDate);
		queryMap.put("endDate", endDate);
		queryMap.put("pageNum", pageNum);
		Map<String, Object> dataMap = userService.queryUserListByPage(queryMap);
		model.addAttribute("pageInfo", dataMap.get("pageInfo"));
		model.addAttribute("userList", dataMap.get("userList"));
		result.setData(model);
		return result;
	}

	/**
	 * 
	 * 查询用户收货地址
	 * 
	 * @author lion
	 * @date 2016-11-25
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUserReceiptAddress", method = RequestMethod.GET)
	public Result findUserReceiptAddress(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String userId = request.getParameter("userId");
		if (StringUtils.isEmpty(userId)) {
			result.setResult("1");
			result.setMsg("查询失败，在试下吧");
			return result;
		}
		List<UserReceiptAddress> userReceiptAddressList = recAddressService
				.queryRecAddressByUserId(Long.parseLong(userId));
		model.addAttribute("userReceiptAddressList", userReceiptAddressList);
		result.setData(model);
		return result;
	}
}
