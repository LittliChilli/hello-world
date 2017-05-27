package com.shop.common.dateutil;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * 日期工具类
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public class DateUtil {

	/**
	 * 时间格式yyyy-MM-dd HH:mm:ss
	 */
	public final static String DateTime_Format = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式yyyy-MM-dd
	 */
	public final static String Date_Format = "yyyy-MM-dd";

	/**
	 * 时间格式HHmmss
	 */
	public final static String TIME_FORMAT = "HHmmss";

	/**
	 * 时间格式yyyyMMddHHmmss
	 */
	public final static String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

	/**
	 * 时间格式HHmmssSSS
	 */
	public final static String TIME_MILLI_SECONDS_FORMAT = "HHmmssSSS";

	/**
	 * 时间格式yyyyMMddHHmmssSSS
	 */
	public final static String DATETIME_MILLI_SECONDS_FORMAT = "yyyyMMddHHmmssSSS";

	/**
	 * 时间格式yyyy年MM月dd日
	 */
	public final static String HANIZATION_DATE_FORMAT = "yyyy年MM月dd日";

	/**
	 * /年/月/日定义
	 */
	public final static String YEAR_MANTH_DAY_DATE_FORMAT = "/年/月/日";

	/**
	 * 将指定字符串转换成日期
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月21日
	 * 
	 * @param date
	 * @param datePattern
	 * @return
	 */
	public static java.util.Date getFormatDate(String date, String datePattern) {

		if (StringUtils.isEmpty(date)) {
			return null;
		}

		Date t = null;

		try {
			SimpleDateFormat sf = new SimpleDateFormat(datePattern);
			t = sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return t;
	}

	/**
	 * 将指定日期对象转换成格式化字符串
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月21日
	 * 
	 * @param date
	 * @param datePattern
	 * @return
	 */
	public static String getFormatDateString(Date date, String datePattern) {
		SimpleDateFormat sd = new SimpleDateFormat(datePattern);
		return sd.format(date);
	}

	/**
	 * 获得今年信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月21日
	 * 
	 * @return
	 */
	public static String getThisYear() {
		// 获得当前日期
		Calendar cldCurrent = Calendar.getInstance();
		// 获得年月日
		String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
		return strYear;
	}

	/**
	 * 含有yyyy-MM-dd'T'hh:mm:ss.SSS格式的时间转换.
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月21日
	 * 
	 * @param tdate
	 * @return
	 */
	public static String getTFormatString(String tdate) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		String str = "";
		try {
			java.util.Date date = format1.parse(tdate);
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = format2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取当前时间前2个小时的时间
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月21日
	 * 
	 * @return
	 */
	public static String getBefore2HourDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -2);
		return df.format(c.getTime());

	}

	/**
	 * 比较2个时间大小
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param time1
	 * @param time2
	 * @param gap
	 * @return
	 */
	public static boolean compareDateTime(Date time1, Date time2, int gap) {
		return time1.getTime() - time2.getTime() > gap * 60 * 1000;
	}

	/**
	 * 获取当天时间String
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getNowDateString() {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(rightNow.getTime());
	}

	/**
	 * 获取当天时间String
	 * 
	 * @version
	 * @author lion
	 * @date:2016年3月2日
	 * 
	 * @return
	 */
	public static String getNowDateString(String datePattern) {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
		return dateFormat.format(rightNow.getTime());
	}

	/**
	 * 获取当前时间String
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getNowDateTimeString() {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(rightNow.getTime());
	}

	/**
	 * 获取现在时间
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 获取当前时间下个月的第一天
	 * 
	 * @Description
	 * 
	 * @version
	 * @author gly
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static Date getNextMonthStart(Date current) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(current);
		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + 1);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return ca.getTime();
	}

	/**
	 * 获取当前月份第一天
	 * 
	 * @Description
	 * 
	 * @version
	 * @author gly
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static Date getCurrentMonthStart(Date current) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(current);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return ca.getTime();
	}

	/**
	 * 获取当前月份最后一天23:59:59
	 * 
	 * @Description
	 * 
	 * @version
	 * @author gly
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static Date getCurrentMonthEnd(Date current) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(current);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		return ca.getTime();
	}

	/**
	 * 得到现在时间
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getHourByDate(Date date) {

		String dateString = getFormatDateString(date, DateTime_Format);

		return dateString.substring(11, 13);
	}

	/**
	 * 得到现在分钟
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param st1
	 * @param st2
	 * @return
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param sj1
	 * @param sj2
	 * @return
	 */
	public static Integer getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

		long day = 0;

		try {

			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return Integer.valueOf(String.valueOf(day));
		}

		return Integer.valueOf(String.valueOf(day));
	}

	/**
	 * 得到二个日期间的间隔天数
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param date
	 * @param mydate
	 * @return
	 */
	public static Integer getTwoDay(Date date, Date mydate) {

		long day = 0;

		try {

			day = getTwoDay(dateToStr(date), dateToStr(mydate));
		} catch (Exception e) {
			return Integer.valueOf(String.valueOf(day));
		}

		return Integer.valueOf(String.valueOf(day));
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param sj1
	 * @param jj
	 * @return
	 */
	public static String getPreTimeStr(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月21日
	 * 
	 * @param date
	 * @param jj
	 * @return
	 */
	public static Date getPreDateTime(Date date, Integer jj) {

		long Time = (date.getTime() / 1000) + jj * 60;
		date.setTime(Time * 1000);

		return date;
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示秒数.
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月21日
	 * 
	 * @param date
	 * @param jj
	 * @return
	 */
	public static Date getPreDateSecond(Date date, Integer jj) {

		long Time = (date.getTime() / 1000) + jj;
		date.setTime(Time * 1000);

		return date;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param nowdate
	 * @param delay
	 * @return
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 得到一个时间延后或前移几天的时间,date为时间,delay为前移或后延的天数
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param date
	 * @param delay
	 * @return
	 */
	public static Date getNextDay(Date date, Integer delay) {

		long myTime = (date.getTime() / 1000) + delay * 24 * 60 * 60;
		date.setTime(myTime * 1000);

		return date;
	}

	/**
	 * 得到一个天的零点时间
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param nowdate
	 * @param delay
	 * @return
	 */
	public static Date getDayTimeZeroSecond(Date nowdate) {

		String dateStr = getFormatDateString(nowdate, Date_Format) + " 00:00:00";

		return getFormatDate(dateStr, DateTime_Format);
	}

	/**
	 * 得到一个天的235959时间
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param nowdate
	 * @param delay
	 * @return
	 */
	public static Date getDayTimeLastSecond(Date nowdate) {

		String dateStr = getFormatDateString(nowdate, Date_Format) + " 23:59:59";

		return getFormatDate(dateStr, DateTime_Format);
	}

	/**
	 * 判断是否润年
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 获取一个月的最后一天yyyy-MM-dd
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @version
	 * @author lion
	 * @date:2015年10月26日
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(Date date1, Date date2) {
		long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 获取时间格式带毫秒
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getTimeMilliseconds() {

		return getTimeMilliseconds(getNow());
	}

	/**
	 * 获取时间格式带毫秒
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getTimeMilliseconds(Date now) {

		return getFormatDateString(now, DATETIME_MILLI_SECONDS_FORMAT);
	}

	/**
	 * 获取时间后6位
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月10日
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getTimeString(Date dateTime) {

		return getFormatDateString(dateTime, TIME_FORMAT);
	}

	/**
	 * 获取时间后9位
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年11月10日
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getTimeMilliSecondsString(Date dateTime) {

		return getFormatDateString(dateTime, TIME_MILLI_SECONDS_FORMAT);
	}

	/**
	 * 获取时间格式String
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 */
	public static String getTimeMilliStr() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = formatter.format(getNow());

		return time;
	}

	/**
	 * 查询开始时间获取
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param startTime
	 * @return
	 */
	public static String getStartDate(String startTime, int months) {
		if (StringUtils.isEmpty(startTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -months); // 得到前一个月
			Date startDate = calendar.getTime();
			startTime = DateUtil.getFormatDateString(startDate, "yyyy-MM-dd 00:00:00");
		} else {
			startTime = startTime + " " + "00:00:00";
		}
		return startTime;
	}

	/**
	 * 查询结束时间获取
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param endTime
	 * @return
	 */
	public static String getEndDate(String endTime) {
		if (StringUtils.isEmpty(endTime)) {
			endTime = DateUtil.getFormatDateString(new Date(), "yyyy-MM-dd 23:59:59");
		} else {
			endTime = endTime + " " + "23:59:59";
		}
		return endTime;
	}

	/**
	 * 查询开始时间获取（带时分秒）
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param startTime
	 * @return
	 */
	public static String getSearchStartTime() {

		return DateUtil.getStartTime(null, 1);
	}

	/**
	 * 查询开始时间获取（带时分秒）
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param startTime
	 * @return
	 */
	public static String getStartTime(String startTime, int months) {
		if (StringUtils.isEmpty(startTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -months); // 得到前一个月
			Date startDate = calendar.getTime();
			startTime = DateUtil.getFormatDateString(startDate, "yyyy-MM-dd 00:00:00");
		}
		if (startTime.length() <= 10) {
			startTime = startTime + " 00:00:00";
		}
		return startTime;
	}

	/**
	 * 查询结束时间获取（带时分秒）
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param endTime
	 * @return
	 */
	public static String getSearchEndTime() {

		return getEndTime(null);
	}

	/**
	 * 查询结束时间获取（带时分秒）
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param endTime
	 * @return
	 */
	public static String getEndTime(String endTime) {
		if (StringUtils.isEmpty(endTime)) {
			endTime = DateUtil.getFormatDateString(new Date(), "yyyy-MM-dd 23:59:59");
		}
		if (endTime.length() <= 10) {
			endTime = endTime + " 23:59:59";
		}
		return endTime;
	}

	/**
	 * 查询开始时间获取（带时分秒）
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param startTime
	 * @return
	 */
	public static String getStartTimeForDay(String startTime, int days) {
		if (StringUtils.isEmpty(startTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -days); // 得到前一天
			Date startDate = calendar.getTime();
			startTime = DateUtil.getFormatDateString(startDate, "yyyy-MM-dd 00:00:00");
		}
		if (startTime.length() <= 10) {
			startTime = startTime + " 00:00:00";
		}
		return startTime;
	}

	/**
	 * 查询结束时间获取（带时分秒）
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param startTime
	 * @return
	 */
	public static String getEndTimeForDay(String startTime, int days) {
		if (StringUtils.isEmpty(startTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -days); // 得到前一天
			Date startDate = calendar.getTime();
			startTime = DateUtil.getFormatDateString(startDate, "yyyy-MM-dd 23:59:59");
		}
		return startTime;
	}

	/**
	 * 计算指定时间与当前的时间差 比如，3天前、10分钟前
	 * 
	 * @version
	 * @author chenhui
	 * @date:2016年4月27日
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeInterval(Date date) {

		long between = 0;
		String result = "";

		Date begin = date;
		Date end = getNow();

		between = (end.getTime() - begin.getTime()) / 1000;// 获得两个时间的秒数差

		if (between < 60) {
			result = "刚刚";
		} else if ((between = between / 60) < 60) {
			result = between + "分前";
		} else if ((between = between / 60) < 24) {
			result = between + "小时前";
		} else if ((between = between / 24) < 30) {
			result = between + "天前";
		} else if ((between = between / 30) < 12) {
			result = between + "月前";
		} else {
			between = between / 12;
			result = between + "年前";
		}

		return result;
	}

	/**
	 * 查询开始时间获取（不带时分秒）
	 * 
	 * @Description
	 * 
	 * @version
	 * @author lion
	 * @date:2015年11月24日
	 * 
	 * @param startTime
	 * @return
	 */
	public static String getStartDateForDay(String startTime, int days) {
		if (StringUtils.isEmpty(startTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -days); // 得到前一天
			Date startDate = calendar.getTime();
			startTime = DateUtil.getFormatDateString(startDate, "yyyy-MM-dd");
		}
		return startTime;
	}

	/**
	 * 获取现在时间
	 * 
	 * @version
	 * @author zousheng
	 * @date:2015年10月26日
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByDate(Date date) {
		// Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		// ParsePosition pos = new ParsePosition(8);
		Date currentTime_2;
		try {
			currentTime_2 = formatter.parse(dateString);
			return currentTime_2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
