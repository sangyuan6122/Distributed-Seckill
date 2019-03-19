package com.jecp.message.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Title 消息状态统计DTO
 * @author WWT
 * @date 2018年5月9日
 */
public class MessageStatusStatisticsDTO implements Serializable {
	private static final long serialVersionUID = -4509188318173987993L;
	/* 待确认数量 */
	private long unfirmed = 0L;
	/* 待消费数量 */
	private long unconsumed = 0L;
	/* 已死亡数量 */
	private long alreadyDead = 0L;

	public long getUnfirmed() {
		return unfirmed;
	}

	public void setUnfirmed(long unfirmed) {
		this.unfirmed = unfirmed;
	}

	public long getUnconsumed() {
		return unconsumed;
	}

	public void setUnconsumed(long unconsumed) {
		this.unconsumed = unconsumed;
	}

	public long getAlreadyDead() {
		return alreadyDead;
	}

	public void setAlreadyDead(long alreadyDead) {
		this.alreadyDead = alreadyDead;
	}


}
