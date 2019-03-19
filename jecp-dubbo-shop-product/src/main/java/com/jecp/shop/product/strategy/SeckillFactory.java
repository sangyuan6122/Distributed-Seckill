package com.jecp.shop.product.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.jecp.shop.product.enums.SeckillStrategyEnum;

/**
 * @Title 秒杀策略工厂
 * @author WWT
 * @date 2018年7月18日
 */
public class SeckillFactory {

	public static SeckillStrategy getStrategy(SeckillStrategyEnum seckillStrategyEnum)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		String className = seckillStrategyEnum.getClazzName();
		Class clazz = Class.forName(className);
		Constructor constructor = clazz.getConstructor();
		SeckillStrategy seckillStrategy = (SeckillStrategy) constructor.newInstance();
		return seckillStrategy;
	}

}
