package com.shop.service.teamgroup.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.dao.basic.TeamMemberRecordMapper;
import com.shop.dao.basic.TeamOrderRecordMapper;
import com.shop.dao.ext.TeamGroupMapperExt;
import com.shop.entity.basic.TeamMemberRecord;
import com.shop.entity.basic.TeamMemberRecordExample;
import com.shop.entity.basic.TeamOrderRecord;
import com.shop.entity.basic.TeamOrderRecordExample;
import com.shop.entity.excel.TeamGroupExportExcel;
import com.shop.service.teamgroup.TeamGroupService;

@Service
public class TeamGroupServiceImpl implements TeamGroupService {

	@Autowired
	TeamGroupMapperExt teamGroupMapperExt;
	
	@Autowired
	TeamMemberRecordMapper teamMemberRecordMapper;
	
	@Autowired
	TeamOrderRecordMapper teamOrderRecordMapper;
	
	@Override
	public Map<String, Object> queryTeamGroupListByPage(Map<String, Object> queryMap) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String pageNum = (String)queryMap.get("pageNum");
		PageHelper.startPage(Integer.parseInt(pageNum), 10);
		List<Map<String,Object>> teamGroupList = teamGroupMapperExt.selectTeamGroupList(queryMap);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(teamGroupList);
		
		for(Map<String,Object> map : teamGroupList){
			@SuppressWarnings("unchecked")
			Map<String,Object> receiveMap = (Map<String,Object>)JSONObject.parse((String)map.get("receiveAddress"));
			map.put("receiveAddress", "<div><span>Addr:"+receiveMap.get("receiptAddress")+"</span></div>"+"<div><span>Name:"+receiveMap.get("receiver")+ "</span></div>"+"<div><span>Tel:"+receiveMap.get("telephone")+"</span></div>");
		}
		
		dataMap.put("pageInfo", pageInfo);
		dataMap.put("teamGroupList", teamGroupList);
		return dataMap;
	}

	@Override
	public void updateTeamGroupByStatus(Map<String, Object> map) {
		
		Long userId = (Long)map.get("userId");
		Long teamId = (Long)map.get("teamId");
		String teamStatus = (String)map.get("teamStatus");
		
		TeamMemberRecordExample example = new TeamMemberRecordExample();
		example.createCriteria().andUserIdEqualTo(userId)
		.andTeamIdEqualTo(teamId);
		List<TeamMemberRecord> recordList = teamMemberRecordMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(recordList)){
			TeamMemberRecord record = recordList.get(0);
			//取消订单
			if("1".equals(teamStatus)){
				record.setTeanmOrderStatus(4);
			}
			//正在配送
			if("2".equals(teamStatus)){
				record.setTeanmOrderStatus(5);
			}
			//确认收货
			if("5".equals(teamStatus)){
				record.setTeanmOrderStatus(6);
			}
			teamMemberRecordMapper.updateByPrimaryKeySelective(record);
		}
		
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void changeTeamGroupStatusByTimetigger() {
		TeamOrderRecordExample teamOrderRecordExample = new TeamOrderRecordExample();
		teamOrderRecordExample.createCriteria().andEndTimeLessThan(new Date())
		.andIsFinishedEqualTo(0);
		 List<TeamOrderRecord> recordList = teamOrderRecordMapper.selectByExample(teamOrderRecordExample);
		for(TeamOrderRecord teamOrderRecord : recordList){
			
			teamOrderRecord.setIsFinished(3);
			teamOrderRecordMapper.updateByPrimaryKeySelective(teamOrderRecord);
			
			TeamMemberRecordExample teamMemberRecordExample = new TeamMemberRecordExample();
			teamMemberRecordExample.createCriteria().andTeamIdEqualTo(teamOrderRecord.getId())
			.andTeanmOrderStatusEqualTo(1);
			List<TeamMemberRecord> teamMemberRecordList =  teamMemberRecordMapper.selectByExample(teamMemberRecordExample);
				for(TeamMemberRecord teamMemberRecord : teamMemberRecordList){
					teamMemberRecord.setTeanmOrderStatus(3);
					teamMemberRecordMapper.updateByPrimaryKeySelective(teamMemberRecord);
				}
		}
	}

	@Override
	public List<TeamGroupExportExcel> exportExcel(Map<String, Object> queryMap) {
		
		List<TeamGroupExportExcel> teamGroupList = teamGroupMapperExt.exportExcelList(queryMap);
		
		for(TeamGroupExportExcel teamGroupExportExcel : teamGroupList){
			@SuppressWarnings("unchecked")
			Map<String,Object> receiveMap = (Map<String,Object>)JSONObject.parse(teamGroupExportExcel.getReceiveAddress());
			teamGroupExportExcel.setReceiveAddress("Addr:"+receiveMap.get("receiptAddress")+"\n"+"Name:"+receiveMap.get("receiver")+"\n"+"Tel:"+receiveMap.get("telephone"));
		}
		return teamGroupList;
	}

}
