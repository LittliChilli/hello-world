package com.shop.teamgroup.goods;

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
import com.shop.entity.basic.TeamOrderRecord;
import com.shop.entity.basic.User;
import com.shop.service.category.GoodsCategoryService;
import com.shop.service.goods.GoodsService;
import com.shop.service.teamgroup.TeamOrderRecordService;
import com.shop.teamgroup.common.UserController;

/**
 * 拼团商品展示
 * 
 * @version
 * @author zousheng
 * @date:2016年12月13日
 */
@Controller
@RequestMapping(value = "/team_group/goods")
public class TeamGoodsController extends UserController {

	@Autowired
	GoodsService goodsService;

	@Autowired
	GoodsCategoryService goodsCategoryService;

	@Autowired
	private TeamOrderRecordService teamOrderRecordService;

	/**
	 * 查询所有商品类别
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_all_goods_category")
	@ResponseBody
	public Result queryAllGoodsCategory(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<GoodsCategory> categoryList = goodsCategoryService.findAllGoodsCategory();
		model.addAttribute("goodsCategoryList", categoryList);
		return super.result(model);
	}

	/**
	 * 查询所有拼团商品
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_all_goods")
	@ResponseBody
	public Result queryAllGoods(HttpServletRequest request, HttpServletResponse response, Model model) {

		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("is_delete", "false");
		queryMap.put("goodsType", GoodsTypeEnum.TEAM_GROUP.getCode());
		model.addAttribute("goodsList", goodsService.queryGoodsByCondition(queryMap));
		return super.result(model);
	}

	/**
	 * 查询商品详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_detail_by_id")
	@ResponseBody
	public Result queryGoodsDetialById(HttpServletRequest request, HttpServletResponse response, Model model) {
		String goodsId = request.getParameter("goodsId");
		if (StringUtils.isEmpty(goodsId)) {
			throw new ServiceException(Result.ERROR_CODE, "拼团商品不存在");
		}
		Goods goods = goodsService.getDetailById(Long.parseLong(goodsId));
		model.addAttribute(Goods.class.getSimpleName(), goods);
		return super.result(model);
	}

	/**
	 * 查询拼团商品详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_team_detail_by_team_id")
	@ResponseBody
	public Result queryTeamDetailByTeamId(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession(request);

		String teamId = request.getParameter("team_id");

		if (StringUtils.isEmpty(teamId)) {
			throw new ServiceException(Result.ERROR_CODE, "拼团信息不存在");
		}

		TeamOrderRecord teamOrderRecord = teamOrderRecordService.queryTeamOrderRecordById(Long.valueOf(teamId));

		if (teamOrderRecord == null) {
			throw new ServiceException(Result.ERROR_CODE, "拼团信息不存在");
		}

		model.addAttribute(TeamOrderRecord.class.getSimpleName(), teamOrderRecord);

		Goods goods = goodsService.getDetailById(teamOrderRecord.getGoodsId());
		model.addAttribute(Goods.class.getSimpleName(), goods);

		// 判断是否是团主
		boolean isLeader = false;
		if (user != null && user.getId().longValue() == teamOrderRecord.getTeamLeaderId().longValue()) {
			isLeader = true;
		}

		model.addAttribute("isLeader", isLeader);

		return super.result(model);
	}

}
