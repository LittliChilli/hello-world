package com.shop.ops.index;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value="/")
	public void index(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			response.sendRedirect("/order/toOrderlist");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
