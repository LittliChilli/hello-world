package com.shop.ops.goods;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shop.common.Result;
import com.shop.common.enums.GoodsTypeEnum;
import com.shop.entity.basic.Goods;
import com.shop.entity.basic.GoodsCategory;
import com.shop.service.category.GoodsCategoryService;
import com.shop.service.goods.GoodsService;

/**
 * 
 * 商品controler
 * 
 * @author lion
 * @date 2016-11-30
 * 
 */
@Controller
@RequestMapping(value = "goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	/**
	 * 
	 * 跳转到商品列表页面
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toGoodsList")
	public String toGoodsList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<GoodsCategory> goodsCategoryList = goodsCategoryService
				.findAllGoodsCategory();
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		return "/back/goods/goodsList";
	}

	/**
	 * 
	 * 跳转到商品添加页面
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUploadGoods")
	public String toUploadGoods(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<GoodsCategory> goodsCategoryList = goodsCategoryService
				.findAllGoodsCategory();
		List<GoodsTypeEnum> goodsTypeList = new ArrayList<GoodsTypeEnum>();
		goodsTypeList.add(GoodsTypeEnum.GENERAL);
		goodsTypeList.add(GoodsTypeEnum.TEAM_GROUP);
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("goodsTypeList", goodsTypeList);
		return "/back/goods/uploadGoods";
	}

	/**
	 * 
	 * 分页查询商品列表
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryGoodsListByPage", method = RequestMethod.GET)
	@ResponseBody
	public Result queryGoodsListByPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		Map<String, String> queryMap = new HashMap<String, String>();
		String pageNum = request.getParameter("pageNum");
		String goodsName = request.getParameter("goodsName");
		String goodsTypeId = request.getParameter("goodsTypeId");
		queryMap.put("pageNum", pageNum);
		queryMap.put("goodsName", goodsName);
		queryMap.put("goodsTypeId", goodsTypeId);
		Map<String, Object> resultMap = goodsService
				.queryGoodsListByPage(queryMap);
		model.addAttribute("pageInfo", resultMap.get("pageInfo"));
		model.addAttribute("goodsList", resultMap.get("goodsList"));
		result.setData(model);
		return result;
	}

	/**
	 * 
	 * 添加/修改 商品
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addGoods", method = RequestMethod.POST)
	@ResponseBody
	public Result addGoods(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam("goodsPic1") MultipartFile goodsPic,
			@RequestParam("goodsPic2") MultipartFile goodsDetailsShowPic,
			@RequestParam("goodsDetailsPic1") MultipartFile goodsDetailsPic1,
			@RequestParam("goodsDetailsPic2") MultipartFile goodsDetailsPic2,
			@RequestParam("goodsDetailsPic3") MultipartFile goodsDetailsPic3,
			@RequestParam("goodsDetailsPic4") MultipartFile goodsDetailsPic4,
			@RequestParam("goodsDetailsPic5") MultipartFile goodsDetailsPic5,
			@RequestParam("goodsDetailsPic6") MultipartFile goodsDetailsPic6,
			@RequestParam("goodsDetailsPic7") MultipartFile goodsDetailsPic7,
			@RequestParam("goodsDetailsPic8") MultipartFile goodsDetailsPic8,
			@RequestParam("goodsDetailsPic9") MultipartFile goodsDetailsPic9,
			@RequestParam("goodsDetailsPic10") MultipartFile goodsDetailsPic10) {
		
		Result result = new Result();
		
		Map<String, String> strMap = new HashMap<String, String>();
		Map<String, MultipartFile> picMap = new HashMap<String, MultipartFile>();
		String goodsName = request.getParameter("goodsName");
		String goodsCategoryId = request.getParameter("category");
		String goodsPrice = request.getParameter("goodsPrice");
		String goodsSoldNumber = request.getParameter("goodsSoldNumber");
		String operationType = request.getParameter("operationType");
		String goodsId = request.getParameter("goodsId");
		String goodsTypeCode = request.getParameter("goodsType");
		String teamGroupNum = request.getParameter("teamGroupNum");
		String teamGroupTime = request.getParameter("teamGroupTime");//单位：小时
		
		if(GoodsTypeEnum.TEAM_GROUP.getCode().equals(goodsTypeCode)){
			if(StringUtils.isEmpty(teamGroupNum) || "0".equals(teamGroupNum)){
				result.setResult("1");
				result.setMsg("请设置拼团商品人数");
				return result;
			}
		
			if(StringUtils.isEmpty(teamGroupTime) || "0".equals(teamGroupTime)){
				result.setResult("1");
				result.setMsg("请设置拼团商品有效时间");
				return result;
			}
		}
		
		strMap.put("goodsName", goodsName);
		strMap.put("goodsCategoryId", goodsCategoryId);
		strMap.put("goodsPrice", goodsPrice);
		strMap.put("goodsSoldNumber", goodsSoldNumber);
		strMap.put("operationtype", operationType);
		strMap.put("goodsId", goodsId);
		strMap.put("goodsTypeCode", goodsTypeCode);
		strMap.put("teamGroupNum", teamGroupNum);
		strMap.put("teamGroupTime", teamGroupTime);
		
		picMap.put("goodsPic", goodsPic);
		picMap.put("goodsDetailsShowPic", goodsDetailsShowPic);
		picMap.put("goodsDetailsPic1", goodsDetailsPic1);
		picMap.put("goodsDetailsPic2", goodsDetailsPic2);
		picMap.put("goodsDetailsPic3", goodsDetailsPic3);
		picMap.put("goodsDetailsPic4", goodsDetailsPic4);
		picMap.put("goodsDetailsPic5", goodsDetailsPic5);
		picMap.put("goodsDetailsPic6", goodsDetailsPic6);
		picMap.put("goodsDetailsPic7", goodsDetailsPic7);
		picMap.put("goodsDetailsPic8", goodsDetailsPic8);
		picMap.put("goodsDetailsPic9", goodsDetailsPic9);
		picMap.put("goodsDetailsPic10", goodsDetailsPic10);
		
		
		if(StringUtils.isEmpty(goodsPrice)){
			result.setResult("1");
			result.setMsg("商品价格不能为空");
			return result;
		}
		
		if(StringUtils.isEmpty(goodsName)){
			result.setResult("1");
			result.setMsg("商品名称不能为空");
			return result;
		}
		
		result = this.judgePicSize(picMap);
		if("1".equals(result.getResult())){
			return result;
		}
		
		return goodsService.addGoods(strMap, picMap);
	
	}
	
	@RequestMapping(value = "/goodsDetail")
	public String goodsDetail(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String goodsId = request.getParameter("goodsId");
		Goods goods = goodsService.getDetailById(Long.parseLong(goodsId));
		List<GoodsCategory> goodsCategoryList = goodsCategoryService
				.findAllGoodsCategory();
		
		List<GoodsTypeEnum> goodsTypeList = new ArrayList<GoodsTypeEnum>();
		
		goodsTypeList.add(GoodsTypeEnum.GENERAL);
		goodsTypeList.add(GoodsTypeEnum.TEAM_GROUP);
		
		model.addAttribute("goodsTypeList", goodsTypeList);
		model.addAttribute("operationType", "update");
		model.addAttribute("goodsVO", goods);
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		return "/back/goods/updateitems";
	}
	

	/**
	 * 
	 * 根据Id删除商品
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteGoodsById", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteGoodsById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Result result = new Result();
		String goodsId = request.getParameter("goodsId");
		Goods goods = goodsService.getGoodsById(Long.parseLong(goodsId));
		goods.setIsDelete(true);
		goodsService.updateGoodsByGoods(goods);
		result.setMsg("操作成功");
		return result;
	}
	
	
	/**
	 * 判断上传商品图片大小是否合法
	 * @version 
	 * @author hyj
	 * @date:2016年11月30日
	 * @param picMap
	 * @return
	 * Result
	 */
	private Result judgePicSize(Map<String,MultipartFile> picMap){
		Result result = new Result();

		if (!checkPicSize(picMap.get("goodsPic").getSize())) {
			result.setResult("1");
			result.setMsg("列表展示不能超过1024k");
			return result;
		}
		
		if (!checkPicSize(picMap.get("goodsDetailsShowPic").getSize())) {
			result.setResult("1");
			result.setMsg("详情展示图不能超过1024k");
			return result;
		}
		
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic1").getSize())) {
			result.setResult("1");
			result.setMsg("详情1图片不能超过2048k");
			return result;
		}
		
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic2").getSize())) {
			result.setResult("1");
			result.setMsg("详情2图片不能超过2048k");
			return result;
		}
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic3").getSize())) {
			result.setResult("1");
			result.setMsg("详情3图片不能超过2048k");
			return result;
		}
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic4").getSize())) {
			result.setResult("1");
			result.setMsg("详情4图片不能超过2048k");
			return result;
		}
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic5").getSize())) {
			result.setResult("1");
			result.setMsg("详情5图片不能超过2048k");
			return result;
		}
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic6").getSize())) {
			result.setResult("1");
			result.setMsg("详情6图片不能超过2048k");
			return result;
		}
		
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic7").getSize())) {
			result.setResult("1");
			result.setMsg("详情7图片不能超过2048k");
			return result;
		}
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic8").getSize())) {
			result.setResult("1");
			result.setMsg("详情8图片不能超过2048k");
			return result;
		}
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic9").getSize())) {
			result.setResult("1");
			result.setMsg("详情9图片不能超过2048k");
			return result;
		}
		if (!checkDetailPicSize(picMap.get("goodsDetailsPic10").getSize())) {
			result.setResult("1");
			result.setMsg("详情10图片不能超过2048k");
			return result;
		}
		
		return result;
	}
	
	
	/**
	 * 判断图片大小是否符合
	 * @version 
	 * @author hyj
	 * @date:2016年11月30日
	 * @param imgSize
	 * @return
	 * boolean
	 */
	public static boolean checkPicSize(long imgSize) {
		if (imgSize > 100*1024) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断详情图片大小是否符合
	 * @version 
	 * @author hyj
	 * @date:2016年11月30日
	 * @param imgSize
	 * @return
	 * boolean
	 */
	public static boolean checkDetailPicSize(long imgSize) {
		if (imgSize > 2*1024*1024) {
			return false;
		}
		return true;
	}
}
