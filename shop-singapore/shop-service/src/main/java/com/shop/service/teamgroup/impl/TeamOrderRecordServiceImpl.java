package com.shop.service.teamgroup.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.Result;
import com.shop.common.exception.ServiceException;
import com.shop.dao.basic.TeamOrderRecordMapper;
import com.shop.entity.basic.TeamOrderRecord;
import com.shop.service.teamgroup.TeamOrderRecordService;

@Service
public class TeamOrderRecordServiceImpl implements TeamOrderRecordService {

	@Autowired
	private TeamOrderRecordMapper teamOrderRecordMapper;

	@Override
	public void addTeamOrderRecord(TeamOrderRecord teamOrderRecord) {

		Integer count = teamOrderRecordMapper.insertSelective(teamOrderRecord);

		if (count <= 0) {
			throw new ServiceException(Result.ERROR_CODE, "开团失败");
		}
	}

	@Override
	public TeamOrderRecord queryTeamOrderRecordById(Long id) {
		return teamOrderRecordMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public void updateTeamOrderRecord(TeamOrderRecord teamOrderRecord) {

		Integer count = teamOrderRecordMapper.updateByPrimaryKeySelective(teamOrderRecord);

		if (count <= 0) {
			throw new ServiceException(Result.ERROR_CODE, "开团失败");
		}
	}
}
