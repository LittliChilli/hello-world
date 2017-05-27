/** 
 * @fileName sss.java
 * 
 * @version 
 * @author zousheng 
 * @date:2016年12月13日
 * Copyright © 2015.美眉分期 All rights reserved.
 */
package com.shop.service.teamgroup;

import com.shop.entity.basic.TeamOrderRecord;

/**
 * @version
 * @author zousheng
 * @date:2016年12月13日
 */
public interface TeamOrderRecordService {

	/**
	 * 用户开团
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param teamOrderRecord
	 */
	void addTeamOrderRecord(TeamOrderRecord teamOrderRecord);
	
	/**
	 * 根据拼团ID查询拼团信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param teamId
	 * @return
	 */
	TeamOrderRecord queryTeamOrderRecordById(Long id);
	
	/**
	 * 更新拼团信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月13日
	 *
	 * @param teamId
	 * @return
	 */
	void updateTeamOrderRecord(TeamOrderRecord teamOrderRecord);
}
