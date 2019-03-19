package com.jecp.sysmanage.service;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemPrivMenu;

public interface TawSystemPrivMenuService extends BaseService<TawSystemPrivMenu>{
	/**
	 * 列表加载
	 * @param beginIndex
	 * @param size
	 * @return
	 */
	public String load(Integer beginIndex, Integer size);
	/**
	 * 添加菜单方案项
	 * @param tawSystemPrivMenu
	 * @return
	 */
	public String add(TawSystemPrivMenu tawSystemPrivMenu);
	/**
	 * 删除菜单方案
	 * @param id
	 */
	public String del(String id);
	/**
	 * 获得菜单方案单条记录
	 * @param id
	 * @return
	 */
	public String get(String id);
}
