package com.shop.common.valid;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 正则表达式配置
 * 
 * @version
 * @author zousheng
 * @date:2016年12月5日
 */
public class RegexUtil {

	static final String NUMERIC = "^[0-9]*$";
	static final String URL = "\\b((ftp|https?)://[-\\w]+(\\.\\w[-\\w]*)+|(?i:[a-z0-9](?:[-a-z0-9]*[a-z0-9])?\\.)+(?-i:com\\b|edu\\b|biz\\b|gov\\b|in(?:t|fo)\\b|mil\\b|net\\b|org\\b|[a-z][a-z]\\b))(:\\d+)?(/[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]*(?:[.!,?]+[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]+)*)?";
	static final String FENQI_URL = "^(http://)?([^\\.]\\.)?mmfenqi.com";
	static final String PHONE = "(\\(\\d{3}\\)|\\d{3,4}-)?\\d{7,8}$";
	static final String CELLPHONE = "0?1\\d{10}";
	static final String POSTCODE = "^[0-9]\\d{5}$";
	static final String DOUBLE_NUMBER = "^[0-9]{1,10}\\.{0,1}[0-9]{1,6}$|^[0-9]\\d{1,10}$";
	static final String INT_NUMBER = "^[0-9]{1,9}$";

	/**
	 * 邮箱正则校验
	 */
	static final String EMAIL = "^[a-z0-9A-Z_\\+-]+(\\.[a-z0-9A-Z_\\+-]+)*@[a-z0-9A-Z-]+(\\.[a-z0-9A-Z-]+)*\\.([a-zA-Z]{2,4})$";

	/**
	 * 登录名称正则校验
	 */
	static final String LOGIN_NAME = "^[a-z0-9_\\+-]+(\\.[a-z0-9_\\+-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2,4})$";
	
	/**
	 * 登录密码正则校验
	 */
	static final String LOGIN_PASSWORD = "(^[a-z0-9A-Z@]{6,30})$";

	/**
	 * 判断content是否合法(用于js判断内容是否合法)
	 *
	 * @param content
	 * @return
	 */
	public static boolean isLegal(String content) {
		return exsitsAll(listExsits(content, URL), FENQI_URL);
	}

	/**
	 * 判断content中存在regex表达(默认Pattern.CASE_INSENSITIVE)的结果
	 *
	 * @param content
	 * @param regex
	 * @return
	 */
	public static List<String> listExsits(String content, String regex) {
		Pattern p = Pattern.compile(regex, 0);
		Matcher matcher = p.matcher(content);
		List<String> results = new ArrayList<String>();
		String result = null;
		while (matcher.find()) {
			result = matcher.group();
			results.add(result);
		}
		return results;
	}

	/**
	 * 判断content中存在regex表达的结果
	 *
	 * @param content
	 * @param regex
	 * @param pattern
	 * @return
	 */
	public static List<String> listExsits(String content, String regex, int pattern) {
		Pattern p = Pattern.compile(regex, pattern);
		Matcher matcher = p.matcher(content);
		List<String> results = new ArrayList<String>();
		String result = null;
		while (matcher.find()) {
			result = matcher.group();
			results.add(result);
		}
		return results;
	}

	/**
	 * 判断content中是否存在regex表达(默认Pattern.CASE_INSENSITIVE)
	 *
	 * @param content
	 * @param regex
	 * @return
	 */
	public static boolean exsit(String content, String regex) {
		return exist(content, regex, 0);
	}

	/**
	 * 检查content中是否存在regex表达
	 *
	 * @param content
	 * @param regex
	 * @param pattern
	 * @return
	 */
	public static boolean exist(String content, String regex, int pattern) {
		if (pattern == 0)
			pattern = Pattern.CASE_INSENSITIVE;
		Pattern p = Pattern.compile(regex, pattern);
		Matcher matcher = p.matcher(content);
		return matcher.find();
	}

	/**
	 * 返回content中存在regex表达(默认Pattern.CASE_INSENSITIVE)的数量
	 *
	 * @param content
	 * @param regex
	 * @return
	 */
	public static int countExsits(String content, String regex) {
		Pattern p = Pattern.compile(regex, 0);
		Matcher matcher = p.matcher(content);
		int result = 0;
		while (matcher.find()) {
			matcher.group();
			result++;
		}
		return result;
	}

	/**
	 * 返回content中存在regex表达(默认Pattern.CASE_INSENSITIVE)的数量
	 *
	 * @param contents
	 * @param regex
	 * @return
	 */
	public static int countExsits(List<String> contents, String regex) {
		Pattern p = Pattern.compile(regex, 0);
		Matcher matcher = null;
		int result = 0;
		for (String content : contents) {
			matcher = p.matcher(content);
			if (matcher.find())
				result++;
		}
		return result;
	}

	/**
	 * 判断列表contents中都存在regex表达
	 *
	 * @param contents
	 * @param regex
	 * @return
	 */
	public static boolean exsitsAll(List<String> contents, String regex) {
		return contents.size() == countExsits(contents, regex);
	}

	/**
	 * 判断注册邮箱是否正确
	 *
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		if (StringUtils.isNotBlank(email)) {
			return email.matches(EMAIL);
		}
		return false;
	}

	/**
	 * 判断密码格式是否正确
	 * @version 
	 * @author hyj
	 * @date:2016年12月9日
	 * @param pwd
	 * @return
	 * boolean
	 */
	public static boolean isValidPwd(String pwd){
		if(StringUtils.isNotEmpty(pwd)){
			return pwd.matches(LOGIN_PASSWORD);
		}
		return false;
	}
	
	/**
	 * 判断手机号码是否正确
	 *
	 * @param cellphone
	 * @return
	 */
	public static boolean isValidCellphone(String cellphone) {
		if (StringUtils.isNotBlank(cellphone)) {
			return cellphone.matches(CELLPHONE);
		}
		return false;
	}

	/**
	 * @param url
	 * @return
	 */
	public static boolean isValidUrl(String url) {
		if (StringUtils.isNotBlank(url)) {
			return url.matches(URL);
		}
		return false;
	}

	/**
	 * 校验纯数字
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年8月31日
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile(NUMERIC);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
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
	public static boolean isNumerDouble(String str) {
		Pattern pattern = Pattern.compile(DOUBLE_NUMBER);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 校验Int类型
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年8月31日
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumerInt(String str) {
		Pattern pattern = Pattern.compile(INT_NUMBER);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		// Pattern pattern = Pattern.compile(DOUBLE_NUMBER);
		// Matcher isNum = pattern.matcher("123.00");
		// if (!isNum.matches()) {
		// System.err.println(false);
		// System.err.println("不匹配");
		//
		// } else {
		// System.err.println(true);
		// System.err.println("匹配");
		// }
		//
		// System.err.println(isNumeric("123.0"));

//		if (RegexUtil.isValidEmail("Zous@mmfenqi.com")) {
//
//			System.out.println("正确的邮箱");
//		} else {
//			System.err.println("错误的邮箱");
//		}
		
		if(RegexUtil.isValidPwd("1234234512345")){
			System.out.println("pass");
		}else{
			System.out.println("not pass");
		}

	}
}
