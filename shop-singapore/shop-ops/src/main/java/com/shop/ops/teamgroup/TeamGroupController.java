package com.shop.ops.teamgroup;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.common.dateutil.DateUtil;
import com.shop.common.excel.ExcelUtil;
import com.shop.entity.excel.TeamGroupExportExcel;
import com.shop.ops.user.UserController;
import com.shop.service.teamgroup.TeamGroupService;

/**
 * 拼团
 * @author ChinaHp
 *
 */
@Controller
@RequestMapping(value="teamGroup")
public class TeamGroupController extends UserController{

	@Autowired
	TeamGroupService teamGroupService;
	
	/**
	 * 拼团页面跳转
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toTeamGroup")
	public String toTeamGroup(HttpServletRequest request, HttpServletResponse response, Model model){
		return "/back/teamGroup/teamGroupList";
	}
	
	/**
	 * 分页查询拼团记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="queryTeamGroupByPage", method = RequestMethod.GET)
	@ResponseBody
	public Result queryTeamGroupInfo(HttpServletRequest request, HttpServletResponse response, Model model){
		
		Result result = new Result();
		
		String memberName = request.getParameter("memberName");
		String teamGroupSn = request.getParameter("teamGroupSn");
		String goodsName = request.getParameter("goodsName");
		String status = request.getParameter("teamStatus");
		String pageNum = request.getParameter("pageNum");
				
		
		String createStartDate = request.getParameter("createStartDate");
		String createEndDate = request.getParameter("createEndDate");
		String successStartDate = request.getParameter("successStartDate");
		String successEndDate = request.getParameter("successEndDate");
		createStartDate = DateUtil.getStartTimeForDay(createStartDate, 0);
		createEndDate = DateUtil.getEndDate(createEndDate);
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("memberName", memberName);
		queryMap.put("teamGroupSn", teamGroupSn);
		queryMap.put("goodsName", goodsName);
		queryMap.put("status", status);
		queryMap.put("pageNum", pageNum);
		queryMap.put("createStartDate", DateUtil.getFormatDate(createStartDate, DateUtil.DateTime_Format));
		queryMap.put("createEndDate", DateUtil.getFormatDate(createEndDate, DateUtil.DateTime_Format));
		
		if(StringUtils.isNotEmpty(successStartDate)){
			successStartDate = DateUtil.getStartTimeForDay(successStartDate, 0);
			queryMap.put("successStartDate", DateUtil.getFormatDate(successStartDate, DateUtil.DateTime_Format));
		}
		if(StringUtils.isNotEmpty(successEndDate)){
			successEndDate = DateUtil.getEndDate(successEndDate);
			queryMap.put("successEndDate", DateUtil.getFormatDate(successEndDate, DateUtil.DateTime_Format));
		}
		Map<String,Object> resultMap = teamGroupService.queryTeamGroupListByPage(queryMap);
		model.addAttribute("pageInfo", resultMap.get("pageInfo"));
		model.addAttribute("teamGroupList", resultMap.get("teamGroupList"));
		result.setData(model);
		return result;
	}
	
	/**
	 * 拼团记录操作实现
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="updateTeamGroupByStatus", method = RequestMethod.GET)
	@ResponseBody
	public Result cancelTeamGroupByCondition(HttpServletRequest request, HttpServletResponse response, Model model){
		Result result = new Result();
		String teamId = request.getParameter("teamId");
		String userId = request.getParameter("userId");
		String teamStatus = request.getParameter("teamStatus");
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(teamId) || StringUtils.isEmpty(teamStatus)){
			result.setResult("1");
			result.setMsg("参数错误");
			return result;
		}
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("teamId", Long.parseLong(teamId));
		queryMap.put("userId", Long.parseLong(userId));
		queryMap.put("teamStatus", teamStatus);
		teamGroupService.updateTeamGroupByStatus(queryMap);
		result.setMsg("操作成功");
		return result;
	}
	
	
	/**
	 * 拼团记录导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="exportTeamGroupExcel", method = RequestMethod.GET)
	public void exportTeamGroupExcel(HttpServletRequest request, HttpServletResponse response){
		
		String memberName = request.getParameter("memberName");
		String teamGroupSn = request.getParameter("teamGroupSn");
		String goodsName = request.getParameter("goodsName");
		String status = request.getParameter("teamStatus");
		
		String createStartDate = request.getParameter("createStartDate");
		String createEndDate = request.getParameter("createEndDate");

		String successStartDate = request.getParameter("successStartDate");
		String successEndDate = request.getParameter("successEndDate");
		createStartDate = DateUtil.getStartTimeForDay(createStartDate, 0);
		createEndDate = DateUtil.getEndDate(createEndDate);
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("memberName", memberName);
		queryMap.put("teamGroupSn", teamGroupSn);
		queryMap.put("goodsName", goodsName);
		queryMap.put("status", status);
		
		queryMap.put("createStartDate", DateUtil.getFormatDate(createStartDate, DateUtil.DateTime_Format));
		queryMap.put("createEndDate", DateUtil.getFormatDate(createEndDate, DateUtil.DateTime_Format));
		
		if(StringUtils.isNotEmpty(successStartDate)){
			successStartDate = DateUtil.getStartTimeForDay(successStartDate, 0);
			queryMap.put("successStartDate", DateUtil.getFormatDate(successStartDate, DateUtil.DateTime_Format));
		}
		if(StringUtils.isNotEmpty(successEndDate)){
			successEndDate = DateUtil.getEndDate(successEndDate);
			queryMap.put("successEndDate", DateUtil.getFormatDate(successEndDate, DateUtil.DateTime_Format));
		}
		List<TeamGroupExportExcel> excelList = teamGroupService.exportExcel(queryMap);
		String fileName = "拼团订单.xls";
		String[] title = { "", "用户名称", "拼团编号", "商品名称", "商品价格", "拼团状态", "拼团时间", "成团时间", "收货地址"};
		ExcelUtil.exportExcel(fileName, title, excelList, request, response);
	}
	
	
}
