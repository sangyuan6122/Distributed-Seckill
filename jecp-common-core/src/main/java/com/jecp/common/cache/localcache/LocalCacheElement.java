package com.jecp.common.cache.localcache;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class LocalCacheElement {
	private String key;
	/* 缓存对象 */
	private Object cacheObj;//
	/* 有效时间 */
	private long effectiveTime;//
	/* 时间单位 */
	private TimeUnit timeUnit;
	/* 创建时间 */
	private long createTime;
	/* 命中次数 */
	private final LongAdder hitCount = new LongAdder();

	public LocalCacheElement(String key, Object cacheObj, long effectiveTime, TimeUnit timeUnit) {
		this.key = key;
		this.cacheObj = cacheObj;
		this.effectiveTime = effectiveTime;
		this.timeUnit = timeUnit;
		this.createTime = System.currentTimeMillis();
	}

	public LocalCacheElement(String key, Object cacheObj) {
		this(key, cacheObj, -1L, null);
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public LongAdder getHitCount() {
		return hitCount;
	}

	public Object getCacheObj() {
		hitCount.increment();
		return this.cacheObj;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public void setCacheObj(Object cacheObj) {
		this.cacheObj = cacheObj;
	}

	@Override
	public int hashCode() {
		if (key == null) {
			return "".hashCode();
		}
		return key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (!(obj instanceof LocalCacheElement))) {
			return false;
		}
		LocalCacheElement element = (LocalCacheElement) obj;
		if ((key == null) || (element.getKey() == null)) {
			return false;
		}
		return key.equals(element.getKey());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static void main(String[] args) {
		// CacheElement c = new CacheElement("3","a");
		LocalCacheElement c1 = new LocalCacheElement("2", "", 1, TimeUnit.SECONDS);
		System.out.println(c1.getCacheObj());
		System.out.println(c1.getCreateTime());
		System.out.println(c1.getKey());

	}
}
