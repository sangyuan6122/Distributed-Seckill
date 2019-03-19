package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemLog;
import com.jecp.sysmanage.vo.TawSystemLogQueryVO;

public interface TawSystemLogDao extends BaseDao<TawSystemLog>{
	/**
	 * 查询
	 * @param tawSystemLog
	 * @param beginIndex
	 * @param size
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<TawSystemLog> query(TawSystemLogQueryVO vo,Integer beginIndex,
			Integer size,String[] sort, String[] order);
	/**
	 * 查询总量
	 * @param vo
	 * @return
	 */
	public Long queryCount(TawSystemLogQueryVO vo);
}
