package com.shop.service.timetigger.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.shop.service.teamgroup.TeamGroupService;
import com.shop.service.timetigger.ChangeTeamGroupOverTimeTigger;

public class ChangeTeamGroupOverTimeTiggerImpl implements ChangeTeamGroupOverTimeTigger{

	@Autowired
	TeamGroupService teamGroupService;
	
	@Override
	public void changeTeamGroupStatus() {
		teamGroupService.changeTeamGroupStatusByTimetigger();
	}

}
