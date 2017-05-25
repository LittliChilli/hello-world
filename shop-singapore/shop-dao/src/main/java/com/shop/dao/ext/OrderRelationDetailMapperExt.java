package com.shop.dao.ext;

import java.util.List;
import java.util.Map;

public interface OrderRelationDetailMapperExt {

	/**
	 * 查询我的订单列表
	 * 
	 * @version 
	 * @author zousheng 
	 * @date:2016年12月2日
	 *
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectMyOrderList(Map<String, Object> map);
	
}
