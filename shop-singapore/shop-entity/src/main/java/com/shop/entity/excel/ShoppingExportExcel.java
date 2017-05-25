package com.shop.entity.excel;

public class ShoppingExportExcel {

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 商品数量
	 */
	private Integer totalNum;

	/**
	 * 商品总价
	 */
	private Double totalGoodsPrice;

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Double getTotalGoodsPrice() {
		return totalGoodsPrice;
	}

	public void setTotalGoodsPrice(Double totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}
	
}
