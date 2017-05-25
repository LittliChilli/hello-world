/** 
 * @fileName WeiXinAuthorUtil.java
 * 
 * @version 
 * @author zousheng 
 * @date:2015年11月11日
 * Copyright © 2015.美眉分期 All rights reserved.
 */
package com.shop.common.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.encode.EncodeUtils;

/**
 * 微信权限信息工具类
 * 
 * @version
 * @author zousheng
 * @date:2016年12月15日
 */
public class WeiXinAuthorUtil {

	/**
	 * 获取用户基本信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年5月19日
	 *
	 * @param state
	 * @param appId
	 * @param web_redirect_uri
	 * @param scope
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getUserInfoByAccessToken(String accessToken, String weixinOpenId) {

		String getUserInfo = ConstantsWeiXin.GET_USER_INFO.replace("ACCESS_TOKEN", accessToken);
		getUserInfo = getUserInfo.replace("OPENID", weixinOpenId);
		String responseJson = WeiXinAuthorUtil.accessHttps(getUserInfo, "", "POST");

		return (Map<String, String>) JSONObject.parseObject(responseJson, Map.class);
	}

	/**
	 * 获取微信临时票据code
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年5月19日
	 *
	 * @param state
	 * @param appId
	 * @param web_redirect_uri
	 * @param scope
	 * @return
	 */
	public static String getWeixinCodeUrl(String state, String appId, String web_redirect_uri, String scope) {

		String scopeBase = ConstantsWeiXin.SCOPE_BASE;

		if (StringUtils.isNotEmpty(scope)) {
			scopeBase = scope;
		}

		String OAuthUrl = null;
		OAuthUrl = ConstantsWeiXin.GetCodeRequest.replace("APPID", EncodeUtils.urlEncode(appId));
		OAuthUrl = OAuthUrl.replace("REDIRECT_URI", EncodeUtils.urlEncode(web_redirect_uri));

		if (StringUtils.isNotEmpty(state)) {
			OAuthUrl = OAuthUrl.replace("STATE", EncodeUtils.urlEncode(state));
		}

		OAuthUrl = OAuthUrl.replace("SCOPE", EncodeUtils.urlEncode(scopeBase));
		return OAuthUrl;
	}

	/**
	 * 获取微信 openId
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年12月17日
	 *
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getWeiXinInfoOpenId(String code, String appid, String secret) {

		// 获取openId
		String getAccessToken = ConstantsWeiXin.GetAccessToken.replace("APPID", appid);
		getAccessToken = getAccessToken.replace("SECRET", secret);
		getAccessToken = getAccessToken.replace("CODE", code);
		String responseJson = accessHttps(getAccessToken, "", "POST");

		return (Map<String, String>) JSONObject.parseObject(responseJson, Map.class);
	}

	/**
	 * 获取微信接口权限签名
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月15日
	 *
	 * @param appId
	 * @param secret
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getWeixinJsapiTicket(String appId, String secret) {

		String accessToken = getWeixinAccessToken(appId, secret);

		if (StringUtils.isEmpty(accessToken)) {
			return "";
		}

		// 获取权限签名
		String jsapiTicketUrl = ConstantsWeiXin.GET_TICKET.replace("ACCESS_TOKEN", accessToken);

		String responseJson = WeiXinAuthorUtil.accessHttps(jsapiTicketUrl, "", "POST");

		Map<String, String> jsapiTicketMap = (Map<String, String>) JSONObject.parseObject(responseJson, Map.class);

		return jsapiTicketMap.get("ticket");
	}

	/**
	 * 获取微信授权接口accessToken
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月15日
	 *
	 * @param appId
	 * @param secret
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getWeixinAccessToken(String appId, String secret) {

		// 获取accessToken
		String accessTokenUrl = ConstantsWeiXin.GET_ACCESS_TOKEN_CLIENT_CREDENTIAL.replace("APPID", EncodeUtils.urlEncode(appId));
		accessTokenUrl = accessTokenUrl.replace("SECRET", EncodeUtils.urlEncode(secret));

		String responseJson = accessHttps(accessTokenUrl, "", "POST");

		// 获取accessToken
		Map<String, String> accessTokenMap = (Map<String, String>) JSONObject.parseObject(responseJson, Map.class);

		return accessTokenMap.get("access_token");
	}

	/**
	 * 访问https并返回数据
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年10月21日
	 *
	 * @param url
	 * @param reqXml
	 * @return
	 */
	public static String accessHttps(String url, String reqXml, String requestMethod) {

		URL myURL;
		InputStreamReader ins = null;
		String str = null;
		StringBuffer buffer = new StringBuffer();
		try {
			myURL = new URL(url);
			HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
			httpsConn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
			httpsConn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
			httpsConn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
			httpsConn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
			httpsConn.setUseCaches(false); // Post 请求不能使用缓存
			httpsConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpsConn.setRequestMethod(requestMethod);// 设定请求的方法为"POST"，默认是GET
			httpsConn.setRequestProperty("Content-Length", reqXml.length() + "");
			OutputStreamWriter out = new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8");
			out.write(reqXml.toString());
			out.flush();
			out.close();
			// 取得该连接的输入流，以读取响应内容
			ins = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(ins);
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str).append("\n");
			}
			ins.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toString().trim();
	}
}
