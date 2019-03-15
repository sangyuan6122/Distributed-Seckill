package com.jecp.common.cache.rediscache.aspect;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jecp.common.cache.rediscache.annotation.RedisCache;
import com.jecp.common.cache.rediscache.annotation.RedisEvict;
import com.jecp.common.cache.rediscache.util.JedisUtil;
import com.jecp.common.cache.rediscache.util.RedisDataType;
import com.jecp.common.cache.rediscache.util.RedisReturnType;


@Aspect
@Component
public class RedisCacheAspect {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	
	//分隔符 生成key 格式为 类全类名|方法名|参数所属类全类名 
    private static final String DELIMITER = ":";    
    
//    @Autowired
//    private RedisTemplate redisTemplate;
    
    /**
     * 切入点,用于查询缓存
     */
    @Pointcut("@annotation(com.jecp.common.cache.rediscache.annotation.RedisCache)")
    public void redisCacheAspect() {
    	System.out.println("切入点redisCacheAspect");
    }

    /**
     * 切入点，用户删除缓存
     */
    @Pointcut("@annotation(com.jecp.common.cache.rediscache.annotation.RedisEvict)")
    public void redisCacheEvict() {
    	System.out.println("切入点redisCacheEvict");
    }

    
    
    @Around("redisCacheAspect()")
    public Object cache(ProceedingJoinPoint joinPoint){
    	// 得到类名、方法名和参数
        String clazzName = joinPoint.getTarget().getClass().getName();        
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        // 根据类名、方法名和参数生成Key        
        log.trace("key参数: " + clazzName + "." + methodName);
        //System.out.println("key参数: " + clazzName + "." + methodName);
        String key = getKey(clazzName, methodName, args);
        if (log.isInfoEnabled()) {
            log.trace("生成key: " + key);
        }
        // 得到被代理的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // 得到被代理的方法上的注解
        RedisCache rAnnotation=method.getAnnotation(RedisCache.class);
        Class model=rAnnotation.type();
        int expiredTime=rAnnotation.expiredTime();//过期时间
        RedisDataType dataType=rAnnotation.dataType();  
        RedisReturnType returnType=rAnnotation.returnType();//返回类型
        
        // 得到被代理方法的返回值类型
        //Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();        
        // result是方法的最终返回结果
        Object result = queryCache(dataType,model,returnType,key,expiredTime);
        try {
            if (null == result) {
            	log.trace("缓存未命中");                
                // 调用数据库查询方法
                result = joinPoint.proceed(args);
                putCache(dataType,returnType,model.getName(),key,result);
            } else {                
            	log.trace("缓存命中, result = " + result);
                return result;
            }
        } catch (Throwable e) {
            log.error("解析异常",e);
        }
        return result;

    }
    @Around("redisCacheEvict()")
    public Object evictCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // 得到被代理的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 得到被代理的方法上的注解
        Class[] modelType = method.getAnnotation(RedisEvict.class).type();
        for(int i=0;modelType!=null&&i<modelType.length;i++){
        	log.info("清空缓存 = " + modelType[i].getName());
        	// 清除对应缓存
        	JedisUtil.del(modelType[i].getName());
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }

    /**
     *根据类名、方法名和参数生成Key
     *@param clazzName
     *@param methodName
     *@param args
     *@return key格式：全类名|方法名｜参数类型
     *      
     */
    private String getKey(String clazzName, String methodName, Object[] args) {
        StringBuilder key = new StringBuilder(clazzName);
        key.append(DELIMITER);
        key.append(methodName);
        key.append(DELIMITER);

        for (int i=0;i<args.length;i++) {
        	if(args[i]==null){
        		key.append("null");
        	}else{
        		key.append(args[i].toString());
        	}   
        	if(i!=args.length-1){
        		key.append(DELIMITER);
        	}
            
        }

        return key.toString();
    }
    /**
     * 查询缓存
     * 1、如果未缓存则返回null，否则返回缓存数据(Object)
     * @param dataType(redis数据类型)
     * @param modelType(建议model或vo类)
     * @param returnType(返回类型)
     * @param key(主键)
     * @return
     */
    public Object queryCache(RedisDataType dataType,Class modelType,RedisReturnType returnType,String key,int expiredTime){
    	Object json;
    	JedisUtil.expire(key, expiredTime);
    	switch (dataType){
	    	case Hashes:
	    		json=JedisUtil.hget(modelType.getName(), key);
	    		if(json==null){
	    			return null;
	    		}
	    		if("List".equals(returnType.name())){
	    			return JSONObject.parseArray(json.toString(), modelType);
	    		}else if("Object".equals(returnType.name()) ){
	    			return JSON.parseObject(json.toString(),modelType);
	    		}else{
	    			return json.toString();
	    		}
	    	case String:
	    		json=JedisUtil.get(key);
	    		if(json==null){
	    			return null;
	    		}else{
	    			JedisUtil.expire(key, expiredTime);
	    			return JSON.parseObject(json.toString(),modelType);
	    		}	    		
	    	case Lists:
	    	case Sets:
	    	case Sortedsets:
	    }
    	return null;
    }
    /**
     * 缓存数据
     * @param dataType
     * @param clazzName
     * @param key
     * @param obj
     */
    public void putCache(RedisDataType dataType,RedisReturnType returnType,String clazzName,String key,Object obj){
    	String json = null;
    	switch (returnType){
    		case List:
    			json=JSON.toJSONString(obj);
    			break;
    		case Object:
    			json=JSON.toJSONString(obj);
    			break;
    		case String:
    			json=obj.toString();
    			break;
    	}
    	switch (dataType){
	    	case Hashes:
	    		JedisUtil.hset(clazzName, key, json);
	    	case String:
	    		JedisUtil.set(key, json);	    		
	    	case Lists:
	    	case Sets:
	    	case Sortedsets:
    	}
    }

}
