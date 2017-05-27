package com.shop.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品类型定义
 * 
 * @author zousheng
 *
 */
public enum GoodsTypeEnum {

	GENERAL("1", "普通商品"),

	TEAM_GROUP("5", "拼团商品");

	private String message;

	private String code;

	private GoodsTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static String getGoodsTypeName(Integer code) {

		for (GoodsTypeEnum goodsTypeEnum : GoodsTypeEnum.values()) {
			if (goodsTypeEnum.getCode().equals(code)) {
				return goodsTypeEnum.getMessage();
			}
		}

		return null;
	}

	public static List<GoodsTypeEnum> getAllGoodsTyep() {

		List<GoodsTypeEnum> goodsTypeEnumList = new ArrayList<GoodsTypeEnum>();
		for (GoodsTypeEnum goodsTypeEnum : GoodsTypeEnum.values()) {
			goodsTypeEnumList.add(goodsTypeEnum);
		}

		return goodsTypeEnumList;
	}

}
