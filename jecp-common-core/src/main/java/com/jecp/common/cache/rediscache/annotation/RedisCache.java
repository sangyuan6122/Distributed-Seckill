package com.jecp.common.cache.rediscache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jecp.common.cache.rediscache.util.RedisDataType;
import com.jecp.common.cache.rediscache.util.RedisReturnType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisCache {
	Class type();//具体执行类
	RedisDataType dataType();//redis数据类型
	RedisReturnType returnType() default RedisReturnType.String;//返回类型
	int expiredTime() default 60*60;//过期时间
	
}
