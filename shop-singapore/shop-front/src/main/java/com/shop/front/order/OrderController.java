package com.shop.front.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.ConstantBasic;
import com.shop.common.Result;
import com.shop.common.Utils;
import com.shop.common.exception.Message;
import com.shop.common.exception.ServiceException;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserOrder;
import com.shop.entity.basic.UserOrderDetails;
import com.shop.entity.basic.UserReceiptAddress;
import com.shop.entity.relation.OrderRelationDetailBean;
import com.shop.front.common.UserController;
import com.shop.service.address.RecAddressService;
import com.shop.service.order.OrderService;
import com.shop.service.relation.orderdetail.OrderRelationDetailService;
import com.shop.service.userOrderDateils.UserOrderDetailsService;;

@Controller
@RequestMapping(value = "/singapore/order")
public class OrderController extends UserController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserOrderDetailsService userOrderDetailsService;

	@Autowired
	private RecAddressService recAddressService;

	@Autowired
	private OrderRelationDetailService orderRelationDetailService;

	/**
	 * 确认订单(由购物车进入)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "orderConfirm_byCart")
	@ResponseBody
	public Result orderConfirm(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		String shopCartOrder = request.getParameter("shop_cart_order");

		ArrayList<String> cartIds = Utils.splitArrayString(shopCartOrder, ConstantBasic.Punctuation.COMMA);

		if (CollectionUtils.isEmpty(cartIds)) {
			throw new ServiceException(Result.ERROR_CODE, "请选择结算商品");
		}

		UserReceiptAddress defaultAddr = recAddressService.queryDefaultAddress(user.getId());
		if (defaultAddr == null) {
			throw new ServiceException(Message.user_address_not_exist);
		}

		List<UserOrderDetails> cartList = new ArrayList<UserOrderDetails>();
		for (String id : cartIds) {
			if (StringUtils.isNotEmpty(id)) {
				UserOrderDetails cart = userOrderDetailsService.queryCartById(Long.parseLong(id));
				cartList.add(cart);
			}
		}

		Map<String, Object> orderMap = orderService.confirmOrder(cartList, defaultAddr);
		model.addAttribute("orderMap", orderMap);

		return super.result(model);
	}

	/**
	 * 更新订单信息(收货地址)
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月2日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "order_create_by_cart")
	@ResponseBody
	public Result orderCreate(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession2Exception(request);

		String orderSn = request.getParameter("order_sn");

		if (StringUtils.isEmpty(orderSn)) {
			throw new ServiceException(Result.ERROR_CODE, "订单信息不存在");
		}

		String addressId = request.getParameter("address_id");

		if (StringUtils.isEmpty(addressId)) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		UserReceiptAddress userReceiptAddress = recAddressService.queryRecAddressById(Long.valueOf(addressId));

		if (userReceiptAddress == null) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("orderSn", orderSn);
		queryMap.put("userId", user.getId().toString());

		List<UserOrder> userOrderList = orderService.queryOrderByCondition(queryMap);

		if (CollectionUtils.isNotEmpty(userOrderList) && userOrderList.size() == 1) {
			UserOrder order = userOrderList.get(0);

			order.setReceiveAddress(JSONObject.toJSONString(userReceiptAddress));

			orderService.updateUserOrderInfo(order);

		} else {
			throw new ServiceException(Result.ERROR_CODE, "订单信息不存在");
		}

		return super.result(model);
	}

	/**
	 * 订单确认，直接下单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "orderConfirm_Direct")
	@ResponseBody
	public Result orderConfirmDirect(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);
		String goodsId = request.getParameter("goodsId");
		String number = request.getParameter("shop_number");

		if (StringUtils.isEmpty(goodsId)) {
			throw new ServiceException(Result.ERROR_CODE, "请选择结算商品");
		}

		if (StringUtils.isEmpty(number)) {
			throw new ServiceException(Result.ERROR_CODE, "请选择商品数量");
		}

		UserReceiptAddress defaultAddr = recAddressService.queryDefaultAddress(user.getId());
		if (defaultAddr == null) {
			throw new ServiceException(Message.user_address_not_exist);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", Long.parseLong(goodsId));
		map.put("number", Integer.parseInt(number));
		map.put("isCreateNew", Boolean.TRUE);
		map.put(User.class.getSimpleName(), user);
		map.put(UserReceiptAddress.class.getSimpleName(), defaultAddr);

		// 1：购物车创建商品；2：生成buying订单
		Map<String, Object> resultMap = orderService.orderConfirmDirect(map);
		model.addAttribute("resultMap", resultMap);

		return super.result(model);
	}

	/**
	 * 我的订单列表
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月2日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_my_order_list")
	@ResponseBody
	public Result queryMyOrderList(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		List<String> orderSatusList = new ArrayList<String>();
		orderSatusList.add("buying");
		orderSatusList.add("alreadyBuy");
		orderSatusList.add("confirmReceived");

		String orderStatus = request.getParameter("order_status");

		List<OrderRelationDetailBean> orderRelationDetailBeanList = orderRelationDetailService.queryMyOrderList(user,
				orderStatus, orderSatusList);

		model.addAttribute("relationList", orderRelationDetailBeanList);

		return super.result(model);
	}

	/**
	 * 取消订单
	 * 
	 * @version
	 * @author hyj
	 * @date:2016年12月5日
	 * @param request
	 * @param response
	 * @param model
	 * @return Result
	 */
	@RequestMapping(value = "cancel_my_order", method = RequestMethod.POST)
	@ResponseBody
	public Result cancelOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		String orderSn = request.getParameter("order_sn");
		if (StringUtils.isEmpty(orderSn)) {
			throw new ServiceException(Result.ERROR_CODE, "订单号为空");
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("orderSn", orderSn);
		queryMap.put("userId", user.getId());
		orderService.cancelOrderByOrderSn(queryMap);

		return super.result(model);
	}

	/**
	 * 确认订单收货
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月7日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "confirm_my_order", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmMyOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		String orderSn = request.getParameter("order_sn");
		if (StringUtils.isEmpty(orderSn)) {
			throw new ServiceException(Result.ERROR_CODE, "订单号为空");
		}

		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("orderSn", orderSn);
		queryMap.put("userId", user.getId().toString());

		List<UserOrder> userOrderList = orderService.queryOrderByCondition(queryMap);

		if (CollectionUtils.isEmpty(userOrderList)) {
			throw new ServiceException(Result.ERROR_CODE, "订单不存在");
		}

		UserOrder userOrder = userOrderList.get(0);

		if (!"alreadyBuy".equals(userOrder.getOrderStatus())) {
			throw new ServiceException(Result.ERROR_CODE, "订单状态不正确");
		}

		userOrder.setOrderStatus("confirmReceived");
		orderService.updateUserOrderInfo(userOrder);

		return super.result(model);
	}

	/**
	 * 我的订单详情
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月2日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_my_order_detail", method = RequestMethod.GET)
	@ResponseBody
	public Result queryMyOrderDetail(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		String orderSn = request.getParameter("order_sn");

		if (StringUtils.isEmpty(orderSn)) {
			throw new ServiceException(Result.ERROR_CODE, "订单编码不能为空");
		}

		List<OrderRelationDetailBean> orderRelationDetailBeanList = orderRelationDetailService.queryMyOrderDetail(user,
				orderSn);

		model.addAttribute("relationList", orderRelationDetailBeanList);

		return super.result(model);
	}
}
