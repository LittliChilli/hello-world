/** 
 * @fileName ConstantsWeiXin.java
 * 
 * @version 
 * @author zousheng 
 * @date:2015年12月17日
 * Copyright © 2015.美眉分期 All rights reserved.
 */
package com.shop.common.wechat;

/**
 * @version
 * @author zousheng
 * @date:2015年12月17日
 */
public interface ConstantsWeiXin {

	/**
	 * 获取code地址
	 */
	String GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	/**
	 * 获取access_token地址
	 */
	String GetAccessToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**
	 * 获取JSSDKAccess_token
	 */
	String GET_ACCESS_TOKEN_CLIENT_CREDENTIAL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

	/**
	 * 获取临时素材MEDIA
	 */
	String GET_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	
	/**
	 * 获取ticket
	 */
	String GET_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 获取二维码ticket
	 */
	String GET_QRCODE_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	/**
	 * 获取用户基本信息
	 */
	String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取用户基本信息(未关注公众号)
	 */
	String GET_USER_INFO_NO_SUBSCRIBNE = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取场景二维码
	 */
	String GET_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	/**
	 * 授权作用域
	 */
	String SCOPE = "snsapi_userinfo";

	/**
	 * 授权作用域
	 */
	String SCOPE_BASE = "snsapi_base";
	
	/**
	 * 授权作用域登录
	 */
	String SCOPE_LOGIN = "snsapi_login";

	/**
	 * 微信统一支付地址
	 */
	String WEIXINPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 微信网页JSAPI支付方式
	 */
	String JSAPI = "JSAPI";

	/***
	 * 二维码支付
	 */
	String NATIVE = "NATIVE";

	/**
	 * 微信APP支付方式
	 */
	String TRADE_TYPE_APP = "APP";

	/**
	 * 微信控制参数授权ACCESS_TOKEN
	 */
	String WEIXIN_ACCESS_TOKEN = "ACCESS_TOKEN_CLIENT_CREDENTIAL";

	/**
	 * 微信参数JSAPI_TICKET
	 */
	String WEIXIN_JSAPI_TICKET = "JSAPI_TICKET";

	/**
	 * 签名方式
	 */
	String signType = "MD5";
	
	/**
	 * 推送信息给用户
	 */
	String SEND_MESSAGE_TO_USER = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	/**
	 * ACCESS_TOKEN常量类
	 */
	String ACCESS_TOKEN = "ACCESS_TOKEN";

}
