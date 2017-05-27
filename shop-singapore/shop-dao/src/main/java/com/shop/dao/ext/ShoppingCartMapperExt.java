package com.shop.dao.ext;

import java.util.List;
import java.util.Map;

import com.shop.entity.basic.UserOrderDetails;
import com.shop.entity.excel.ShoppingExportExcel;

public interface ShoppingCartMapperExt {

	/**
	 * 查询购物车
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectShopCart(Map<String, Object> map);
	
	
	/**
	 * 批量查询购物车
	 * @param cartIds
	 * @return
	 */
	List<UserOrderDetails> selectUserOrderDetailsByIds(List<Long> cartIds);

	/**
	 * 
	 * 查询采购清单数据
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param map
	 * @return
	 */
	List<ShoppingExportExcel> selectShoppingList(Map<String, Object> map);

}
