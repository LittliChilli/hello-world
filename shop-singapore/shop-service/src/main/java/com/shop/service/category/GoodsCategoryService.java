package com.shop.service.category;

import java.util.List;
import java.util.Map;

import com.shop.common.exception.ServiceException;
import com.shop.entity.basic.GoodsCategory;

public interface GoodsCategoryService {

	/**
	 * 添加商品一级分类
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param categoryName
	 * 
	 */
	public void addGoodsFirstCategory(GoodsCategory goodsCategory)
			throws ServiceException;

	/**
	 * 修改商品一级分类
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param goodsCategory
	 */
	public void updateGoodsFirstCategory(GoodsCategory goodsCategory)
			throws ServiceException;

	/**
	 * 
	 * 根据类别名称查询类别信息
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param CategoryName
	 * @return
	 */
	public List<GoodsCategory> findGoodsCategoryByCategoryName(
			String categoryName);

	/**
	 * 
	 * 分页查询商品类别
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param categoryName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> queryGoodsCategoryByPage(String pageNum);

	/**
	 * 
	 * 根据id删除商品类别
	 * 
	 * lion 2016年11月29日
	 * 
	 * @param goodsCategoryId
	 */
	public void deleteGoodsFirstCategoryById(Long goodsCategoryId);

	/**
	 * 
	 * 查询所有商品类别
	 * 
	 * lion 2016年11月30日
	 * 
	 * @return
	 */
	public List<GoodsCategory> findAllGoodsCategory();
	
	

}
