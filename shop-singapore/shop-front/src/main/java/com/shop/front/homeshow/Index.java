package com.shop.front.homeshow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.front.common.UserController;

/**
 * 首页地址转换
 * 
 * @version
 * @author zousheng
 * @date:2016年12月5日
 */
@Controller
public class Index extends UserController {

	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "redirect:/static/singapore_telphone_web/market/index.html";
	}
}
