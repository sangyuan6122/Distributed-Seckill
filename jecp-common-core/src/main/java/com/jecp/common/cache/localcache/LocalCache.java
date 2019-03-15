package com.jecp.common.cache.localcache;

import java.lang.ref.SoftReference;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jecp.sysmanage.model.TawSystemUser;

/**
 * @功能 本地缓存
 * @作者 WWT
 * @修改时间 2018年4月21日
 */
public class LocalCache {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	
	private static final ConcurrentHashMap<String, SoftReference<LocalCacheElement>> cacheObjectMap = new ConcurrentHashMap<>();
	private static final DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();
	private static final Thread thread;

	private LocalCache() {
	}

	static {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				log.info("======>缓存清理线程启动");
				expiredClear();
				log.info("======>缓存清理线程结束");
			}
		});
		thread.setDaemon(true);
		thread.setName("ClearCache");
		thread.start();
	}

	/**
	 * 添加永久缓存
	 * 
	 * @param key
	 * @param obj(建议调用完执行obj=null)
	 */
	public static void put(String key, Object obj) {		
		SoftReference<LocalCacheElement> softReference = new SoftReference<>(new LocalCacheElement(key, obj));
		obj = null;
		cacheObjectMap.put(key, softReference);
	}

	/**
	 * 添加有期限缓存
	 * 
	 * @param key
	 * @param obj(建议调用完执行obj=null)
	 * @param effectiveTime
	 * @param timeUnit
	 */
	public static void put(String key, Object obj, long effectiveTime, TimeUnit timeUnit) {		
		long nanoSeconds = TimeUnit.NANOSECONDS.convert(effectiveTime, timeUnit);		
		delayQueue.put(new DelayElement<String>(key, nanoSeconds));
		SoftReference<LocalCacheElement> softReference = new SoftReference<>(new LocalCacheElement(key, obj,effectiveTime, timeUnit));
		obj = null;
		cacheObjectMap.put(key, softReference);
	}
//	/**
//	 * 添加缓存
//	 * @param localCacheElement
//	 */
//	public static void put(LocalCacheElement localCacheElement) {
//		String key=localCacheElement.getKey();
//		SoftReference<LocalCacheElement> softReference;
//		if(localCacheElement.getTimeUnit()!=null) {
//			long nanoSeconds = TimeUnit.NANOSECONDS.convert(localCacheElement.effectiveTime(), localCacheElement.getTimeUnit());		
//			delayQueue.put(new DelayElement<String>(key, nanoSeconds));
//			softReference = new SoftReference<>(new LocalCacheElement(key, localCacheElement.getCacheObj(),
//					localCacheElement.effectiveTime(), localCacheElement.getTimeUnit()));
//		}else {
//			softReference = new SoftReference<>(new LocalCacheElement(key, localCacheElement.getCacheObj()));
//		}
//		localCacheElement = null;
//		cacheObjectMap.put(key, softReference);
//	}
	/**
	 * 获得缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		SoftReference<LocalCacheElement> softReference=cacheObjectMap.get(key);
		if(softReference==null) {
			log.trace("缓存不存在,key:{}",key);
			return null;
		}
		LocalCacheElement localCacheElement=softReference.get();
		if(localCacheElement==null) {
			log.trace("缓存已被jvm回收,key:{}",key);
			return null;
		}
		return localCacheElement.getCacheObj();
	}
	/**
	 * 是否包含指定Key的缓存
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key) {
		return cacheObjectMap.containsKey(key);
	}
	/**
	 * 删除指定缓存
	 * @param key
	 */
	public static void remove(String key) {
		cacheObjectMap.remove(key);
		delayQueue.remove(new DelayElement(key,-1L));
	}
	/**
	 * 根据Key模糊删除
	 * @param fuzzyKey
	 */
	public static void fuzzyRemove(String fuzzyKey) {
		Set<String> keys=cacheObjectMap.keySet();
		for(String key:keys) {
			if(key.indexOf(fuzzyKey)>-1) {
				remove(key);
			}
		}
	}
	/**
	 * 删除所有缓存
	 */
	public static void removeAll() {
		cacheObjectMap.clear();
		delayQueue.clear();
	}
	/**
	 * 清理过期缓存
	 */
	private static void expiredClear() {
		while (true) {
			try {
				DelayElement<String> delayElement = delayQueue.take();	
				log.trace("清理缓存:{}",delayElement);
				if (delayElement != null) {
					cacheObjectMap.remove(delayElement.getElement());					
				}
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 获取缓存清理线程状态
	 * @return
	 */
	public static Thread.State clearThreadState() {
		return thread.getState();
	}
	/**
	 * 统计
	 */
	public static void statistics() {
		if(log.isInfoEnabled()) {
			log.info("缓存数量:{}",cacheObjectMap.size());
		}		
		for(Entry<String, SoftReference<LocalCacheElement>> entry : cacheObjectMap.entrySet()){
			String key=entry.getKey();
			LocalCacheElement cacheElement=entry.getValue().get();
			if(log.isInfoEnabled()) {
				log.info("Key:{},创建时间:{},过期时间:{},命中次数:{},缓存对象:{}", key,cacheElement.getCreateTime(),cacheElement.getEffectiveTime(),cacheElement.getHitCount(),cacheElement.getCacheObj());
			}			
		}
	}
	public static void main(String[] args) throws InterruptedException {
		TawSystemUser user1=new TawSystemUser("1","a");
		TawSystemUser user2=new TawSystemUser("2","b");

		LocalCache.put("1", user2,2L,TimeUnit.SECONDS);
		LocalCache.put("2", user2,5L,TimeUnit.SECONDS);
		LocalCache.put("3", user2);
		user1=null;
		user2=null;
		LocalCache.get("1");
		LocalCache.get("1");
		LocalCache.get("3");
		statistics();
//		System.out.println(delayQueue.contains(new DelayElement("1",-1L)));
//		delayQueue.remove(new DelayElement("1",-1L));
//		System.out.println(user1+":"+user2);
//		System.out.println( ((TawSystemUser)LocalCache.get("1")).getUserid());
		
		while(1==1) {
			TimeUnit.SECONDS.sleep(1);
			System.out.println(cacheObjectMap.size());
			System.out.println( clearThreadState());
		}
	}

}
