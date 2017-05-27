package com.shop.service.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.Result;
import com.shop.common.exception.ServiceException;

/**
 * 异常统一处理类
 * 
 * @version
 * @author zousheng
 * @date:2016年5月14日
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		Result result = new Result();

		handleResultException(result, ex);

		// JSON格式返回
		try {
			// 这句话的意思，是让浏览器用utf8来解析返回的数据
			response.setHeader("Content-type", "application/json;charset=UTF-8");
			// 这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(JSONObject.toJSONString(result));
			pw.flush();
			pw.close();
			return new ModelAndView();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 返回原页面弹出提示信息
		return new ModelAndView("redirect:" + request.getHeader("referer")).addObject("errMsg", ex.getMessage());
	}

	/**
	 * 处理result异常结果
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年5月14日
	 *
	 * @param result
	 * @param ex
	 */
	private void handleResultException(Result result, Exception ex) {
		if (ex instanceof ServiceException) {

			ServiceException e = (ServiceException) ex;
			result.setResult(String.valueOf(e.getCode()));
			result.setMsg(e.getMessage());
		} else {

			ex.printStackTrace();
			result.setResult("1");
			result.setMsg("服务器开小差，请稍后再试");
		}
	}

}
