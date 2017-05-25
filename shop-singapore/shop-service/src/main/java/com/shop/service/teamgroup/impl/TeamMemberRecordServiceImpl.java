package com.shop.service.teamgroup.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.Result;
import com.shop.common.exception.ServiceException;
import com.shop.dao.basic.TeamMemberRecordMapper;
import com.shop.entity.basic.TeamMemberRecord;
import com.shop.entity.basic.TeamMemberRecordExample;
import com.shop.entity.basic.TeamMemberRecordExample.Criteria;
import com.shop.service.teamgroup.TeamMemberRecordService;

@Service
public class TeamMemberRecordServiceImpl implements TeamMemberRecordService {

	@Autowired
	private TeamMemberRecordMapper teamMemberRecordMapper;

	@Override
	public void addTeamMemberRecord(TeamMemberRecord teamMemberRecord) {

		Integer count = teamMemberRecordMapper.insertSelective(teamMemberRecord);

		if (count <= 0) {
			throw new ServiceException(Result.ERROR_CODE, "参团失败");
		}
	}
	
	@Override
	public void updateTeamMemberRecord(TeamMemberRecord teamMemberRecord) {

		Integer count = teamMemberRecordMapper.updateByPrimaryKeySelective(teamMemberRecord);

		if (count <= 0) {
			throw new ServiceException(Result.ERROR_CODE, "参团失败");
		}
	}

	@Override
	public List<TeamMemberRecord> queryTeamMemberRecordByTeamId(Long teamId) {

		TeamMemberRecordExample example = new TeamMemberRecordExample();
		Criteria criteria = example.createCriteria();
		criteria.andTeamIdEqualTo(teamId);

		return teamMemberRecordMapper.selectByExample(example);
	}

	@Override
	public TeamMemberRecord queryTeamMemberRecordById(Long id) {
		return teamMemberRecordMapper.selectByPrimaryKey(id);
	}

}
