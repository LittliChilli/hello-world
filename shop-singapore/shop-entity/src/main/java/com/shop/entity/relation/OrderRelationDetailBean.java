package com.shop.entity.relation;

import java.util.ArrayList;
import java.util.List;

import com.shop.entity.basic.UserOrder;
import com.shop.entity.basic.UserOrderDetails;

/**
 * 订单与订单详情关联Bean
 * 
 * @version
 * @author zousheng
 * @date:2016年12月2日
 */
public class OrderRelationDetailBean {

	/**
	 * 用户订单
	 */
	private UserOrder userOrder;

	/**
	 * 用户订单详情(一个订单多个商品)
	 */
	private List<UserOrderDetails> userOrderDetailsList = new ArrayList<UserOrderDetails>();

	public UserOrder getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(UserOrder userOrder) {
		this.userOrder = userOrder;
	}

	public List<UserOrderDetails> getUserOrderDetailsList() {
		return userOrderDetailsList;
	}

	public void setUserOrderDetailsList(List<UserOrderDetails> userOrderDetailsList) {
		this.userOrderDetailsList = userOrderDetailsList;
	}
}
