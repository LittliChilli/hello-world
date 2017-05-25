package com.shop.front.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.common.enums.GoodsTypeEnum;
import com.shop.common.exception.ServiceException;
import com.shop.entity.basic.Goods;
import com.shop.entity.basic.GoodsCategory;
import com.shop.front.common.UserController;
import com.shop.service.category.GoodsCategoryService;
import com.shop.service.goods.GoodsService;

@Controller
@RequestMapping(value = "/singapore/goods")
public class GoodsController extends UserController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	/**
	 * 首页所有商品类型
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月1日
	 *
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_all_goods_category")
	@ResponseBody
	public Result queryAllGoodsCategory(HttpServletRequest request, HttpServletResponse httpServletResponse,
			Model model) {

		List<GoodsCategory> goodsCategoryList = goodsCategoryService.findAllGoodsCategory();
		model.addAttribute("goodsCategoryList", goodsCategoryList);

		return super.result(model);
	}

	/**
	 * 首页所有商品
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月1日
	 *
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_all_goods")
	@ResponseBody
	public Result queryAllGoods(HttpServletRequest request, HttpServletResponse httpServletResponse, Model model) {

		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("is_delete", "false");
		queryMap.put("goodsType", GoodsTypeEnum.GENERAL.getCode());
		model.addAttribute("goodsList", goodsService.queryGoodsByCondition(queryMap));

		return super.result(model);
	}

	/**
	 * 商品详情
	 * 
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "singapore_query_goodsDetail")
	@ResponseBody
	public Result queryGoodsDetail(HttpServletRequest request, HttpServletResponse httpServletResponse, Model model) {

		String goodsId = request.getParameter("goodsId");
		if (StringUtils.isEmpty(goodsId)) {
			throw new ServiceException(Result.ERROR_CODE, "请选择商品");
		}

		Goods goods = goodsService.getDetailById(Long.parseLong(goodsId));
		model.addAttribute(Goods.class.getSimpleName(), goods);

		return super.result(model);
	}
}
