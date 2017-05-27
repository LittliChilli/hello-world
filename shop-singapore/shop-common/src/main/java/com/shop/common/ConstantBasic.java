package com.shop.common;

public interface ConstantBasic {

	interface Punctuation {

		/** 空 */
		String BLANK = "";
		/** 点号 */
		String DOT = ".";
		/** 点点 */
		String DOTDOT = "..";
		/** 点点 */
		String DOTDOTDOT = "...";
		/** 逗号 */
		String COMMA = ",";
		/** 冒号 */
		String COLON = ":";
		/** 分号 */
		String SEMICOLON = ";";
		/** 换行符 */
		String NEWLINE = "\n";
		/** 斜杠 **/
		String SLASH = "/";
		/** 问号 */
		String QUESTION = "?";
		/** 单引号 */
		String QUOTATION = "'";
		/** 下划线 */
		String UNDERLINE = "_";
		/** 竖线 */
		String VERTICAL = "|";
		/** 美元符 */
		String DOLLARS = "$";
		/** 人民币 */
		String RMB = "￥";
		/** 零 */
		String ZERO = "0";
		/** 一 */
		String ONE = "1";
		/** 十 */
		String TEN = "10";
		/** null字符串 */
		String NULL = "null";
		/** 大括号 */
		String BRACE = "{}";
		/** 左大括号 */
		String LEFT_BRACE = "{";
		/** 右大括号 */
		String RIGHT_BRACE = "}";
		/** 中括号 */
		String BRACKET = "[]";
		/** 左中括号 */
		String LEFT_BRACKET = "[";
		/** 右中括号 */
		String RIGHT_BRACKET = "]";
		/** 小括号 */
		String PARENTHESES = "()";
		/** 左小括号 */
		String LEFT_PARENTHESES = "(";
		/** 右小括号 */
		String RIGHT_PARENTHESES = ")";
		/** 双引号 */
		String DOUBLE_QUOTATION = "\"";
		/** 单引号 */
		String SINGLE_QUOTATION = "\'";
		/** 等号 */
		String EQUALS = "=";
		/** #号 */
		String HASH = "#";
		/**
		 * %号
		 */
		String PERCENT = "%";
	}

	/**
	 * 定义标准的格式
	 * 
	 */
	interface Format {
		String JSON = "json";

		String XML = "xml";
	}

	/**
	 * 单词
	 * 
	 */
	interface Word {
		String N = "n";
		String NO = "no";
		String NULL = "null";
		String OFF = "off";
		String ON = "on";
		String Y = "y";
		String YES = "yes";
		String ONE = "1";
		String ZERO = "0";
		String FALSE = "false";
		String TRUE = "true";
	}

	/**
	 * 短信同通知配置
	 */
	interface SmsConfig {
		String smsapi = "http://h.1069106.com:1210/services/msgsend.asmx/SendMsg";
		String usercode = "hzyb";
		String userPwd = "HZ13958076240yb";
		String channel = "0";
		// 短信模版，利用string.format来简单格式化
		// String template1 =
		// "美美分期安全中心:%s是您本次身份验证码，5分钟内有效，美美分期工作人员绝不会向您索取此验证码，切勿告知他人。【美美分期】";
		String template2 = "美眉分期安全中心:%s是您本次身份验证码，5分钟内有效，美眉分期工作人员绝不会向您索取此验证码，切勿告知他人。【美眉分期】";

		String template3 = "幸运的%realName%,您参加一元夺宝活动成功夺宝(%luckyGoods%)，详情请登录 微信公众号 查看。【美眉分期】";

		String template4 = "亲爱的%realName%美眉，在您变得越来越美的同时，不要忘记美眉分期还款哦，您于%date%前应还美眉分期贷款%amount%元，请及时还款以免逾期哦！【美眉分期】";

		String template5 = "%realName%同学，温馨的提醒您，您在美眉分期的第%staging%期分期，还款日为%date%， 还款金额%amount%元，请及时还款以免逾期！如已还款请飘过【美眉分期】";

		String template6 = "您监控的短信验证码发送数据,今天已发送%number%条,请立即检测是否正常。【美眉分期】";
	}

	/**
	 * 订单业务类型编码
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月6日
	 */
	interface OrderTypeCode {

		/**
		 * mmfq订单生成
		 */
		String MMFEQ_ORDER_CODE = "100001";

		/**
		 * 一元购充值
		 */
		String LUCKY_RECHARGE_CODE = "200001";

		/**
		 * 微信退款
		 */
		String WEIXIN_REFUND_CODE = "300001";

		/**
		 * 拼团订单
		 */
		String MMFEQ_PINTUAN_ORDER_CODE = "400001";

		/**
		 * 保险订单
		 */
		String MMFEQ_ORDER_INSURANCE_CODE = "500001";
		
		/**
		 * SingApore orderSn
		 */
		String SINGAPORE_ORDER_CODE = "600001";
		
		/**
		 * SingApore goodsSn
		 */
		String SINGAPORE_GOODS_SN = "700001";
		
		/**
		 * SingApore orderSn
		 */
		String SINGAPORE_TEAM_ORDER_CODE = "800001";
	}
}
