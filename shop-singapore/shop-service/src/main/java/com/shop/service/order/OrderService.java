package com.shop.service.order;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.shop.entity.basic.TeamMemberRecord;
import com.shop.entity.basic.TeamOrderRecord;
import com.shop.entity.basic.UserOrder;
import com.shop.entity.basic.UserOrderDetails;
import com.shop.entity.basic.UserReceiptAddress;
import com.shop.entity.excel.ShoppingExportExcel;

public interface OrderService {

	/**
	 * 确认下单(由购物车进入)
	 * 
	 * @param user
	 * @return
	 */
	Map<String, Object> confirmOrder(List<UserOrderDetails> cartList, UserReceiptAddress userReceiptAddress);

	/**
	 * 下单
	 * 
	 * @param shoppingCartId
	 * @param order
	 */
	void createOrder(List<String> idList, UserOrder order);

	/**
	 * 确认下单(直接确认)
	 * 
	 * @param map
	 */
	Map<String, Object> orderConfirmDirect(Map<String, Object> map);

	/**
	 * 
	 * 分页查询订单列表
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param queryMap
	 * @return
	 */
	Map<String, Object> queryOrderListByPage(Map<String, String> queryMap);

	/**
	 * 
	 * 根据Id取消用户订单
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param orderId
	 */
	void cancelUserOrderByOrderId(Long orderId);

	/**
	 * 
	 * 根据Id确认用户订单收获
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param orderId
	 */
	void confirmUserOrderReceivedById(Long orderId);

	/**
	 * 
	 * 查询采购清单数据
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ShoppingExportExcel> findShoppingListForExportExcel(String startTime, String endTime, String orderStatus,
			String receiptAddress);

	/**
	 * 根据userId和orderStatus查询用户订单数量
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 * 
	 * @param userId
	 * @param orderStatus
	 * @return
	 */
	Integer queryOrdeNumByOrderStatus(Long userId, String orderStatus);

	/**
	 * 
	 * 查询送货信息
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findDeliveryListForExportExcel();

	/**
	 * 查询订单
	 * 
	 * @param queryMap
	 * @return
	 */
	List<UserOrder> queryOrderByCondition(Map<String, String> queryMap);

	/**
	 * 根据条件取消订单
	 * 
	 * @param map
	 */
	void cancelOrderByOrderSn(Map<String, Object> map);

	/**
	 * 
	 * 根据id确认采购完成
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param orderId
	 */
	void confirmUserOrderAlreadyBuyById(Long orderId);

	/**
	 * 
	 * 导出配送信息
	 * 
	 * lion 2016年12月1日
	 * 
	 * @param queryMap
	 */
	void exportDeliveryListExcel(Map<String, String> queryMap, HttpServletResponse response);

	/**
	 * 更新userOrder信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月6日
	 *
	 * @param userOrder
	 */
	void updateUserOrderInfo(UserOrder userOrder);

	/**
	 * 拼团成功生成用户下单
	 * 
	 * @param cartList
	 * @param userReceiptAddress
	 * @param orderSn
	 * @return
	 */
	Map<String, Object> orderConfirmTeamGoods(TeamOrderRecord teamOrderRecord, TeamMemberRecord teamMemberRecord,
			Integer number);
}
