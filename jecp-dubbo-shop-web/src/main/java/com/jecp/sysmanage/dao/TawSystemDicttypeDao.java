package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemDicttype;

public interface TawSystemDicttypeDao extends BaseDao<TawSystemDicttype>{
	/**
	 * 根据父节点ID获得子节点
	 * @param pid
	 * @return
	 */
	public List<TawSystemDicttype> getDictTree(String pid);
	/**
	 * 删除当前父节点
	 * @param dictid
	 */
	public void delDictTree(Integer dictid);
	/**
	 * 查询字典是否重复
	 * @param tawSystemDicttype
	 * @return
	 */
	public int isRepeat(String parentdictid,String dictname,String dictid);
}
