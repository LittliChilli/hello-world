package com.shop.service.relation.orderdetail;

import java.util.List;

import com.shop.entity.basic.User;
import com.shop.entity.relation.OrderRelationDetailBean;

/**
 * Order关联Detail订单详情
 * 
 * @version
 * @author zousheng
 * @date:2016年12月2日
 */
public interface OrderRelationDetailService {

	/**
	 * 查询我的订单列表
	 * @version 
	 * @author zousheng 
	 * @date:2016年12月6日
	 *
	 * @param user
	 * @param orderStatus
	 * @param orderSatusList
	 * @return
	 */
	List<OrderRelationDetailBean> queryMyOrderList(User user, String orderStatus, List<String> orderSatusList);
	
	/**
	 * 查询我的订单详情信息
	 * @param user
	 * @param orderSn
	 * @return
	 */
	List<OrderRelationDetailBean> queryMyOrderDetail(User user, String orderSn);
}
