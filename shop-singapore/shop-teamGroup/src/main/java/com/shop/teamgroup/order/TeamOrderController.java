package com.shop.teamgroup.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.ConstantBasic;
import com.shop.common.Result;
import com.shop.common.Utils;
import com.shop.common.dateutil.DateUtil;
import com.shop.common.enums.GoodsTypeEnum;
import com.shop.common.enums.TeamOrderStatusEnum;
import com.shop.common.exception.Message;
import com.shop.common.exception.ServiceException;
import com.shop.common.imgutil.ImgPathUtil;
import com.shop.dao.ext.TeamGroupMapperExt;
import com.shop.entity.basic.Goods;
import com.shop.entity.basic.TeamMemberRecord;
import com.shop.entity.basic.TeamOrderRecord;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserReceiptAddress;
import com.shop.service.address.RecAddressService;
import com.shop.service.goods.GoodsService;
import com.shop.service.teamgroup.TeamMemberRecordService;
import com.shop.service.teamgroup.TeamOrderRecordService;
import com.shop.teamgroup.common.UserController;;

@Controller
@RequestMapping(value = "/team_group/order")
public class TeamOrderController extends UserController {

	@Autowired
	private RecAddressService recAddressService;

	@Autowired
	private TeamOrderRecordService teamOrderRecordService;

	@Autowired
	private TeamMemberRecordService teamMemberRecordService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private TeamGroupMapperExt teamGroupMapperExt;

	/**
	 * 团长直接开团
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/confirm_open_team", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmOpenTeam(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);
		String goodsId = request.getParameter("goodsId");

		if (StringUtils.isEmpty(goodsId)) {
			throw new ServiceException(Result.ERROR_CODE, "请选择开团商品");
		}

		UserReceiptAddress defaultAddr = recAddressService.queryDefaultAddress(user.getId());
		if (defaultAddr == null) {
			throw new ServiceException(Message.user_address_not_exist);
		}

		Goods goods = goodsService.getGoodsById(Long.valueOf(goodsId));

		if (goods.getIsDelete()) {
			throw new ServiceException(Result.ERROR_CODE, "商品已失效");
		}

		if (!GoodsTypeEnum.TEAM_GROUP.getCode().equals(goods.getGoodsType())) {
			throw new ServiceException(Result.ERROR_CODE, "非拼团商品,不能开团");
		}

		// 开团
		confirmOpenTeam(user, defaultAddr, goods, model);

		return super.result(model);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void confirmOpenTeam(User user, UserReceiptAddress defaultAddr, Goods goods, Model model) {

		TeamOrderRecord teamOrderRecord = new TeamOrderRecord();
		teamOrderRecord.setTeamGroupSn(DateUtil.getTimeMilliseconds());
		teamOrderRecord.setGoodsId(goods.getId());
		teamOrderRecord.setGoodsName(goods.getGoodsName());
		teamOrderRecord.setGoodsPic(goods.getGoodsPic());
		teamOrderRecord.setGoodsPrice(goods.getGoodsPrice());
		teamOrderRecord.setTeamLeaderId(user.getId());
		teamOrderRecord.setTotalPersonNum(goods.getGoodsGroupNumLimit());
		teamOrderRecord.setBuyPersonNum(1);
		teamOrderRecord.setIsFinished(0);
		teamOrderRecord.setCreateTime(DateUtil.getNow());

		// 结束时间为当前时间 + 限制拼团时间小时数
		teamOrderRecord.setEndTime(DateUtil.getPreDateSecond(DateUtil.getNow(), goods.getGoodsGroupTime() * 60));

		teamOrderRecordService.addTeamOrderRecord(teamOrderRecord);

		TeamMemberRecord teamMemberRecord = new TeamMemberRecord();
		teamMemberRecord.setOrderSn(Utils.getNextOrderSn(ConstantBasic.OrderTypeCode.SINGAPORE_TEAM_ORDER_CODE));
		teamMemberRecord.setReceiveAddress(JSONObject.toJSONString(defaultAddr));
		teamMemberRecord.setTeamId(teamOrderRecord.getId());
		teamMemberRecord.setTeanmOrderStatus(1);
		teamMemberRecord.setUserId(user.getId());
		teamMemberRecord.setCreateTime(DateUtil.getNow());

		teamMemberRecordService.addTeamMemberRecord(teamMemberRecord);
	}

	@RequestMapping(value = "/join_open_team", method = RequestMethod.POST)
	@ResponseBody
	public Result joinOpenTeam(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);
		String teamId = request.getParameter("team_id");

		if (StringUtils.isEmpty(teamId)) {
			throw new ServiceException(Result.ERROR_CODE, "请选择参团组");
		}

		UserReceiptAddress defaultAddr = recAddressService.queryDefaultAddress(user.getId());
		if (defaultAddr == null) {
			throw new ServiceException(Message.user_address_not_exist);
		}

		// 参团
		TeamOrderRecord teamOrderRecord = checkOpenTeamParam(Long.valueOf(teamId), user.getId());

		joinOpenTeam(teamOrderRecord, defaultAddr, user);

		return super.result(model);
	}

	/**
	 * 同步控制参团校验,防止同一个团多人参入
	 * 
	 * @param teamId
	 * @return
	 */
	public synchronized TeamOrderRecord checkOpenTeamParam(Long teamId, Long userId) {

		TeamOrderRecord teamOrderRecord = teamOrderRecordService.queryTeamOrderRecordById(teamId);

		if (teamOrderRecord == null) {
			throw new ServiceException(Result.ERROR_CODE, "请选择参团组");
		}

		if (teamOrderRecord.getIsFinished() == 1) {
			throw new ServiceException(Result.ERROR_CODE, "拼团已成功,不能参团");
		}

		Goods goods = goodsService.getGoodsById(teamOrderRecord.getGoodsId());

		if (goods.getIsDelete()) {
			throw new ServiceException(Result.ERROR_CODE, "商品已失效");
		}

		if (!GoodsTypeEnum.TEAM_GROUP.getCode().equals(goods.getGoodsType())) {
			throw new ServiceException(Result.ERROR_CODE, "非拼团商品,不能参团");
		}

		// 拼团已成功,提示无法参与
		if (teamOrderRecord.getBuyPersonNum().intValue() >= teamOrderRecord.getTotalPersonNum().intValue()) {
			throw new ServiceException(Result.ERROR_CODE, "拼团已成功,不能参团");
		}

		List<TeamMemberRecord> teamMemberRecordList = teamMemberRecordService
				.queryTeamMemberRecordByTeamId(teamOrderRecord.getId());

		for (TeamMemberRecord teamMemberRecord : teamMemberRecordList) {
			if (userId.longValue() == teamMemberRecord.getUserId().longValue()) {
				throw new ServiceException(Result.ERROR_CODE, "您已参团,不能重复参团");
			}
		}

		return teamOrderRecord;
	}

