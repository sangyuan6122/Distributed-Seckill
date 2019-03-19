package com.jecp.sysmanage.service;

import com.jecp.base.page.PageQuery;
import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemLog;
import com.jecp.sysmanage.vo.TawSystemLogQueryVO;


public interface TawSystemLogService extends BaseService<TawSystemLog>{
	/**
	 * 查询日志
	 * @param vo
	 * @param paginationQuery
	 * @return
	 */
	public String query(TawSystemLogQueryVO vo,PageQuery paginationQuery) ;

}
