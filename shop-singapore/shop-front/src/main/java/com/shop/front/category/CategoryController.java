package com.shop.front.category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.entity.basic.GoodsCategory;
import com.shop.service.category.GoodsCategoryService;

@Controller
@RequestMapping(value = "/singapore/category")
public class CategoryController {
	
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@RequestMapping(value = "query_all_category")
	@ResponseBody
	public Result queryAllCategory(HttpServletRequest request, HttpServletResponse response, Model model){
		Result result = new Result();
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.findAllGoodsCategory();
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		result.setData(model);
		return result;
	}

}
