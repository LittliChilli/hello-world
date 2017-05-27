package com.shop.service.category.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.dateutil.DateUtil;
import com.shop.common.exception.ServiceException;
import com.shop.dao.basic.GoodsCategoryMapper;
import com.shop.entity.basic.GoodsCategory;
import com.shop.entity.basic.GoodsCategoryExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.service.category.GoodsCategoryService;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;

	@Override
	public void addGoodsFirstCategory(GoodsCategory goodsCategory)
			throws ServiceException {
		if (CollectionUtils.isEmpty(this
				.findGoodsCategoryByCategoryName(goodsCategory
						.getCategoryName()))) {
			goodsCategoryMapper.insertSelective(goodsCategory);
		} else {
			throw new ServiceException("1", "类别已存在，换一个吧");
		}
	}

	@Override
	public void updateGoodsFirstCategory(GoodsCategory goodsCategory)
			throws ServiceException {
		if (CollectionUtils.isEmpty(this
				.findGoodsCategoryByCategoryName(goodsCategory
						.getCategoryName()))) {
			goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);
		} else {
			throw new ServiceException("1", "类别已存在，换一个吧");
		}
	}

	@Override
	public List<GoodsCategory> findGoodsCategoryByCategoryName(
			String categoryName) {
		GoodsCategoryExample example = new GoodsCategoryExample();
		example.createCriteria().andCategoryNameLike(categoryName);
		List<GoodsCategory> goodsCategoryList = goodsCategoryMapper
				.selectByExample(example);
		return goodsCategoryList;
	}

	@Override
	public Map<String, Object> queryGoodsCategoryByPage(String pageNum) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageHelper.startPage(Integer.parseInt(pageNum), 10);
		GoodsCategoryExample example = new GoodsCategoryExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		List<GoodsCategory> goodsCategoryList = goodsCategoryMapper
				.selectByExample(example);
		PageInfo<GoodsCategory> pageInfo = new PageInfo<GoodsCategory>(
				goodsCategoryList);
		dataMap.put("goodsCategoryList", goodsCategoryList);
		dataMap.put("pageInfo", pageInfo);
		return dataMap;
	}

	@Override
	public void deleteGoodsFirstCategoryById(Long goodsCategoryId) {
		GoodsCategory goodsCategory = new GoodsCategory();
		goodsCategory.setId(goodsCategoryId);
		goodsCategory.setUpdateTime(DateUtil.getNow());
		goodsCategory.setIsDelete(true);
		goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);
	}

	@Override
	public List<GoodsCategory> findAllGoodsCategory() {
		GoodsCategoryExample example = new GoodsCategoryExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		example.setOrderByClause("create_time DESC");
		List<GoodsCategory> goodsCategoryList = goodsCategoryMapper
				.selectByExample(example);
		return goodsCategoryList;
	}

}
