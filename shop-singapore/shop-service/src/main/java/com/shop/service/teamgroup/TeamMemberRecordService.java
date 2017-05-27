/** 
 * @fileName sss.java
 * 
 * @version 
 * @author zousheng 
 * @date:2016年12月13日
 * Copyright © 2015.美眉分期 All rights reserved.
 */
package com.shop.service.teamgroup;

import java.util.List;

import com.shop.entity.basic.TeamMemberRecord;

/**
 * @version
 * @author zousheng
 * @date:2016年12月13日
 */
public interface TeamMemberRecordService {

	/**
	 * 添加用户参团记录
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param teamOrderRecord
	 */
	void addTeamMemberRecord(TeamMemberRecord teamMemberRecord);

	/**
	 * 根据拼团ID查询拼团列表信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param teamId
	 * @return
	 */
	List<TeamMemberRecord> queryTeamMemberRecordByTeamId(Long teamId);

	/**
	 * 根据参团ID查询参团信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param teamId
	 * @return
	 */
	TeamMemberRecord queryTeamMemberRecordById(Long id);
	
	/**
	 * 更新参团信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param teamId
	 * @return
	 */
	void updateTeamMemberRecord(TeamMemberRecord teamMemberRecord);
}
