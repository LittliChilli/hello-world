package com.shop.common.wechat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WechatUtil {

	/**
	 * 获取临时素材MEDIA
	 */
	private static final String GET_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 判断是否是微信浏览器
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param userAgent
	 * @return
	 */
	public static boolean isWechat(String userAgent) {

		boolean isWeixin = false;

		String ua = userAgent.toLowerCase();

		// 是微信浏览器
		if (ua.indexOf("micromessenger") > 0) {
			isWeixin = true;
		}

		return isWeixin;
	}

	/**
	 * 获取微信临时素材
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param accessToken
	 * @param mediaId
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static InputStream getWeiXinMediaStream(String accessToken, String mediaId) throws IOException {

		// 获取media地址
		String mediaUrl = GET_MEDIA.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);

		return accessHttpsgly(mediaUrl);
	}

	/**
	 * 微信上传的临时素材转换为inputStream流
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static InputStream accessHttpsgly(String url) throws IOException {

		URL url1 = new URL(url);

		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();

		conn.setConnectTimeout(5000);
		conn.setReadTimeout(30000);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Cache-Control", "no-cache");
		String boundary = "-----------------------------" + System.currentTimeMillis();
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		conn.connect();

		InputStream inputStream = conn.getInputStream();

		// 把inputStream先转成byte[] 然后再转回 InputStream 。否则会导致图片不完整
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();

		byte[] buff = new byte[1024];
		int rc = 0;

		while ((rc = inputStream.read(buff)) > 0) {
			swapStream.write(buff, 0, rc);
		}

		byte[] in2b = swapStream.toByteArray();

		inputStream = new ByteArrayInputStream(in2b);

		inputStream.close();
		swapStream.close();
		return inputStream;
	}
}