	/**
	 * 用户参团数据处理
	 * 
	 * @param teamOrderRecord
	 * @param defaultAddr
	 * @param user
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void joinOpenTeam(TeamOrderRecord teamOrderRecord, UserReceiptAddress defaultAddr, User user) {

		TeamMemberRecord addTeamMemberRecord = new TeamMemberRecord();
		addTeamMemberRecord.setOrderSn(Utils.getNextOrderSn(ConstantBasic.OrderTypeCode.SINGAPORE_TEAM_ORDER_CODE));
		addTeamMemberRecord.setReceiveAddress(JSONObject.toJSONString(defaultAddr));
		addTeamMemberRecord.setTeamId(teamOrderRecord.getId());
		addTeamMemberRecord.setTeanmOrderStatus(1);
		addTeamMemberRecord.setUserId(user.getId());
		addTeamMemberRecord.setCreateTime(DateUtil.getNow());

		teamMemberRecordService.addTeamMemberRecord(addTeamMemberRecord);

		teamOrderRecord.setBuyPersonNum(teamOrderRecord.getBuyPersonNum() + 1);

		// 拼团成功
		if (teamOrderRecord.getBuyPersonNum() == teamOrderRecord.getTotalPersonNum()) {

			teamOrderRecord.setIsFinished(1);
			teamOrderRecord.setFinishedTime(DateUtil.getNow());

			List<TeamMemberRecord> teamMemberRecordList = teamMemberRecordService
					.queryTeamMemberRecordByTeamId(teamOrderRecord.getId());

			for (TeamMemberRecord teamMemberRecord : teamMemberRecordList) {
				teamMemberRecord.setTeanmOrderStatus(2);
				teamMemberRecordService.updateTeamMemberRecord(teamMemberRecord);
			}
		}

		teamOrderRecordService.updateTeamOrderRecord(teamOrderRecord);
	}

	/**
	 * 查询我的拼团订单
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月14日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/query_my_open_team_list")
	@ResponseBody
	public Result queryMyOpenTeamList(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		String teamOrderStatus = request.getParameter("team_order_status");

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userId", user.getId());

		if (StringUtils.isNotEmpty(teamOrderStatus)) {
			queryMap.put("status", teamOrderStatus);
		}

		List<Map<String, Object>> teamGroupList = teamGroupMapperExt.selectTeamGroupList(queryMap);

		for (Map<String, Object> map : teamGroupList) {

			Long goodsId = (Long) map.get("goodsId");
			String goodsPic = (String) map.get("goodsPic");

			if (StringUtils.isNotEmpty(goodsPic)) {
				map.put("goodsPicPath", ImgPathUtil.getGoodImgPath(goodsId.toString(), goodsPic, false));
			}

			Integer teamStatus = (Integer) map.get("teamStatus");

			map.put("teamStatusStr", TeamOrderStatusEnum.getGoodsTypeName(teamStatus));
		}

		model.addAttribute("team_group_list", teamGroupList);
		return super.result(model);
	}

	/**
	 * 拼团订单确认收货
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月15日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/confirm_my_team_order", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmMyTeamOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		String teamMemberId = request.getParameter("team_member_id");

		TeamMemberRecord teamMemberRecord = teamMemberRecordService
				.queryTeamMemberRecordById(Long.valueOf(teamMemberId));

		if (TeamOrderStatusEnum.DELIVERY_GOODS.getCode().intValue() != teamMemberRecord.getTeanmOrderStatus()
				.intValue()) {
			throw new ServiceException(Result.ERROR_CODE, "拼团订单状态未进入送货中,不能确认收货");
		}

		if (user.getId().longValue() != teamMemberRecord.getUserId().longValue()) {
			throw new ServiceException(Result.ERROR_CODE, "用户操作权限不足");
		}

		teamMemberRecord.setTeanmOrderStatus(TeamOrderStatusEnum.CONFIRM_RECEIPT.getCode());

		teamMemberRecordService.updateTeamMemberRecord(teamMemberRecord);
		
		return super.result(model);
	}
}
