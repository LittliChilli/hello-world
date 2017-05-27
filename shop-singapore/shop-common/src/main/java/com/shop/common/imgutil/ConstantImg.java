package com.shop.common.imgutil;

import com.shop.common.readconfig.Property;

/**
 * 定义文件存储的路径
 * 
 * @author mmfq2
 * 
 */
public interface ConstantImg {

	/**
	 * 默认图片
	 */
	String PCI_URL_FOR_EMPTY = "http://www.mmfenqi.com/static/masserts/miao/img/choose3.png";

	/**
	 * 图片展示路径前缀
	 */
	String SYS_GET_IMG_PREFIX = Property.getProperty("SYS_GET_IMG_PREFIX");

	/**
	 * 图片存储路径前缀
	 */
	String SYS_SAVE_IMG_PREFIX = Property.getProperty("SYS_SAVE_IMG_PREFIX");

	/**
	 * 服务器存储图片路径
	 */
	String IMG_GETPATH = "/static/shop_file/img";

	/**
	 * 商品图片路径
	 */
	String GOOD_IMG_PATH = IMG_GETPATH + "/goods";
}
