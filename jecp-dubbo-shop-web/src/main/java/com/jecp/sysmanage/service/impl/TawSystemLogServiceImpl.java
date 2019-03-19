package com.jecp.sysmanage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemLogDao;
import com.jecp.sysmanage.model.TawSystemLog;
import com.jecp.sysmanage.service.TawSystemLogService;
import com.jecp.sysmanage.vo.TawSystemLogQueryVO;
@Service("tawSystemLogService")
public class TawSystemLogServiceImpl extends BaseServiceImpl<TawSystemLog> implements TawSystemLogService{
	@Resource
	private TawSystemLogDao tawSystemLogDao;

	@Override
	public String query(TawSystemLogQueryVO vo,PageQuery paginationQuery) {
		JSONObject jSONObject=new JSONObject();
		List<TawSystemLog> list=tawSystemLogDao.query(vo, paginationQuery.getPage(), paginationQuery.getRows(),
				paginationQuery.getSort(), paginationQuery.getOrder());
		Long total=tawSystemLogDao.queryCount(vo);
		jSONObject.put("total", total);
		jSONObject.put("rows", JSONArray.parseArray(JSON.toJSONString(list)));
		return jSONObject.toJSONString();
	}
	
	/*set、get*/
	@Override
	@Resource(name="tawSystemLogDao")
	public void setBasedao(BaseDao<TawSystemLog> basedao) {		
		super.setBasedao(basedao);
	}

	
}
