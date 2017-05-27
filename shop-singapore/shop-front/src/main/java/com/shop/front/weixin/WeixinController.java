package com.shop.front.weixin;

import java.rmi.server.ServerCloneException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.common.exception.ServiceException;
import com.shop.common.wechat.JsSDKSign;
import com.shop.common.wechat.WeiXinAuthorUtil;
import com.shop.common.wechat.WeixinParamProperty;
import com.shop.common.wechat.vo.JsSDKConfig;
import com.shop.front.common.UserController;

@Controller
@RequestMapping("/singapore/weixin")
public class WeixinController extends UserController {

	/**
	 * 加载jsSDKConfig
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月15日
	 *
	 * @param model
	 * @param request
	 * @param targetUrl
	 * @return
	 */
	@RequestMapping(value = "/getJsSDKConfig")
	@ResponseBody
	public Result getJsSDKConfig(Model model, HttpServletRequest request, String targetUrl) {

		if (StringUtils.isBlank(targetUrl)) {
			throw new ServiceException(Result.ERROR_CODE, "获取微信配置失败,请重新打开页面");
		}

		String jsapiTicket = WeiXinAuthorUtil.getWeixinJsapiTicket(WeixinParamProperty.SHOP_APPID,
				WeixinParamProperty.SHOP_SECRET);

		int tryTimes = 2;
		while (StringUtils.isEmpty(jsapiTicket) && tryTimes > 0) {
			jsapiTicket = WeiXinAuthorUtil.getWeixinJsapiTicket(WeixinParamProperty.SHOP_APPID,
					WeixinParamProperty.SHOP_SECRET);
			tryTimes--;
		}

		if (StringUtils.isBlank(jsapiTicket)) {
			throw new ServiceException(Result.ERROR_CODE, "获取微信配置失败,请重新打开页面");
		}

		// 页面签名
		Map<String, String> retMap = JsSDKSign.sign(jsapiTicket, targetUrl);
		JsSDKConfig jsSDKConfig = new JsSDKConfig();
		jsSDKConfig.setAppId(WeixinParamProperty.SHOP_APPID);
		jsSDKConfig.setNonceStr(retMap.get("nonceStr"));
		jsSDKConfig.setTimestamp(retMap.get("timestamp"));
		jsSDKConfig.setSignature(retMap.get("signature"));

		model.addAttribute("jsSDKConfig", jsSDKConfig);

		return super.result(model);
	}

}
