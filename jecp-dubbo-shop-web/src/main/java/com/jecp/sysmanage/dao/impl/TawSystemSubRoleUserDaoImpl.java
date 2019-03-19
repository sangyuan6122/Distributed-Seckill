package com.jecp.sysmanage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.common.model.Tree;
import com.jecp.common.model.TreeAttributes;
import com.jecp.common.util.TreeUtil;
import com.jecp.sysmanage.dao.TawSystemSubRoleUserDao;
import com.jecp.sysmanage.model.TawSystemSubrole;
import com.jecp.sysmanage.model.TawSystemSubroleuser;
@Repository(value="tawSystemSubRoleUserDao")
public class TawSystemSubRoleUserDaoImpl extends BaseDaoImpl<TawSystemSubroleuser> implements TawSystemSubRoleUserDao{

	@Override
	public List<TawSystemSubroleuser> getBySubroleid(String subroleid) {
		Query q = getSession().createQuery("from TawSystemSubroleuser where subroleid=?");
		q.setParameter(0, subroleid);
		List<TawSystemSubroleuser> list = q.list();
		return list;
	}

	@Override
	public List<TawSystemSubroleuser> isExist(String[] featurecodes) {
		Query q = getSession().createQuery("from TawSystemSubroleuser where featurecode in(:featurecodes)");
		q.setParameterList("featurecodes", featurecodes);
		List<TawSystemSubroleuser> list = q.list();	
		return list;
	}

	@Override
	public List<Tree> getRoleUserTree(String roleid) {
		Query query=getSession().createQuery("from TawSystemSubrole where roleid=? order by deptid");
		query.setParameter(0, roleid);
		List<TawSystemSubrole> rolelist = query.list();
        List<Tree> treeDataList = new ArrayList<Tree>();
        Tree treeData;
        Tree subtreeData;
        List<TawSystemSubroleuser> roleuserlist;
        TreeAttributes treeAttributes ;
        TreeAttributes treeAttributes2 ;
        for (TawSystemSubrole tawSystemSubrole : rolelist) {
        	treeData = new Tree();
            treeData.setId(tawSystemSubrole.getId());
            treeData.setPid(roleid);
            treeData.setIconCls("other-subrole");
            treeData.setText(tawSystemSubrole.getSubrolename());            
            treeData.setState("closed");
            treeAttributes =new TreeAttributes();
            treeAttributes.setA1("subrole");
            treeData.setAttributes(treeAttributes);
            treeDataList.add(treeData);
            //查询子角色对应人员
            query=getSession().createQuery("from TawSystemSubroleuser where subroleid=?");
            query.setParameter(0, tawSystemSubrole.getId());
            roleuserlist = query.list();
            for (TawSystemSubroleuser tawSystemSubroleuser : roleuserlist) {
                subtreeData = new Tree();
                subtreeData.setId(tawSystemSubroleuser.getId());
                subtreeData.setPid(tawSystemSubroleuser.getSubroleid());
                subtreeData.setText(tawSystemSubroleuser.getUsername());  
                treeAttributes2 =new TreeAttributes();
                treeAttributes2.setA1("user");
                subtreeData.setAttributes(treeAttributes2);
                subtreeData.setIconCls("other-people");
                subtreeData.setState("open");

                treeDataList.add(subtreeData);
            }
        }
        subtreeData = new Tree();
        subtreeData.setId(roleid);
        subtreeData.setPid(null);
        subtreeData.setText("全部角色");
        subtreeData.setState("open");
        treeDataList.add(subtreeData);
        List<Tree> newTreeDataList = TreeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}
	
}
