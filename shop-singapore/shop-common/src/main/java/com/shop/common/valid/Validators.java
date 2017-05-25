package com.shop.common.valid;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 公共校验类
 * 
 * @version
 * @author zousheng
 * @date:2016年12月5日
 */
public class Validators {

	/**
	 * 正则验证方法
	 * 
	 * @param regexstr
	 * @param str
	 * @return
	 */
	public static boolean Match(String regexstr, String str) {
		Pattern regex = Pattern.compile(regexstr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}

	/**
	 * 邮箱验证
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean MatchMail(String mail) {
		return RegexUtil.isValidEmail(mail);
	}
	
	/**
	 * 密码校验
	 * @version 
	 * @author hyj
	 * @date:2016年12月9日
	 * @param pwd
	 * @return
	 * boolean
	 */
	public static boolean MatchPwd(String pwd){
		return RegexUtil.isValidPwd(pwd);
	}

	/**
	 * 银行卡验证
	 * 
	 * @param bankCode
	 * @return
	 */
	public static boolean MatchBankCode(String bankCode) {
		String bankregex = "^(\\d{16}|\\d{19})$";
		return Match(bankregex, bankCode);
	}

	/**
	 * 手机验证
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean MatchMobile(String mobile) {
		String mobileregex = "^(0?1\\d{10})$";
		return Match(mobileregex, mobile);
	}

	/**
	 * 电话验证
	 * 
	 * @param Tel
	 * @return
	 */
	public static boolean MatchTel(String Tel) {
		String telregex = "(^[0-9]{3,4}-[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{7,15}$)";
		return Match(telregex, Tel);
	}

	public static boolean Webdomain(String webdomain) {
		String webdomainregex = "http://([^/]+)/*";
		return Match(webdomainregex, webdomain);
	}

	public static boolean ZipCode(String zipcode) {
		String zipcoderegex = "^[0-9]{6}$";
		return Match(zipcoderegex, zipcode);
	}

	/**
	 * 校验Double类型
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年8月31日
	 *
	 * @param str
	 * @return
	 */
	public static boolean checkNumerDouble(String str) {
		return RegexUtil.isNumerDouble(str);
	}

	/**
	 * 校验Double类型
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年8月31日
	 *
	 * @param str
	 * @return
	 */
	public static boolean checkNumerInt(String str) {
		return RegexUtil.isNumerInt(str);
	}

	/**
	 * 校验身份证号
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年9月10日
	 *
	 * @param idcard
	 * @return
	 */
	public static boolean checkIdCardNo(String idcard) {
		HashMap<Integer, String> area = new HashMap<Integer, String>();
		area.put(11, "北京");
		area.put(12, "天津");
		area.put(13, "河北");
		area.put(14, "山西");
		area.put(15, "内蒙古");
		area.put(21, "辽宁");
		area.put(22, "吉林");
		area.put(23, "黑龙江");
		area.put(31, "上海");
		area.put(32, "江苏");
		area.put(33, "浙江");
		area.put(34, "安徽");
		area.put(35, "福建");
		area.put(36, "江西");
		area.put(37, "山东");
		area.put(41, "河南");
		area.put(42, "湖北");
		area.put(43, "湖南");
		area.put(44, "广东");
		area.put(45, "广西");
		area.put(46, "海南");
		area.put(50, "重庆");
		area.put(51, "四川");
		area.put(52, "贵州");
		area.put(53, "云南");
		area.put(54, "西藏");
		area.put(61, "陕西");
		area.put(62, "甘肃");
		area.put(63, "青海");
		area.put(64, "宁夏");
		area.put(65, "新疆");
		area.put(71, "台湾");
		area.put(81, "香港");
		area.put(82, "澳门");
		area.put(91, "国外");
		if (StringUtils.isBlank(idcard))
			return false;
		// logger.info("身份证所在地：" + area.get(Integer.parseInt(idcard.substring(0,
		// 2))));
		if (area.get(Integer.parseInt(idcard.substring(0, 2))) == null)
			return false;
		if (!(idcard.length() == 15 || idcard.length() == 18))
			return false;
		if (idcard.length() == 15) {
			// 老身份证
			int year = Integer.parseInt(idcard.substring(2, 6)) + 1900;
			String ereg;
			if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
				ereg = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$";// 测试出生日期的合法性
			} else {
				ereg = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$";// 测试出生日期的合法性
			}
			if (Match(ereg, idcard))
				return true;
			else
				return false;

		} else if (idcard.length() == 18) {
			// 新省份证
			// 18位身份号码检测
			// 出生日期的合法性检查
			// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
			// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
			int year = Integer.parseInt(idcard.substring(2, 6)) + 1900;
			String ereg;
			if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
				ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$";// 闰年出生日期的合法性正则表达式
			} else {
				ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$";// 平年出生日期的合法性正则表达式
			}
			if (Match(ereg, idcard)) {// 测试出生日期的合法性
				// 计算校验位
				String[] idcards = new String[18];
				for (int i = 0; i < idcard.length(); i++) {
					idcards[i] = idcard.substring(i, i + 1);
				}
				int S = (Integer.valueOf(idcards[0]) + Integer.valueOf(idcards[10])) * 7
						+ (Integer.valueOf(idcards[1]) + Integer.valueOf(idcards[11])) * 9
						+ (Integer.valueOf(idcards[2]) + Integer.valueOf(idcards[12])) * 10
						+ (Integer.valueOf(idcards[3]) + Integer.valueOf(idcards[13])) * 5
						+ (Integer.valueOf(idcards[4]) + Integer.valueOf(idcards[14])) * 8
						+ (Integer.valueOf(idcards[5]) + Integer.valueOf(idcards[15])) * 4
						+ (Integer.valueOf(idcards[6]) + Integer.valueOf(idcards[16])) * 2
						+ Integer.valueOf(idcards[7]) * 1 + Integer.valueOf(idcards[8]) * 6
						+ Integer.valueOf(idcards[9]) * 3;
				int Y = S % 11;
				String M = "F";
				String JYM = "10X98765432";
				M = JYM.substring(Y, Y + 1);// 判断校验位
				if (StringUtils.equalsIgnoreCase(M, String.valueOf(idcards[17])))
					return true; // 检测ID的校验位
				else
					return false;
			} else
				return false;
		}
		return false;
	}

	/*
	 * 校验过程： 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
	 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
	 */
	/**
	 * 校验银行卡卡号
	 */
	public static boolean checkBankCard(String bankCard) {
		if (bankCard.length() < 15 || bankCard.length() > 19) {
			return false;
		}
		char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return bankCard.charAt(bankCard.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeBankCard
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
		if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
				|| !nonCheckCodeBankCard.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeBankCard.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	public static void main(String[] args) {

		// 电子邮件
		// System.out.println(MatchMail("dffdfdf@qq.com"));
		// System.out.println(MatchMobile("13555655606"));
		// 网上摘的几个身份证
		/*
		 * System.out.println(checkIdCardNo("420101198001300053"));
		 * System.out.println(checkIdCardNo("430911800709422"));
		 * System.out.println(checkIdCardNo("432534198803097018"));
		 */

		// 银行卡
		System.out.println(checkBankCard("6215888899995554"));

	}
}
