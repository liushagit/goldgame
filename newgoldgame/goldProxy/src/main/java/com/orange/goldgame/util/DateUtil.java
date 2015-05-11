package com.orange.goldgame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final long ONE_DAY = 86400L;
	public static final long ONE_HOUR = 3600;
	public static final long ONE_MINUTE = 60;
	public static final long ONE_MILLISECOND = 1000L;

	public static String distanceOfTimeInWords(long fromTime, long toTime, String format) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), format, 7);
	}

	public static String distanceOfTimeInWords(long fromTime, long toTime, String format, int days) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), format, days);
	}

	public static String distanceOfTimeInWords(long fromTime, long toTime, int days) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), "MM-dd HH:mm", days);
	}

	public static String distanceOfTimeInWords(long fromTime, long toTime) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), "MM-dd HH:mm", 7);
	}

	public static String distanceOfTimeInWords(Date fromTime, Date toTime, int days) {
		return distanceOfTimeInWords(fromTime, toTime, "MM-dd HH:mm", days);
	}

	public static String distanceOfTimeInWords(Date fromTime, Date toTime, String format) {
		return distanceOfTimeInWords(fromTime, toTime, format, 7);
	}

	public static String distanceOfTimeInWords(Date fromTime, Date toTime) {
		return distanceOfTimeInWords(fromTime, toTime, "MM-dd HH:mm", 7);
	}

	public static String dateForString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 截止时间时间到起始时间间隔的时间描述
	 * 
	 * @param fromTime
	 *            起始时间
	 * @param toTime
	 *            截止时间
	 * @param format
	 *            格式化
	 * @param days
	 *            超过此天数，将按format格式化显示实际时间
	 * @return
	 */
	public static String distanceOfTimeInWords(Date fromTime, Date toTime, String format, int days) {
		long distanceInMinutes = (toTime.getTime() - fromTime.getTime()) / 60000;
		String message = "";
		if (distanceInMinutes == 0) {
			message = "几秒钟前";
		} else if (distanceInMinutes >= 1 && distanceInMinutes < 60) {
			message = distanceInMinutes + "分钟前";
		} else if (distanceInMinutes >= 60 && distanceInMinutes < 1440) {
			message = (distanceInMinutes / 60) + "小时前";
		} else if (distanceInMinutes >= 1440 && distanceInMinutes <= (1440 * days)) {
			message = (distanceInMinutes / 1440) + "天前";
		} else {
			message = new SimpleDateFormat(format).format(fromTime);
		}
		return message;
	}

	public static boolean isInHours(String begin,String end){
		String dayDesc = getDateDesc();
		begin = dayDesc + begin;
		end = dayDesc + end;
		
		return isInDayHours(begin, end);
	}
	
	private static boolean isInDayHours(String begin,String end){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date begDate = sf.parse(begin);
			Date endDate = sf.parse(end);
			long now = System.currentTimeMillis() / 1000L;
			long st = begDate.getTime() / 1000L;
			long et = endDate.getTime() / 1000L;
			return (now >= st && now <= et) ? true : false;
		} catch (ParseException e) {
		}
		return false;
		
	}
	/**
	 * 当前日期是否在起止范围内
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isInDateRange(String start, String end) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		try {
			return isInDateRange(sf.parse(start), sf.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 当前日期是否在起止范围内
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isInDateRange(Date start, Date end) {
		long now = System.currentTimeMillis() / 1000L;
		long st = start.getTime() / 1000L;
		long et = end.getTime() / 1000L + ONE_DAY;
		return (now >= st && now <= et) ? true : false;
	}
	
	/**
	 * timeSec是否在指定时间范围内
	 * @param start
	 * @param end
	 * @param timeSec   指定的时间
	 * @return
	 */
	public static boolean isTimeDateRange(String start, String end, int timeSec) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			long st = sf.parse(start).getTime() / 1000L;
			long et = sf.parse(end).getTime() / 1000L + ONE_DAY;
			return (timeSec >= st && timeSec <= et) ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean startTime(String start){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return isStart(sf.parse(start));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 是否在规定时间之后
	 * @param start
	 * @return
	 */
	public static boolean isStartTime(String start) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return isStart(sf.parse(start));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isStart(Date start) {
		long now = System.currentTimeMillis() / 1000L;
		long st = start.getTime() / 1000L;
		return (now >= st ) ? true : false;
	}
	/**
	 * 当前时间秒
	 * 
	 * @return 当前时间秒
	 */
	public static int now() {
		return (int) (System.currentTimeMillis() / ONE_MILLISECOND);
	}

	/**
	 * 是否是同一天
	 * @param time1		ms
	 * @param time2		ms
	 * @return
	 */
	public static boolean isSameDay(long time1, long time2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(time1);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(time2);
		
		if ( calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) ) {
			return true;
		}
		return false;
	}
	
	public static String getTimeDesc(int timeSec) {
		if ( timeSec <= 0 ) {
			return "0秒";
		}
		int oneDay = 60 * 60 * 24;
		int days = timeSec / oneDay;
		timeSec -= days * oneDay;
		int hours = timeSec / 3600;
		timeSec -= hours * 3600;
		int min = timeSec / 60;
		timeSec -= min * 60;
		return days + "天" + hours + "小时" + min + "分钟" + timeSec + "秒";
	}
	
	/**
	 * 间隔实际天数
	 * @param millis1
	 * @param millis2
	 * @return
	 */
	public static int getIntervalDays(long millisMin, long millisMax) {
		Calendar minCalendar = Calendar.getInstance();
		Calendar maxCalendar = Calendar.getInstance();
		minCalendar.setTimeInMillis(millisMin);
		maxCalendar.setTimeInMillis(millisMax);
		if ( millisMin > millisMax ) {
			minCalendar.setTimeInMillis(millisMax);
			maxCalendar.setTimeInMillis(millisMin);
		}
		int intervalYear = maxCalendar.get(Calendar.YEAR) - minCalendar.get(Calendar.YEAR);
		int intervalDay = maxCalendar.get(Calendar.DAY_OF_YEAR) - minCalendar.get(Calendar.DAY_OF_YEAR);
		for ( int i=0; i<intervalYear; i++ ) {
			minCalendar.set(Calendar.YEAR, minCalendar.get(Calendar.YEAR) + 1);
			intervalDay += minCalendar.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return intervalDay;
	}
	
	public static String getDateDesc(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static void main(String[] args) {
		System.out.println(isInHours("161100", "161150"));
	}
	public static String getDateDesc(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String day = sf.format(new Date());
		return day;
	}
	public static String getDateDesc(Date date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String day = sf.format(date);
		return day;
	}
	public static String getDetailDate(Date date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String day = sf.format(date);
		return day;
	}
	
	public static Date getDetailTime(String date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDate(String dateStr){
	    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	    try {
            return sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
	    return null;
	}

	public static Date getNowDate() {
		return new Date();
	}
	
	public static boolean isInTimeRange(Date startTime,Date endTime){
		long now = System.currentTimeMillis();
		long start = startTime.getTime();
		long end = endTime.getTime();
		if(now >= start && now <= end){
			return true;
		}
		return false;
	}
}
