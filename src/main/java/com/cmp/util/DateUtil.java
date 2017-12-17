package com.cmp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	/** 默认时间格式 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	
	/** 日期时间格式：yyyy-MM-dd */
	public static final String DAY_FORMAT = "yyyy-MM-dd";
	/** 年份时间格式：yyyy */
	public static final String YEAR_FORMAT = "yyyy";
	/** 月份时间格式：yyyy-MM */
	public static final String MONTH_FORMAT = "yyyy-MM";
	/**
	 * 年月日
	 */
	public static final String DATEFORMAT_DAY_NODELIMITER = "yyyyMMdd";

	/**
	 * 年-月-日
	 */
	public static final String DATEFORMAT_DAY = "yyyy-MM-dd";
	
	public static final String DATEFORMAT_DAY2 = "MM/dd/yy";
	
	/**
	 * 月/日/年 时:分:秒
	 */
	public static final String DATE_FORMAT_RYN_SFM = "MM/dd/yy HH:mm:ss";
	
	/** 默认时间格式 */
	public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
	
	/**
	 * 将字符串转换为时间格式java.util.Date
	 * 
	 * @param time
	 *            传入字符串时间 如：“2012-08-19”
	 * @param format
	 *            传入格式 如：yy/MM/dd HH:mm:ss
	 * @return
	 */
	public static Date stringToDate(String time, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * //将java.util.Date转换为字符串
	 * 
	 * @param time
	 *            传入字符串时间 如：“2012-08-19”
	 * @param format
	 * @return
	 */
	public static String dateToString(Date time, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getCurrentDayAfter(int day) {
		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.DATE, day);
		String dayAfter = new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
		return dayAfter;
	}

	/**
	 * 得到Timestamp格式的当前时间
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date parse(String strDate, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(strDate);
	}

	/**
	 * 
	 * @Title: calDuration
	 * @Description: 计算出起始时间到现在的时间间隔
	 * @param beginDate
	 * @param format
	 * @return String
	 */
	public static String calDuration(String beginDate, String format) {
		SimpleDateFormat sdf;
		StringBuilder result = new StringBuilder();
		if (!StringUtils.isEmpty(format)) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		}
		try {
			long beginTime = sdf.parse(beginDate).getTime();
			long endTime = System.currentTimeMillis();
			long durationTime = endTime - beginTime;
			long day = durationTime / (24 * 60 * 60 * 1000);
			long hour = (durationTime - day * 24 * 60 * 60 * 1000)
					/ (60 * 60 * 1000);
			long minute = (durationTime - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000)
					/ (60 * 1000);
			long millis = (durationTime - day * 24 * 60 * 60 * 1000 - hour * 60
					* 60 * 1000 - minute * 60 * 1000) / 1000;
			if (day != 0) {
				result.append(day).append("天");
			}
			if (hour != 0) {
				result.append(hour).append("时");
			}
			if (minute != 0) {
				result.append(minute).append("分");
			}
			if (millis != 0) {
				result.append(millis).append("秒");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 例如将时间"Tue, 26 Feb 2013 09:26:57 GMT"转化为日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date forMateDate(String dateStr) {
		Date date=null;
		try {
			String pattern = "EEE, dd MMM yyyy HH:mm:ss z";
			SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
		    date = format.parse(dateStr);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return date;
	}
}
