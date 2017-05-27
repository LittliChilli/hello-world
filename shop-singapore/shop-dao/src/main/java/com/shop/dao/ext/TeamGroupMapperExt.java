package com.shop.dao.ext;

import java.util.List;
import java.util.Map;

import com.shop.entity.excel.TeamGroupExportExcel;

public interface TeamGroupMapperExt {
	
	/**
	 * 查询拼团信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectTeamGroupList(Map<String,Object> map);
	
	
	/**
	 * 导出拼团订单记录
	 * @param map
	 * @return
	 */
	List<TeamGroupExportExcel> exportExcelList(Map<String,Object> map);

}
