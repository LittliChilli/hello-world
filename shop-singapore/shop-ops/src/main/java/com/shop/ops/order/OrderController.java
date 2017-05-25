package com.shop.ops.order;

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
import com.shop.common.excel.ExcelUtil;
import com.shop.entity.excel.ShoppingExportExcel;
import com.shop.service.order.OrderService;

/**
 * 订单controller
 * 
 * @author lion
 * @date 2016-11-24
 * 
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {

	@Autowired
	public OrderService orderService;

	@RequestMapping(value = "/toUserOrderList")
	public String toOrderList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "/back/order/orderList";
	}

	/**
	 * 
	 * 分页查询订单列表
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "queryOrderListByPage", method = RequestMethod.GET)
	@ResponseBody
	public Result queryOrderListByPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String orderSn = request.getParameter("orderSn");
		String orderStatus = request.getParameter("orderStatus");
		String startTime = request.getParameter("startDate");
		String endTime = request.getParameter("endDate");
		String pageNum = request.getParameter("pageNum");
		String receiptAddress = request.getParameter("receiptAddress");
		startTime = DateUtil.getStartTimeForDay(startTime, 0);
		endTime = DateUtil.getEndDate(endTime);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("orderSn", orderSn);
		queryMap.put("orderStatus", orderStatus);
		queryMap.put("startTime", startTime);
		queryMap.put("endTime", endTime);
		queryMap.put("pageNum", pageNum);
		queryMap.put("receiptAddress", receiptAddress);
		Map<String, Object> dataMap = orderService
				.queryOrderListByPage(queryMap);
		model.addAttribute("pageInfo", dataMap.get("pageInfo"));
		model.addAttribute("orderList", dataMap.get("orderList"));
		result.setData(model);
		return result;
	}

	/**
	 * 
	 * 取消用户订单
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "cancelUserOrderById", method = RequestMethod.GET)
	@ResponseBody
	public Result cancelUserOrderById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String userOrderId = request.getParameter("orderId");
		if (StringUtils.isEmpty(userOrderId)) {
			result.setResult("1");
			result.setMsg("操作失败，再试下吧");
			return result;
		}
		orderService.cancelUserOrderByOrderId(Long.parseLong(userOrderId));
		return result;
	}

	/**
	 * 
	 * 确认用户订单已收获
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "confirmUserOrderReceivedById", method = RequestMethod.GET)
	@ResponseBody
	public Result confirmUserOrderReceivedById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String userOrderId = request.getParameter("orderId");
		if (StringUtils.isEmpty(userOrderId)) {
			result.setResult("1");
			result.setMsg("操作失败，再试下吧");
			return result;
		}
		orderService.confirmUserOrderReceivedById(Long.parseLong(userOrderId));
		return result;
	}

	/**
	 * 
	 * 确认采购完成
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "confirmUserOrderAlreadyBuyById", method = RequestMethod.GET)
	@ResponseBody
	public Result confirmUserOrderAlreadyBuyById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String orderId = request.getParameter("orderId");
		if (StringUtils.isEmpty(orderId) || !StringUtils.isNumeric(orderId)) {
			result.setResult("1");
			result.setMsg("操作失败，再试下吧！");
			return result;
		}
		orderService.confirmUserOrderAlreadyBuyById(Long.parseLong(orderId));
		return result;
	}

	/**
	 * 
	 * 导出采购清单
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "exportShoppingListExcel", method = RequestMethod.GET)
	public void exportShoppingListExcel(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String orderStatus = request.getParameter("orderStatus");
		String receiptAddress = request.getParameter("receiptAddress");
		if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		String fileName = startTime + "至" + endTime + "采购清单.xls";
		startTime = DateUtil.getStartTimeForDay(startTime, 1);
		endTime = DateUtil.getEndTime(endTime);
		List<ShoppingExportExcel> shoppingList = orderService
				.findShoppingListForExportExcel(startTime, endTime, orderStatus,receiptAddress);
		String[] title = { "采购清单", "商品名称", "数量", "商品总价" };
		ExcelUtil.exportExcel(fileName, title, shoppingList, request, response);
	}

	/**
	 * 
	 * 导出配送信息
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "exportDeliveryListExcel", method = RequestMethod.GET)
	public void exportDeliveryListExcel(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String orderSn = request.getParameter("orderSn");
		String orderStatus = request.getParameter("orderStatus");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String receiptAddress = request.getParameter("receiptAddress");
		startTime = DateUtil.getStartTimeForDay(startTime, 0);
		endTime = DateUtil.getEndDate(endTime);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("orderSn", orderSn);
		queryMap.put("orderStatus", orderStatus);
		queryMap.put("startTime", startTime);
		queryMap.put("endTime", endTime);
		queryMap.put("receiptAddress", receiptAddress);
		orderService.exportDeliveryListExcel(queryMap, response);
	}
}
