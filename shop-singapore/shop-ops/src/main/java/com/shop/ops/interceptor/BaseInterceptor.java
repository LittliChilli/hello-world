package com.shop.ops.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BaseInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (request.getRequestURI().equals("/admin/tologin")
				|| request.getRequestURI().equals("/admin/loginout")
				|| request.getRequestURI().equals("/admin/login")) {
			return true;
		} else {
			if (request.getSession().getAttribute("admin") == null) {
				response.sendRedirect("/admin/tologin");
			}else{
				return true;
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
