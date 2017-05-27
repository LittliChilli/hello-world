package com.shop.service.teamgroup;

import java.util.List;
import java.util.Map;

import com.shop.entity.excel.TeamGroupExportExcel;

public interface TeamGroupService {
	
	/**
	 * 拼团记录查询
	 * @param queryMap
	 * @return
	 */
	Map<String,Object> queryTeamGroupListByPage(Map<String,Object> queryMap);
	
	
	/**
	 * 更改拼团状态
	 * @param map
	 */
	void updateTeamGroupByStatus(Map<String,Object> map);
	
	
	/**
	 * 超过拼团有效时间未成团订单系统置为失败
	 */
	void changeTeamGroupStatusByTimetigger();
	
	/**
	 * 拼团记录导出
	 * @param map
	 * @return
	 */
	List<TeamGroupExportExcel> exportExcel(Map<String,Object> map);

}
