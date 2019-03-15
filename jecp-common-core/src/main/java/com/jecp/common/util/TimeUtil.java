package com.jecp.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/**
	 * 获得当前时间yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getcuTime(){
		return sdf.format(Calendar.getInstance().getTime());
	}
	/**
	 * 获得当前时间yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getcuTimeBySSS(){
		return sdf1.format(Calendar.getInstance().getTime());
	}
	/**
	 * 获得当前年月yyyyMM
	 * @return
	 */
	public static String getYearAndMonth(){
		int year =Calendar.getInstance().get(Calendar.YEAR);
		int month =Calendar.getInstance().get(Calendar.MONTH)+1;
		return year+(month<10?"0"+month:month+"");
	}
	/**
	 * 日期类型转换为字符串
	 * @param time
	 * @return
	 */
	public static String timeToStr(Object time){
		if(time instanceof java.sql.Date || time instanceof java.util.Date
				||time instanceof java.sql.Timestamp){
			return sdf.format(time);
		}
		return time.toString();		
	}
	/**
	 * 字符串转换Timestamp
	 * @param dateStr
	 * @return
	 */
	public synchronized static Timestamp stringToTimestamp(String dateStr){
		Calendar calendar;
		try {	
			calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {			
			return null;			
		}
		return new Timestamp(calendar.getTimeInMillis());
	}
	public synchronized static Timestamp stringToTimestamp(SimpleDateFormat sdf,String dateStr){
		Calendar calendar;
		try {	
			calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {			
			return null;			
		}
		return new Timestamp(calendar.getTimeInMillis());
	}
	/**
	 * 获得本次检查时间点(当前5分钟)
	 * @return
	 */
	public static String getThisTime(){
		Calendar calendar;
		calendar=Calendar.getInstance();
		int minute = calendar.get(Calendar.MINUTE);
		int remainder=minute%5;
//		if (remainder==0)
//			remainder = 5;	
		calendar.add(Calendar.MINUTE, -remainder);
		return sdf2.format(calendar.getTime());
	}
	public static void main(String[] args) throws Exception {
	
		Calendar beginCalendar = Calendar.getInstance();		
		
		System.out.println(beginCalendar.getTime());
	}
}
