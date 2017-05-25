package com.shop.ops.admin;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.shop.entity.basic.Admin;
import com.shop.service.admin.AdminService;

/**
 * 
 * 管理员controller
 * 
 * @author lion
 * @date 2016-11-25
 * 
 */
@Controller
@RequestMapping(value = "admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 
	 * 跳转到登录页面
	 * 
	 * lion 2016年11月29日
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "tologin", method = RequestMethod.GET)
	public String toLogin(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/admin/login";
	}

	/**
	 * 
	 * 管理员登录
	 * 
	 * lion 2016年11月28日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String loginName = request.getParameter("loginName");
		String loginPassward = request.getParameter("loginPassward");
		Admin admin = adminService.findAdminByLoginNameAndPassward(loginName,
				loginPassward);
		request.getSession().setAttribute("admin", admin);
		return result;
	}

	/**
	 * 
	 * 管理员退出系统
	 * 
	 * lion 2016年11月28日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "loginout", method = RequestMethod.GET)
	public String loginOut(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		request.getSession().removeAttribute("admin");
		return "/back/admin/login";
	}

	/**
	 * 
	 * 跳转到管理员列表
	 * 
	 * lion 2016年11月28日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAdminList", method = RequestMethod.GET)
	public String toAdminList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "/back/admin/adminList";
	}

	/**
	 * 
	 * 分页查询管理员列表
	 * 
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "queryAdminListByPage", method = RequestMethod.GET)
	@ResponseBody
	public Result queryAdminListByPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String loginName = request.getParameter("loginName");
		String pageNum = request.getParameter("pageNum");
		Map<String, Object> dataMap = adminService.queryAdminListByPage(
				loginName, pageNum);
		model.addAttribute("pageInfo", dataMap.get("pageInfo"));
		model.addAttribute("adminList", dataMap.get("adminList"));
		result.setData(model);
		return result;
	}

	/**
	 * 
	 * 添加管理员
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addAdminInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result addAdminInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String loginName = request.getParameter("addLoginName");
		String loginPassward = request.getParameter("addLoginPassword");
		String nickName = request.getParameter("addNickName");
		Pattern pattern = Pattern.compile("^[A-Za-z0-9\\-]+$");
		if (StringUtils.isEmpty(loginName)) {
			result.setResult("2");
			result.setMsg("帐号不能为空");
			return result;
		} else {
			if (loginName.length() < 6) {
				result.setResult("2");
				result.setMsg("帐号只能是6位以上的英文和数字,最多12位");
				return result;
			} else if (loginName.length() > 12) {
				result.setResult("2");
				result.setMsg("帐号最多12位英文和数字");
				return result;
			}
			Matcher matcherName = pattern.matcher(loginName);
			boolean bolName = matcherName.matches();
			if (!bolName) {
				result.setResult("2");
				result.setMsg("帐号只能是英文和数字");
				return result;
			}
		}
		if (StringUtils.isEmpty(loginPassward)) {
			result.setResult("3");
			result.setMsg("帐号或密码不能为空");
			return result;
		} else if (loginPassward.length() < 6) {
			result.setResult("3");
			result.setMsg("密码太简单了，至少6位以上，最多12位");
			return result;
		} else if (loginPassward.length() > 12) {
			result.setResult("3");
			result.setMsg("密码最多12位");
			return result;
		} else {
			Matcher matcherPassWord = pattern.matcher(loginName);
			boolean bolPassWord = matcherPassWord.matches();
			if (!bolPassWord) {
				result.setResult("3");
				result.setMsg("密码只能是英文和数字");
				return result;
			}
		}
		adminService.addAdminInfo(loginName, loginPassward, nickName);
		return result;
	}

	/**
	 * 
	 * 删除管理员
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteAdminInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteAdminInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String adminId = request.getParameter("adminId");
		if (StringUtils.isEmpty(adminId)) {
			result.setResult("1");
			result.setMsg("操作失败，再试下吧");
			return result;
		}
		adminService.deleteAdminInfo(Long.parseLong(adminId));
		return result;
	}

	/**
	 * 
	 * 重置管理员密码
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "resetAdminPassward", method = RequestMethod.GET)
	@ResponseBody
	public Result resetAdminPassward(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String adminId = request.getParameter("adminId");
		if (StringUtils.isEmpty(adminId)) {
			result.setResult("1");
			result.setMsg("操作失败，再试下吧");
			return result;
		}
		adminService.resetAdminPassward(Long.parseLong(adminId));
		return result;
	}
}
