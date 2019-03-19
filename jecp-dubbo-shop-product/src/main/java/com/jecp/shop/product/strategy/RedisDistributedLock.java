package com.jecp.shop.product.strategy;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jecp.common.cache.rediscache.util.JedisUtil;

import redis.clients.jedis.Jedis;

/**
 * @Title Redis分布式锁
 * @author WWT
 * @date 2018年7月18日
 */
@Component
public class RedisDistributedLock {
	private static final Logger log = LoggerFactory.getLogger(JedisUtil.class);
	private static final Long SUCCESS = 1L;
	private static final String SCRIPT_UNLOCK = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
	private static final String SCRIPT_UNLOCK_SHA1 = DigestUtils.sha1Hex(SCRIPT_UNLOCK);
	@Autowired
	private JedisUtil jedisUtil;
	@PostConstruct
	private void init() {
		Jedis jedis = jedisUtil.getJedis();
		try {
			jedis.scriptLoad(SCRIPT_UNLOCK);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			JedisUtil.returnResource(jedis);
		}
	}

	/**
	 * 获得分布式锁成功为止
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public boolean lock(String key, String value, int expireTime) {
		Jedis jedis = JedisUtil.getJedis();
		try {
			while (true) {
				String result = jedis.set(key, value, "nx", "ex", expireTime);
				if ("OK".equals(result)) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		} finally {
			JedisUtil.returnResource(jedis);
		}
	}

	/**
	 * 获得分布式锁,可设置超时时间
	 * @param key
	 * @param value
	 * @param timeout
	 * @param expireTime
	 * @return
	 */
	public boolean tryLock(String key, String value, int timeout, int expireTime) {	
		final long endTime = System.currentTimeMillis() + timeout;
		Jedis jedis = JedisUtil.getJedis();
		boolean success = false;
		try {
			while (System.currentTimeMillis() <= endTime) {
				String result = jedis.set(key, value, "nx", "px", expireTime);
				if ("OK".equals(result)) {
					success = true;
					break;
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			JedisUtil.returnResource(jedis);
		}
		return success;
	}

	/**
	 * if redis.call("get",KEYS[1]) == ARGV[1] then 
	 *    return redis.call("del",KEYS[1])
	 * else 
	 *    return 0 
	 * end 
	 * 防止持有过期锁的客户端误删现有锁的情况出现 
	 * 用lua脚本解锁
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean unLock(String key, String value) {
		Jedis jedis = JedisUtil.getJedis();
		boolean success = false;
		try {
			Object result = jedis.evalsha(SCRIPT_UNLOCK_SHA1, 1, key, value);
			if (result != null && SUCCESS.equals(result)) {
				success = true;
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			JedisUtil.returnResource(jedis);
		}
		return success;
	}

}
