package com.jecp.sysmanage.util;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jecp.common.cache.rediscache.annotation.RedisCache;
import com.jecp.common.cache.rediscache.util.RedisDataType;
import com.jecp.sysmanage.model.TawSystemCookie;

public class CookieMem {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	public static int count=0;
	public static Map<String,TawSystemCookie> session = new HashMap<String,TawSystemCookie>(); 
	/**
	 * 清除过期session
	 */
	public static void removeExpired(int chenkpoint) {
		if(count>chenkpoint){
			Long min =0L;		
			log.info("-------开始清除过期cookie("+CookieMem.session.size()+")-------");
			Iterator<Entry<String,TawSystemCookie>> it = CookieMem.session.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String,TawSystemCookie> entry=it.next();
				min=(new Timestamp(System.currentTimeMillis()).getTime()-entry.getValue().getLastoperatetime().getTime() )/1000/60;
				if(min>120){					
					it.remove();				
					log.info("key:"+entry.getValue().getCookieid());
					log.info("userid:"+entry.getValue().getUserid());
					log.info("ip:"+entry.getValue().getLoginip());
					log.info("lastoperatetime:"+entry.getValue().getLastoperatetime());
					log.info("------------------------");
				}
			}
//			for(Entry<String,TawSystemCookie> entry:CookieMem.session.entrySet().iterator()){
//				min=(new Timestamp(System.currentTimeMillis()).getTime()-entry.getValue().getLastoperatetime().getTime() )/1000/60;
//				if(min>120){					
//					session.remove(entry.getKey());				
//					log.info("key:"+entry.getValue().getCookieid());
//					log.info("userid:"+entry.getValue().getUserid());
//					log.info("ip:"+entry.getValue().getLoginip());
//					log.info("lastoperatetime:"+entry.getValue().getLastoperatetime());
//					log.info("------------------------");
//				}
//			}	
			CookieMem.count=0;
			log.info("---------cookie清除结束("+CookieMem.session.size()+")---------");
		}		
	}
	/**
	 * 验证session是否有效
	 * @param cookieid
	 * @param maxTimeOut
	 * @return
	 */
	@RedisCache(type=TawSystemCookie.class,dataType=RedisDataType.String,expiredTime=60*2)
	public static TawSystemCookie vaildCookie(String cookieid, int maxTimeOut) {		
		TawSystemCookie tawSystemCookie =session.get(cookieid);
		if(tawSystemCookie==null){
			return null;
		}else{
			Long min =(new Timestamp(System.currentTimeMillis()).getTime()-tawSystemCookie.getLastoperatetime().getTime() )/1000/60;
			if(min>maxTimeOut){
				return null;
			}
		}
		tawSystemCookie.setLastoperatetime(new Timestamp(System.currentTimeMillis()));
		session.put(cookieid, tawSystemCookie);		
		return tawSystemCookie;
	}
	
	
	 
}
