/** 
 * @fileName ImgPathUtil.java
 * 
 * @version 
 * @author zousheng 
 * @date:2016年11月25日
 * Copyright © 2016.美眉分期 All rights reserved.
 */
package com.shop.common.imgutil;

import com.shop.common.ConstantBasic;
import com.shop.common.encode.EncodeUtils;

/**
 * img图片存储路径
 * 
 * @version
 * @author zousheng
 * @date:2016年1月28日
 */
public class ImgPathUtil {

	/**
	 * 保存服务器图片路径
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param picPath
	 * @return
	 */
	public static String saveImgPath(String picPath) {

		return ConstantImg.SYS_SAVE_IMG_PREFIX + picPath;
	}

	/**
	 * 展示服务器图片路径
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param picPath
	 * @return
	 */
	public static String getImgPath(String picPath) {

		return ConstantImg.SYS_GET_IMG_PREFIX + picPath;
	}

	/**
	 * 获取服务器图片或文件
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param filePath
	 * @param imgName
	 * @return
	 */
	public static String getServiceFile(String filePath, String imgName, Boolean isSaveFlag) {

		if (isSaveFlag) {
			return saveImgPath(filePath) + ConstantBasic.Punctuation.SLASH + imgName;
		} else {
			return getImgPath(filePath) + ConstantBasic.Punctuation.SLASH + EncodeUtils.urlEncode(imgName);
		}
	}

	/**
	 * 商品图片定义路径
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param goodsId
	 * @param imgName
	 * @return
	 */
	public static String getGoodImgPath(String goodsId, String imgName, Boolean isSaveFlag) {

		String picPath = ConstantImg.GOOD_IMG_PATH + ConstantBasic.Punctuation.SLASH + goodsId;

		return getServiceFile(picPath, imgName, isSaveFlag);
	}

}
