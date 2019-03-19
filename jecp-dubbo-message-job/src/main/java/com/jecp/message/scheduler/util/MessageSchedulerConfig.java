package com.jecp.message.scheduler.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @功能 可靠消息恢复策略配置
 * @作者 WWT
 * @修改时间 2018年6月17日
 */
@Component
public class MessageSchedulerConfig {
	
	private static int timeOut;
	private static int recoveryMaxtimes;
	private static int recoveryFirst;
	private static int recoverySecond;
	private static int recoveryThree;
	private static int recoveryFour;
	private static int recoveryFive;

	public static int getTimeOut() {
		return timeOut;
	}

	@Value("${message.timeout}")
	public void setTimeOut(int timeOut) {
		MessageSchedulerConfig.timeOut = timeOut;
	}

	public static int getRecoveryMaxtimes() {
		return recoveryMaxtimes;
	}

	@Value("${message.recovery.maxtimes}")
	public void setRecoveryMaxtimes(int recoveryMaxtimes) {
		MessageSchedulerConfig.recoveryMaxtimes = recoveryMaxtimes;
	}

	public static int getRecoveryFirst() {
		return recoveryFirst;
	}

	@Value("${message.recovery.first}")
	public void setRecoveryFirst(int recoveryFirst) {
		MessageSchedulerConfig.recoveryFirst = recoveryFirst;
	}

	public static int getRecoverySecond() {
		return recoverySecond;
	}

	@Value("${message.recovery.second}")
	public void setRecoverySecond(int recoverySecond) {
		MessageSchedulerConfig.recoverySecond = recoverySecond;
	}

	public static int getRecoveryThree() {
		return recoveryThree;
	}

	@Value("${message.recovery.three}")
	public void setRecoveryThree(int recoveryThree) {
		MessageSchedulerConfig.recoveryThree = recoveryThree;
	}

	public static int getRecoveryFour() {
		return recoveryFour;
	}

	@Value("${message.recovery.four}")
	public void setRecoveryFour(int recoveryFour) {
		MessageSchedulerConfig.recoveryFour = recoveryFour;
	}

	public static int getRecoveryFive() {
		return recoveryFive;
	}

	@Value("${message.recovery.five}")
	public void setRecoveryFive(int recoveryFive) {
		MessageSchedulerConfig.recoveryFive = recoveryFive;
	}		
	
}
