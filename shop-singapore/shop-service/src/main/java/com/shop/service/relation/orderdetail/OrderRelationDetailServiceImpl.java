package com.shop.service.relation.orderdetail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dao.basic.UserOrderMapper;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserOrder;
import com.shop.entity.basic.UserOrderDetails;
import com.shop.entity.basic.UserOrderExample;
import com.shop.entity.basic.UserOrderExample.Criteria;
import com.shop.entity.relation.OrderRelationDetailBean;
import com.shop.service.userOrderDateils.UserOrderDetailsService;

/**
 * Order关联Detail订单详情
 * 
 * @version
 * @author zousheng
 * @date:2016年12月2日
 */
@Service
public class OrderRelationDetailServiceImpl implements OrderRelationDetailService {

	@Autowired
	private UserOrderMapper userOrderMapper;

	@Autowired
	private UserOrderDetailsService userOrderDetailsService;

	@Override
	public List<OrderRelationDetailBean> queryMyOrderList(User user, String orderStatus, List<String> orderSatusList) {

		List<UserOrder> userOrderList = queryMyOrderList(user, orderStatus, null, orderSatusList);

		return queryMyOrderList(userOrderList, 2);
	}

	/**
	 * 查询我的订单列表信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月7日
	 *
	 * @param user
	 * @param orderStatus
	 * @param orderSn
	 * @param orderSatusList
	 * @return
	 */
	private List<UserOrder> queryMyOrderList(User user, String orderStatus, String orderSn,
			List<String> orderSatusList) {

		UserOrderExample example = new UserOrderExample();
		Criteria criteria = example.createCriteria();

		if (user != null) {
			criteria.andUserIdEqualTo(user.getId());
		}

		if (StringUtils.isNotEmpty(orderStatus)) {
			criteria.andOrderStatusEqualTo(orderStatus);
		}

		if (StringUtils.isNotEmpty(orderSn)) {
			criteria.andOrderSnEqualTo(orderSn);
		}

		if (StringUtils.isEmpty(orderStatus) && CollectionUtils.isNotEmpty(orderSatusList)) {
			criteria.andOrderStatusIn(orderSatusList);
		}

		example.setOrderByClause("create_time desc");

		List<UserOrder> userOrderList = userOrderMapper.selectByExample(example);

		return userOrderList;
	}

	/**
	 * 查询订单列表中商品列表信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月7日
	 *
	 * @param userOrderList
	 * @param num
	 * @return
	 */
	private List<OrderRelationDetailBean> queryMyOrderList(List<UserOrder> userOrderList, Integer num) {

		List<OrderRelationDetailBean> orderRelationDetailBeanList = new ArrayList<OrderRelationDetailBean>();

		for (UserOrder userOrder : userOrderList) {
			List<UserOrderDetails> userOrderDetailsList = userOrderDetailsService
					.queryShopCartDetailByOrderSn(userOrder.getUserId(), userOrder.getOrderSn());

			if (CollectionUtils.isEmpty(userOrderDetailsList)) {
				continue;
			}

			OrderRelationDetailBean orderRelationDetailBean = new OrderRelationDetailBean();

			// 转换orderStatus为中文描述
			userOrder.setOrderStatus(convertOrderStatusShow(userOrder.getOrderStatus()));
			orderRelationDetailBean.setUserOrder(userOrder);

			int size = 0;

			// 跟换图片路径指定线上展示
			for (UserOrderDetails userOrderDetails : userOrderDetailsList) {

				size += 1;
				// 限制读取订单商品列表数量
				if (num != null && size > num) {
					break;
				}

				userOrderDetailsService.getGoodsPic(userOrderDetails);

				orderRelationDetailBean.getUserOrderDetailsList().add(userOrderDetails);
			}

			orderRelationDetailBeanList.add(orderRelationDetailBean);
		}

		return orderRelationDetailBeanList;
	}

	@Override
	public List<OrderRelationDetailBean> queryMyOrderDetail(User user, String orderSn) {
		List<UserOrder> userOrderList = queryMyOrderList(user, null, orderSn, null);

		return queryMyOrderList(userOrderList, null);
	}

	/**
	 * 转换orderStatus为中文描述
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月5日
	 *
	 * @param orderStatus
	 * @return
	 */
	private String convertOrderStatusShow(String orderStatus) {

		String str = orderStatus;

		if ("buying".equals(orderStatus)) {
			str = "等待采购";
		} else if ("alreadyBuy".equals(orderStatus)) {
			str = "等待收货";
		} else if ("confirmReceived".equals(orderStatus)) {
			str = "确认收货";
		} else if ("cancel".equals(orderStatus)) {
			str = "已取消";
		}

		return str;
	}
}
