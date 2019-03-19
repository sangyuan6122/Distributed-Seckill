package com.jecp.message.scheduler.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.message.api.ReliableMessageApi;
import com.jecp.message.enums.MessageStatusEnum;
import com.jecp.message.model.ReliableMessage;
import com.jecp.message.scheduler.util.MessageSchedulerConfig;
import com.jecp.message.scheduler.util.SchedulerTimeUtil;

/**
 * @功能 消息重发任务
 * @作者 WWT
 * @修改时间 2018年6月17日
 */
public class MessageReSendJob{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	// 每页数量
	private static final Integer ROWS = 100;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private ReliableMessageApi reliableMessageApi;
	/**
	 * 消息重新发送给消费者
	 */
	public void reSend(){
		log.info("=========>{}开始执行消息重发任务", SchedulerTimeUtil.getCheckTime(0));
		try {
			String[] sort = { "last_update_time" };
			String[] order = { "asc" };
			PageQuery pageQuery;

			int page = 1;
			while (true) {
				log.info("开始查询第{}页确认超时数据", page);
				pageQuery = new PageQuery(page, ROWS, sort, order);
				String checkConfirmTime=SDF.format(SchedulerTimeUtil.getCheckTime(MessageSchedulerConfig.getTimeOut()));				
				PageBean<ReliableMessage> pageBean = reliableMessageApi.getExceptionMsg(MessageStatusEnum.UNCONSUMED,checkConfirmTime, pageQuery);
				if (pageBean.getPageData().size() == 0) {
					break;
				}
				List<ReliableMessage> list = pageBean.getPageData();
				for (ReliableMessage reliableMessage : list) {
					if (reliableMessage.getResendTimes() >= MessageSchedulerConfig.getRecoveryMaxtimes()) {
						reliableMessageApi.messageDead(reliableMessage.getMessageKey());
						continue;
					} 
					int intervalSeconds=SchedulerTimeUtil.getIntervalSeconds(reliableMessage.getResendTimes());
					Date reSendTime=SchedulerTimeUtil.dateAddSeconds(reliableMessage.getLastUpdateTime(), intervalSeconds);
					if (reSendTime.compareTo(new Date()) < 0) {						
						reliableMessageApi.reSendMessage(reliableMessage.getMessageKey());
					}					
				}
				page++;
			}
		} catch (Exception ex) {
			log.error("", ex);
		}

		log.info("=========>消息重发任务执行完成");
	}

}
