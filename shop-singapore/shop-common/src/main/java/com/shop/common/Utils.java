package com.shop.common;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.shop.common.dateutil.DateUtil;


/**
 * 描述赶紧写上
 *
 * @author <a href="mailto:hellmash@gmail.com">hellmash</a>
 * @version create on 2015-05-02 11:37
 */
public class Utils {

	/**
	 * 订单号混淆规则对应:0123456789
	 */
	private final static String[] ORDERNO_CONFUSION = { "0", "9", "6", "7", "8", "5", "2", "3", "4", "1" };

	/**
	 * 时间格式化工具
	 */
	private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");

	/**
	 * The FieldPosition.
	 */
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

	/**
	 * 数字格式化
	 */
	private final static NumberFormat numberFormat = new DecimalFormat("0000");

	/**
	 * 时间序列
	 */
	private static int seq = 0;

	private static final int MAX = 9999;

	/**
	 * 生成校验码
	 *
	 * @return
	 */
	public static String genVerifyCode() {
		Random random = new Random();
		int first = random.nextInt(9) + 1;
		String str = "" + first;
		for (int i = 0; i < 5; i++) {
			int digit = random.nextInt(10);
			str += digit;
		}

		return str;
	}
	
	/**
	 * 生成几位随机数
	 *
	 * @return
	 */
	public static String genRandomNumber(Integer num) {
		Random random = new Random();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			int digit = random.nextInt(10);
			sb.append(digit);
		}

