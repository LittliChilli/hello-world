package com.shop.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼团状态定义
 * 
 * @version
 * @author zousheng
 * @date:2016年12月14日
 */
public enum TeamOrderStatusEnum {

	WAIT_PAYMENT(0, "待支付"), FIGHT_GROUP_PENDING(1, "拼团中"), Fight_group_success(2, "已成团(采购中)"), Fight_group_failed(3,
			"拼团失败"), Fight_group_cancle(4, "拼团取消"), DELIVERY_GOODS(5, "送货中"), CONFIRM_RECEIPT(6, "已收货");

	private String message;

	private Integer code;

	private TeamOrderStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static String getGoodsTypeName(Integer code) {

		for (TeamOrderStatusEnum goodsTypeEnum : TeamOrderStatusEnum.values()) {
			if (goodsTypeEnum.getCode().equals(code)) {
				return goodsTypeEnum.getMessage();
			}
		}

		return code.toString();
	}

	public static List<TeamOrderStatusEnum> getAllGoodsTyep() {

		List<TeamOrderStatusEnum> goodsTypeEnumList = new ArrayList<TeamOrderStatusEnum>();
		for (TeamOrderStatusEnum goodsTypeEnum : TeamOrderStatusEnum.values()) {
			goodsTypeEnumList.add(goodsTypeEnum);
		}

		return goodsTypeEnumList;
	}

}
