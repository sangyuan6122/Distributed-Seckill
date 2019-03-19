package com.jecp.message.scheduler.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @功能 可靠消息任务时间工具
 * @作者 WWT
 * @修改时间 2018年6月17日
 */
public class SchedulerTimeUtil {
	/**
	 * 获得恢复策略执行时间
	 * 
	 * @param timeout
	 * @return
	 */
	public static Date getCheckTime(int timeout) {
		return new Date(System.currentTimeMillis() - timeout * 1000);
	}
	
	/**
	 * 时间增加秒数
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date dateAddSeconds(Date date,int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}
	/**
	 * 根据重复次数获得对应秒数
	 * @param times
	 * @return
	 */
	public static int getIntervalSeconds(int times) {
		switch (times) {
		case 0:
			return MessageSchedulerConfig.getRecoveryFirst();
		case 1:
			return MessageSchedulerConfig.getRecoverySecond();
		case 2:
			return MessageSchedulerConfig.getRecoveryThree();
		case 3:
			return MessageSchedulerConfig.getRecoveryFour();
		case 4:
			MessageSchedulerConfig.getRecoveryFive();
		}
		return MessageSchedulerConfig.getRecoveryFive();
	}
}
