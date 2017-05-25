/**
 * ImgSaveUtil.java
 */
package com.shop.common.imgutil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.shop.common.encode.EncodeUtils;
import com.shop.common.wechat.WechatUtil;

/**
 * 图片保存处理工具类
 * 
 * @version
 * @author zousheng
 * @date:2016年11月25日
 */
public class ImgSaveUtil {

	/**
	 * base64字符串转化成图片, 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 * 
	 * @param imgStr
	 *            // base64流串
	 * 
	 * @return
	 * @throws IOException
	 */
	public static InputStream GenerateImage(String imgStr) throws IOException {

		byte[] inb = EncodeUtils.base64Decode(imgStr);

		InputStream inputStream = new ByteArrayInputStream(inb);

		inputStream.close();
		return inputStream;
	}

	/**
	 * 存储base64模式的img图片
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 * 
	 * @param imgBase64
	 *            // base64流
	 * 
	 * @param imgFileName
	 *            // 文件全路径包含文件名称
	 * 
	 * @return
	 */
	public static Boolean saveBase64Img(String imgBase64, String imgFileName) {

		// 图像数据为空
		if (StringUtils.isNotEmpty(imgBase64)) {

			try {

				InputStream inputStream = GenerateImage(imgBase64);
				FileUtils.copyInputStreamToFile(inputStream, new File(imgFileName));

				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 存储wehcat——mediaId模式的img图片
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 * 
	 * @param mediaId
	 *            // 微信mediaId
	 * 
	 * @param imgFileName
	 *            // 文件全路径包含文件名称
	 * 
	 * @return
	 */
	public static Boolean saveWechatImg(String accessToken, String mediaId, String imgFileName) {

		if (StringUtils.isNotEmpty(mediaId)) {

			try {

				InputStream inputStream = WechatUtil.getWeiXinMediaStream(accessToken, mediaId);
				FileUtils.copyInputStreamToFile(inputStream, new File(imgFileName));

				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 存储File模式的img图片
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 * 
	 * @param inputStream
	 *            // MultipartFile.getInputStream()
	 * 
	 * @param imgFileName
	 *            // 文件全路径包含文件名称
	 * 
	 * @return
	 */
	public static Boolean saveFileImg(InputStream inputStream, String imgFileName) {

		try {
			FileUtils.copyInputStreamToFile(inputStream, new File(imgFileName));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
