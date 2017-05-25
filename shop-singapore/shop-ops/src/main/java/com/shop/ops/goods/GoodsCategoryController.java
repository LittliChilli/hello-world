package com.shop.ops.goods;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.common.dateutil.DateUtil;
import com.shop.entity.basic.GoodsCategory;
import com.shop.service.category.GoodsCategoryService;

/**
 * 商品类别Controller
 * 
 * @author lion
 * @date 2016-11-24
 * 
 */
@Controller
@RequestMapping(value = "goodsCategory")
public class GoodsCategoryController {

	@Autowired
	public GoodsCategoryService goodsCategoryService;

	/**
	 * 添加商品一级分类
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * 
	 */
	@RequestMapping(value = "addGoodsFirstCategory", method = RequestMethod.GET)
	@ResponseBody
	public Result addGoodsFirstCategory(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String categoryName = request.getParameter("categoryName");
		if (StringUtils.isEmpty(categoryName)) {
			result.setResult("1");
			result.setMsg("类别名称不能为空");
			return result;
		}
		GoodsCategory goodsCategory = new GoodsCategory();
		goodsCategory.setCategoryName(categoryName.trim());
		goodsCategory.setCreateTime(DateUtil.getNow());
		goodsCategory.setUpdateTime(DateUtil.getNow());
		goodsCategory.setIsDelete(false);
		goodsCategoryService.addGoodsFirstCategory(goodsCategory);
		return result;
	}

	/**
	 * 修改商品一级分类
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "updateGoodsFirstCategory", method = RequestMethod.GET)
	public Result updateGoodsFirstCategory(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String categoryId = request.getParameter("goodsCategoryId");
		String categoryName = request.getParameter("categoryName");
		if (StringUtils.isEmpty(categoryId)) {
			result.setResult("1");
			result.setMsg("操作失败，再试下吧");
			return result;
		}
		if (StringUtils.isEmpty(categoryName)) {
			result.setResult("1");
			result.setMsg("类别名称不能为空");
			return result;
		}
		GoodsCategory goodscategory = new GoodsCategory();
		goodscategory.setId(Long.parseLong(categoryId));
		goodscategory.setCategoryName(categoryName.trim());
		goodscategory.setUpdateTime(DateUtil.getNow());
		goodsCategoryService.updateGoodsFirstCategory(goodscategory);
		return result;
	}

	/**
	 * 
	 * 跳转到商品一级分类列表页面
	 * 
	 * lion 2016年11月28日
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toGoodsCategoryList", method = RequestMethod.GET)
	public String toGoodsCategoryList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/goods/goodsCategoryList";
	}

	/**
	 * 分页查询商品一级分类列表
	 * 
	 * @author lion
	 * @date 2016-11-24
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * 
	 */
	@RequestMapping(value = "queryGoodsCategoryListByPage", method = RequestMethod.GET)
	@ResponseBody
	public Result queryGoodsCategoryListByPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String pageNum = request.getParameter("pageNum");
		Map<String, Object> dataMap = goodsCategoryService
				.queryGoodsCategoryByPage(pageNum);
		model.addAttribute("pageInfo", dataMap.get("pageInfo"));
		model.addAttribute("goodsCategoryList",
				dataMap.get("goodsCategoryList"));
		result.setData(model);
		return result;
	}

	/**
	 * 
	 * 根据id删除商品类别
	 * 
	 * lion 2016年11月29日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteGoodsFirstCategoryById", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteGoodsFirstCategoryById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String goodsCategoryId = request.getParameter("goodsCategoryId");
		if (StringUtils.isEmpty(goodsCategoryId)
				|| !StringUtils.isNumeric(goodsCategoryId)) {
			result.setResult("1");
			result.setMsg("操作失败，刷新试下吧");
			return result;
		}
		goodsCategoryService.deleteGoodsFirstCategoryById(Long
				.parseLong(goodsCategoryId));
		return result;
	}
}
