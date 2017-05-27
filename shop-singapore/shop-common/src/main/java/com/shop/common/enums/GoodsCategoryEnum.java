package com.shop.common.enums;

public enum GoodsCategoryEnum {
	
	
	VEGETABLES(1,"蔬菜"),
	
	MEAT(2,"肉");
	
	private int code;
	
	private String name;

	private GoodsCategoryEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
