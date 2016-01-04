package pers.ksy.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	public static final String DATA_PATTERN = "yyyy-MM-dd";
	public static final String DATA_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = null;

	/**
	 * String转Date
	 * @param dateStr 时间字符串
	 * @param pattern 格式
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		return parseDate(dateStr, pattern, Locale.US);
	}
	
	/**
	 * String转Date
	 * @param dateStr 时间字符串
	 * @param pattern 格式
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern, Locale locale) {
		if (StringUtil.notEmpty(dateStr)) {
			try {
				sdf = new SimpleDateFormat(pattern, locale);
				return sdf.parse(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * String转Date格式：DATA_PATTERN(yyyy-MM-dd)
	 * @param dateStr 
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, DATA_PATTERN);
	}

	/**
	 * String转Date格式：DATA_TIME_PATTERN(yyyy-MM-dd HH:mm:ss)
	 * @param dateStr 
	 * @return
	 */
	public static Date parseDateTime(String dateStr) {
		return parseDate(dateStr, DATA_TIME_PATTERN);
	}

	/**
	 * 时间格式化(Date转String)
	 * @param date 时间
	 * @param pattern 格式
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (null != date) {
			try {
				sdf = new SimpleDateFormat(pattern);
				return sdf.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 时间格式化(Date转String) 格式：DATA_PATTERN(yyyy-MM-dd)
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DATA_PATTERN);
	}

	/**
	 * 时间格式化(Date转String) 格式：DATA_TIME_PATTERN(yyyy-MM-dd HH:mm:ss)
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DATA_TIME_PATTERN);
	}
	
	/**
	 * 生成当前时间的序列号格式：yyyyMMddHHmmssss
	 * @return 如2014050214063313
	 */
	public static String formatNowSerial(){
		return formatDate(new Date(), "yyyyMMddHHmmssss");
	}

	/**
	 * 日期之间相差月数
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 月数
	 */
	public static int differMonths(Date start, Date end) {
		int years = end.getYear() - start.getYear();
		int months = end.getMonth() - start.getMonth();
		return years * 12 + months;
	}
	
	public static String nowDateTime() {
		return formatDateTime(new Date());
	}

}