		return sb.toString();
	}

	/**
	 * 获取访问ip
	 *
	 * @param request
	 * @return
	 */
	public final static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// android会返回多个代理,第一个ip为真实ip
		if (null != ip && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.split(",")[0];
			}
		}
		return ip;
	}

	/**
	 * 通过uuid生成订单编号,改为订单业务类型+ 用户ID倒序 + 时间戳
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 *
	 * @param userId
	 * @return
	 */
	public static String getNextOrderSn(String orderTypeCode) {
		/*
		 * String uuid = UUID.randomUUID().toString(); return uuid.substring(0,
		 * 8) + uuid.substring(9, 13) + uuid.substring(14, 18) +
		 * uuid.substring(19, 23) + uuid.substring(24);
		 */

		return orderTypeCode + DateUtil.getTimeMilliseconds();
	}

	/**
	 * 字符串倒序输出
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 *
	 * @param str
	 * @return
	 */
	public static String reverseString(String str) {
		StringBuffer stringBuffer = new StringBuffer(str);

		return stringBuffer.reverse().toString();
	}

	/**
	 * 时间格式生成序列,通过时间和末尾4个尾数
	 *
	 * @return String
	 */
	public static synchronized String generateSequenceNo() {

		Calendar rightNow = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
		numberFormat.format(seq, sb, HELPER_POSITION);

		// 序列累加，避免重复
		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}

		return sb.toString();
	}

	/**
	 * 与第三方交互号码混淆定义,后8位混淆规则,根据号码(前三位数求和/2 + 第二位)*(第三位
	 * ＋１),生成二维数组,后续1-8位，取数组[1-8,号码数].
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月4日
	 *
	 * @param thirdNumber
	 */
	public static String[][] convertIntegerArray(Integer fristNumber, Integer secondNumber, Integer thirdNumber) {

		String[][] numSeven = new String[11][];

		int thirdNumberTemp1 = ((fristNumber + secondNumber + thirdNumber) / 2 + secondNumber) * (thirdNumber + 1);

		// 按照电话号码第三位从后加,加10位.
		for (int i = 1; i < 11; i++) {
			thirdNumberTemp1 = thirdNumberTemp1 + 1;

			int thirdNumberTemp2 = thirdNumberTemp1;

			numSeven[i] = new String[11];
			for (int j = 1; j < 11; j++) {
				thirdNumberTemp2 = thirdNumberTemp2 + 1;

				if (thirdNumberTemp2 > 9) {
					numSeven[i][j] = String.valueOf(thirdNumberTemp2 % 10);
				} else {
					numSeven[i][j] = String.valueOf(thirdNumberTemp2);
				}
				// System.out.print(numSeven[i][j] + ",");
			}
			// System.out.println();
		}

		return numSeven;
	}

	

	
	/**
	 * String数组转化为List<Integer>
	 *
	 * @param arr
	 * @return
	 */
	public static List<Integer> toTranInteger(String[] arr) {
		List<Integer> list = new ArrayList<Integer>();
		if (arr != null && arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				list.add(Integer.parseInt(arr[i]));
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月8日
	 *
	 * @param str
	 *            输入字符串
	 * @param begin
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	public static String toAddStar(String str, int begin, int end) {
		// 取值从1开始
		begin = begin - 1;
		// 防止begin小于0
		if (begin < 0) {
			begin = 0;
		}

		if (begin > str.length() || begin >= end) {
			return str;
		}

		if (end > str.length()) {
			end = str.length();
		}

		// 打星的个数
		int i = end - begin;

		StringBuffer star = new StringBuffer();

		for (; i > 0; i--) {
			star.append("*");
		}

		return str.substring(0, begin) + star + str.substring(end);
	}

	
	/**
	 * String字符串分割Long数组
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月25日
	 *
	 * @param str
	 * @return
	 */
	public static List<Long> splitArrayLong(String str, String character) {

		if (StringUtils.isBlank(str)) {
			return null;
		}

		String[] strArray = StringUtils.splitPreserveAllTokens(str, character);

		List<Long> intArray = new ArrayList<Long>();

		for (String intStr : strArray) {
			intArray.add(Long.valueOf(intStr));
		}

		return intArray;
	}

	/**
	 * String字符串分割Integer数组
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月25日
	 *
	 * @param str
	 * @return
	 */
	public static ArrayList<Integer> splitArrayInteger(String str, String character) {

		if (StringUtils.isBlank(str)) {
			return null;
		}

		String[] strArray = StringUtils.splitPreserveAllTokens(str, character);

		ArrayList<Integer> intArray = new ArrayList<Integer>();

		for (String intStr : strArray) {
			intArray.add(Integer.valueOf(intStr));
		}

		return intArray;
	}

	/**
	 * String字符串分割String数组
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月25日
	 *
	 * @param str
	 * @return
	 */
	public static ArrayList<String> splitArrayString(String str, String character) {

		if (StringUtils.isBlank(str)) {
			return null;
		}

		String[] strArray = StringUtils.splitPreserveAllTokens(str, character);

		ArrayList<String> stringArray = new ArrayList<String>();

		for (String strObj : strArray) {
			stringArray.add(strObj);
		}

		return stringArray;
	}

	/**
	 * 创建测试使用电话号码
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年12月18日
	 *
	 * @return
	 */
	public static String createTelphone() {

		int[] telphoneArrays = { 134, 135, 136, 137, 138, 139, 150, 151, 152, 158, 159, 182, 183, 184, 157, 187, 188,
				147, 178, 130, 131, 132, 155, 156, 145, 185, 186, 176, 185, 133, 153, 180, 181, 189, 177 };

		Random random = new Random();
		Integer randomCount = random.nextInt(telphoneArrays.length);

		if (randomCount >= 35) {
			randomCount = 34;
		}

		int telphonePrefix = telphoneArrays[randomCount];

		StringBuffer sb = new StringBuffer();
		sb.append(telphonePrefix);
		sb.append("*");
		for (int i = 0; i < 7; i++) {
			int digit = random.nextInt(9);
			sb.append(digit);
		}

		return sb.toString();
	}

	/**
	 * 金额double类型保留2位小数
	 * 
	 * @param d
	 * @return
	 */
	public static String doubleFormat(double d) {

		DecimalFormat df = new DecimalFormat("####0.00");

		return df.format(d);
	}

	/**
	 * 订单号混淆操作： 医院id + 订单id + 随机4位数 + 订单号混淆转换
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年6月23日
	 *
	 * @return
	 */
	public static String convertOrderNoConfusion(String hosId, String orderId) {

		String str = hosId + orderId + genRandomNumber(4);
		
		if (StringUtils.isEmpty(str)) {
			return str;
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			sb.append(ORDERNO_CONFUSION[Integer.valueOf(str.substring(i, i + 1))]);
		}

		return sb.toString();
	}

	/*public static void main(String[] args) {

		String ss = "45679";

		for (int i = 0; i < ss.length(); i++) {
			System.out.println(ORDERNO_CONFUSION[Integer.valueOf(ss.substring(i, i + 1))]);
		}
	}*/
}
