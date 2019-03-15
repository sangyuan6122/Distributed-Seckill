package com.jecp.trade.record.test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.util.SnowflakeIdWorker;
import com.jecp.trade.record.api.TradeRecordApi;
import com.jecp.trade.record.dto.TradeRecordListDTO;
import com.jecp.trade.record.model.TradeRecord;

public class TradeOrderTest {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        
        TradeRecordApi tradeRecordApi = (TradeRecordApi)context.getBean("tradeRecordApiImpl"); // 获取远程服务代理
        
//        for(int i=0;i<99;i++) {
//        	TradeRecordInputDTO dto=new TradeRecordInputDTO("001","002", 50d,"","2018071100005",
//            		Long.valueOf(i));            
//            tradeRecordApi.balancePayMent(dto); // 执行远程方法
//        }
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(200);
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
        PageQuery pageQuery=new PageQuery(1,10000);
        PageBean<TradeRecordListDTO> pageBean= tradeRecordApi.list("admin", pageQuery);
        Iterator<TradeRecordListDTO> itr=pageBean.getPageData().iterator();
        long start=System.currentTimeMillis();
        for(int i=0;i<10000;i++) {
        	fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					 
		            tradeRecordApi.balancePayMent(Long.valueOf(itr.next().getGtid())); // 执行远程方法
				}        		
        	});	
        }
        fixedThreadPool.shutdown();
        while(true) {
        	if(fixedThreadPool.isTerminated()) {
        		LocalCache.statistics();
        		long time=System.currentTimeMillis()-start;
        		System.out.println(time/1000+" 秒");        		
        		break;
        	}
        }
    }
}
