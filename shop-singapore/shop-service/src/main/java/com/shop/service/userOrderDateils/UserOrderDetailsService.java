package com.shop.service.userOrderDateils;

import java.util.List;
import java.util.Map;

import com.shop.entity.basic.Goods;
import com.shop.entity.basic.UserOrderDetails;

public interface UserOrderDetailsService {

	/**
	 * 添加商品至购物车
	 * 
	 * @param goodsId
	 * @param number
	 * @param userId
	 * @return
	 */
	UserOrderDetails addToCart(Map<String, Object> map);

	/**
	 * 下单添加商品详情信息
	 * 
	 * @param shoppingCart
	 * @return
	 */
	UserOrderDetails addUserOrderDetail(UserOrderDetails shoppingCart);

	/**
	 * 购物车展示
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getCartItems(Map<String, Object> map);

	/**
	 * 根据购物车id删除商品
	 * 
	 * @param id
	 * @return
	 */
	void deleteCartItemById(Long id);

	/**
	 * 根据Id查询购物车商品
	 * 
	 * @param id
	 * @return
	 */
	UserOrderDetails queryCartById(Long id);

	/**
	 * 根据条件查询购物明细
	 * 
	 * @version
	 * @author hyj
	 * @date:2016年11月30日
	 * @param map
	 * @return List<UserOrderDetails>
	 */
	List<UserOrderDetails> selectCartByCondition(Map<String, Object> map);

	/**
	 * 添加商品明细图片
	 * 
	 * @version
	 * @author hyj
	 * @date:2016年11月30日
	 * @param userOrderDetails
	 * @return UserOrderDetails
	 */
	UserOrderDetails getGoodsPic(UserOrderDetails userOrderDetails);

	/**
	 * 批量修改购物车信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月2日
	 *
	 * @param userOrderDetailsList
	 */
	void updateUserOrderDetailsBatch(List<UserOrderDetails> userOrderDetailsList);

	/**
	 * 根据订单编号查询购物车订单详情信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月2日
	 *
	 * @param userId
	 * @param orderSn
	 * @return
	 */
	List<UserOrderDetails> queryShopCartDetailByOrderSn(Long userId, String orderSn);

}
