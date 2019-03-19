package com.jecp.sysmanage.service;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemDicttype;

public interface TawSystemDicttypeService extends BaseService<TawSystemDicttype>{
	/**
	 * 根据父节点ID获得子节点
	 * @param pid
	 * @return
	 */
	public String getDictTree(String pid);
	/**
	 * 删除当前父节点
	 * @param dictid
	 */
	public int delDictTree(Integer dictid);
	/**
	 * 查询字典是否重复
	 * @param tawSystemDicttype
	 * @return
	 */
	public int isRepeat(String parentdictid,String dictname,String dictid);
	/**
	 * 根据id查询
	 * @param dictid
	 * @return
	 */
	public String get(Integer dictid);
	/**
	 * 获得字典树
	 * @return
	 */
	public String getTree(String parentDictid);
	
	public String getToJson(String parentDictid);
	
	public String getCodeToJson(String parentDictid);
}
