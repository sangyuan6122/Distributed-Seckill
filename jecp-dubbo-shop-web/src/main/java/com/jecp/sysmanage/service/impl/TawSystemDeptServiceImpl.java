package com.jecp.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.common.model.Tree;
import com.jecp.common.util.StatusEnum;
import com.jecp.sysmanage.dao.TawSystemDeptDao;
import com.jecp.sysmanage.model.TawSystemDept;
import com.jecp.sysmanage.service.TawSystemDeptService;
import com.jecp.sysmanage.util.SysCatchUtil;

@Service("tawSystemDeptService")
public class TawSystemDeptServiceImpl extends BaseServiceImpl<TawSystemDept> implements TawSystemDeptService {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemDeptDao tawSystemDeptDao;

	@Override
	public String addDept(TawSystemDept tawSystemDept) {	
		JSONObject jSONObject=new JSONObject();		
		Long deptid = tawSystemDeptDao.getNextDeptid(tawSystemDept.getParentdeptid());		
		Long rtxdeptid=tawSystemDeptDao.getNextRtxDeptid();
		tawSystemDept.setDeptid(deptid);
		tawSystemDept.setRtxdeptid(rtxdeptid);
		tawSystemDeptDao.save(tawSystemDept);
		SysCatchUtil.updateDeptCache(tawSystemDept); 
		
		Tree tree=new Tree();
		tree.setId(String.valueOf(deptid));
		tree.setText(tawSystemDept.getDeptname());
		tree.setPid(String.valueOf(tawSystemDept.getParentdeptid()) );		
		tree.setState("open");		
		jSONObject.put("node", JSON.toJSON(tree));
		jSONObject.put("status", 1);		
		return jSONObject.toJSONString();
	}	
	@Override
	public String getDeptJson(Long deptid) { 
		String json="{}";		
		TawSystemDept tawSystemDept = tawSystemDeptDao.getById(deptid);
		json=JSON.toJSONString(tawSystemDept);			
		return json;
	}
	
	@Override
	public String delDept(Long deptid) {		
		TawSystemDept tawSystemDept=super.getById(deptid);
		if(tawSystemDept.getParentdeptid()==-999 ){
			StatusEnum.STATUS_FAIL.setMsg("根节点不能删除!");
			return StatusEnum.STATUS_FAIL.toString();
		}else {
			tawSystemDeptDao.delSubAll(deptid);
			return StatusEnum.STATUS_SUCCESS.toString();
		}
	}
	@Override
	public String getTree(Long parentDeptid,String treeType) {
		List<TawSystemDept> dlist = tawSystemDeptDao.getByParentdeptid(parentDeptid);
		List<Tree> treeDataList = new ArrayList<Tree>();
        Tree treeData;
        for (TawSystemDept TawSystemDept : dlist) {
            treeData = new Tree();
            treeData.setId(String.valueOf(TawSystemDept.getDeptid()));
            treeData.setPid(String.valueOf(TawSystemDept.getParentdeptid()));
            treeData.setText(TawSystemDept.getDeptname());
            if( tawSystemDeptDao.isHaveSub(TawSystemDept.getDeptid())==false&&"dept".equals(treeType) ){
            	treeData.setState("open");
            }else {
            	treeData.setState("closed");
            }
            treeDataList.add(treeData);
        }	
		String json = JSONArray.toJSONString(treeDataList);
		return json;
	}
	@Override
	public String modify(TawSystemDept tawSystemDept) {
		JSONObject jSONObject =new JSONObject();
		super.update(tawSystemDept);
		Tree tree=new Tree();
		tree.setId(String.valueOf(tawSystemDept.getDeptid()));
		tree.setText(tawSystemDept.getDeptname());
		tree.setPid(String.valueOf(tawSystemDept.getParentdeptid()) );	
		if(tawSystemDeptDao.isHaveSub(tawSystemDept.getDeptid()) ) {
			tree.setState("closed");	
		}else {
			tree.setState("open");
		}
			
		jSONObject.put("node", JSON.toJSON(tree));		
		return jSONObject.toJSONString();
	}
	/*set、get*/
	@Override
	@Resource(name="tawSystemDeptDao")
	public void setBasedao(BaseDao<TawSystemDept> basedao) {		
		super.setBasedao(basedao);
	}
	
		
}
