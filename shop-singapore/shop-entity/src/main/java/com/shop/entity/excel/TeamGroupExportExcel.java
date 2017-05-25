package com.shop.entity.excel;

import java.util.Date;

public class TeamGroupExportExcel {
	
	
	private String loginName;
	
	private String teamGroupSn;
	
	private String goodName;
	
	private Double goodsPrice;
	
	private String teamStatus;
	
	private Date createTime;
	
	private Date finishedTime;
	
	private String receiveAddress;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTeamGroupSn() {
		return teamGroupSn;
	}

	public void setTeamGroupSn(String teamGroupSn) {
		this.teamGroupSn = teamGroupSn;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(String teamStatus) {
		this.teamStatus = teamStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	
}
