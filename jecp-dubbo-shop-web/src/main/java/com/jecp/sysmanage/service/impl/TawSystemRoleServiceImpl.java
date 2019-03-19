package com.jecp.sysmanage.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.common.model.Tree;
import com.jecp.common.model.TreeAttributes;
import com.jecp.common.util.TreeUtil;
import com.jecp.sysmanage.dao.TawSystemRoleDao;
import com.jecp.sysmanage.model.TawSystemRole;
import com.jecp.sysmanage.service.TawSystemRoleService;

import net.sf.json.JSONArray;

@Service("tawSystemRoleService")
public class TawSystemRoleServiceImpl extends BaseServiceImpl<TawSystemRole> implements TawSystemRoleService {
	private TawSystemRoleDao tawSystemRoleDao;
	@Override
	public String getTree() {
		List<TawSystemRole> list=tawSystemRoleDao.getTree();
		List<Tree> treeDataList = new ArrayList<Tree>();
		Tree treeData;
		TreeAttributes treeAttributes;
		for(TawSystemRole tawSystemRole:list){
			treeData=new Tree();
			treeAttributes=new TreeAttributes();
			treeAttributes.setA1(tawSystemRole.getType());
			treeData.setId(tawSystemRole.getRoleid());
			treeData.setPid(tawSystemRole.getParentid());			
			treeData.setText(tawSystemRole.getName());
			treeData.setAttributes(treeAttributes);
			treeData.setIconCls("other-menu");
			if( isHaveSub(tawSystemRole.getRoleid())==false&&"role".equals(tawSystemRole.getType()) ){
            	treeData.setState("open");
            	treeData.setIconCls("other-role");
            }
			treeDataList.add(treeData);
		}
		List<Tree> newTreeDataList = TreeUtil.getfatherNode(treeDataList);
		
		return JSONArray.fromObject(newTreeDataList).toString();
	}

	@Override
	public Boolean isHaveSub(String roleid) {
		
		return tawSystemRoleDao.isHaveSub(roleid);
	}

	@Override
	public int del(String roleid) {		
		return tawSystemRoleDao.del(roleid);
	}
	
	/*set、get*/
	@Override
	@Resource(name="tawSystemRoleDao")
	public void setBasedao(BaseDao<TawSystemRole> basedao) {
		super.setBasedao(basedao);		
	}
}
