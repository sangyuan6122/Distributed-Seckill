package com.jecp.sysmanage.service.impl;

import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.util.JsonUtil;
import com.jecp.sysmanage.dao.TawSystemCookieDao;
import com.jecp.sysmanage.dao.TawSystemUserDao;
import com.jecp.sysmanage.model.TawSystemCookie;
import com.jecp.sysmanage.service.TawSystemCookieService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("tawSystemCookieService")
public class TawSystemCookieServiceImpl extends BaseServiceImpl<TawSystemCookie> implements TawSystemCookieService {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	private final static long expiredTime = 60 * 2;// 2小时
	private final static String cookiePrefix = "com.jecp.sysmanage.model.TawSystemCookie:";// cookie存储key前缀
	@Autowired
	private TawSystemCookieDao tawSystemCookieDao;
	@Autowired
	private TawSystemUserDao tawSystemUserDao;
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public TawSystemCookie getCookie(String cookieid) {
		cookieid = cookiePrefix + cookieid;
		Object cacheObj = LocalCache.get(cookieid);
		if (cacheObj != null) {
			return (TawSystemCookie) cacheObj;
		}
		BoundValueOperations bvo = redisTemplate.boundValueOps(cookieid);
		String cookie = (String) bvo.get();
		if (cookie != null) {
			JsonUtil jsonUtil = new JsonUtil();
			JSONObject jSONObject = JSONObject.fromObject(cookie, jsonUtil.getJsonConfig());
			TawSystemCookie tawSystemCookie = (TawSystemCookie) JSONObject.toBean(jSONObject, TawSystemCookie.class);
			tawSystemCookie.setLastoperatetime(new Timestamp(System.currentTimeMillis()));
			cookie = JSONObject.fromObject(tawSystemCookie, jsonUtil.getJsonConfig()).toString();
			bvo.set(cookie, expiredTime, TimeUnit.MINUTES);
			LocalCache.put(cookieid, tawSystemCookie, expiredTime, TimeUnit.MINUTES);
			return tawSystemCookie;
		} else {
			return null;
		}
	}

	@Override
	public void addToCache(TawSystemCookie cookie) {
		String cookieid = cookiePrefix + cookie.getCookieid();
		JSONObject jSONObject = JSONObject.fromObject(cookie, new JsonUtil().getJsonConfig());
		BoundValueOperations bvo = redisTemplate.boundValueOps(cookieid);
		bvo.set(jSONObject.toString(), expiredTime, TimeUnit.MINUTES);
		LocalCache.put(cookieid, cookie, expiredTime, TimeUnit.MINUTES);
	}

	@Override
	public String online() {
		Set<String> keys = redisTemplate.keys(cookiePrefix + "*");
		JSONArray jSONArray = new JSONArray();
		for (String key : keys) {
			String json = redisTemplate.opsForValue().get(key);
			jSONArray.add(json);
		}
		return jSONArray.toString();
	}

	@Override
	public int onlineTotal() {
		Set<String> keys = redisTemplate.keys(cookiePrefix + "*");
		return keys.size();
	}

	@Override
	public String removeCookie(String cookieid) {
		JSONObject jSONObject = new JSONObject();
		try {
			redisTemplate.delete(cookiePrefix + cookieid);
			LocalCache.remove(cookiePrefix + cookieid);
			jSONObject.accumulate("status", "1");
		} catch (Exception e) {
			jSONObject.accumulate("status", "0");
			log.error("", e);
		}
		return jSONObject.toString();
	}

	@Override
	public void delAllUserCookie() {
		redisTemplate.delete(redisTemplate.keys(cookiePrefix + "*"));	
		LocalCache.fuzzyRemove(cookiePrefix);
	}

	/* set、get */
	@Override
	@Resource(name = "tawSystemCookieDao")
	public void setBasedao(BaseDao<TawSystemCookie> basedao) {
		super.setBasedao(basedao);
	}

}
