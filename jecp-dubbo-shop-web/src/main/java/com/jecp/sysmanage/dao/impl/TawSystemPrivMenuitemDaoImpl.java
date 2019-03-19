package com.jecp.sysmanage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.common.model.Tree;
import com.jecp.common.util.TreeUtil;
import com.jecp.sysmanage.dao.TawSystemPrivMenuitemDao;
import com.jecp.sysmanage.model.TawSystemPriv;
import com.jecp.sysmanage.model.TawSystemPrivMenuitem;
@Repository(value="tawSystemPrivMenuitemDao")
public class TawSystemPrivMenuitemDaoImpl extends BaseDaoImpl<TawSystemPrivMenuitem> implements TawSystemPrivMenuitemDao{

	@Override
	public List<Tree> getTree(String menuid,String clientType) {
		Query query=getSession().createNativeQuery("select p.* from taw_system_priv_menuitem i,"
				+ "taw_system_priv p where i.PRIVID=p.privid and i.menuid=:menuid order by p.sort",TawSystemPriv.class);
		query.setParameter("menuid", menuid);
		List<TawSystemPriv> list = query.list();
		List<Tree> treeDataList = new ArrayList<Tree>();
        for (TawSystemPriv tawSystemPriv : list) {
            Tree treeData = new Tree();
            treeData.setId(tawSystemPriv.getPrivid().toString());
            treeData.setPid(tawSystemPriv.getParentprivid());
            treeData.setText(tawSystemPriv.getName()); 
            if("3".equals(tawSystemPriv.getMenulevel()) ){
            	treeData.setIconCls("other-button");
            }
            if( isHaveSub(tawSystemPriv.getPrivid().toString())==false ){
            	treeData.setState("open");
            }
            treeDataList.add(treeData);
        }
        String rootid="-999";
        if("M".equals(clientType)){
        	rootid="-998";
		}
        List<Tree> newTreeDataList = TreeUtil.getfatherNode(rootid,treeDataList);
		return newTreeDataList;
	}
	public Boolean isHaveSub(String parentprivid) {
		Query query=getSession().createNativeQuery("select p.* from taw_system_priv_menuitem i,"
				+ "taw_system_priv p where i.PRIVID=p.privid and p.PARENTPRIVID=:parentprivid");
		query.setParameter("parentprivid", parentprivid);
		if(query.list().size()>0){
			return true;
		}
		return false;
	}
	@Override
	public void delAllTree(String menuid, String privid) {
		Query query=getSession().createNativeQuery("delete from taw_system_priv_menuitem where menuid=:menuid"
				+ " and privid in(select privid from taw_system_priv start with privid=:privid "
				+ " connect by prior privid=parentprivid)");
		query.setParameter("menuid", menuid);
		query.setParameter("privid", privid);
		query.executeUpdate();		
	}
	@Override
	public boolean isHaveParent(String menuid, String privid) {
		Query query=getSession().createNativeQuery("select '1' from taw_system_priv_menuitem "
				+ "where menuid=:menuid and privid =(select parentprivid from taw_system_priv where privid=:privid)");
		query.setParameter("menuid", menuid);
		query.setParameter("privid", privid);
		if(query.list().size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	@Override
	public void addRootTree(String menuid,String clientType) {
		TawSystemPrivMenuitem menuitem = new TawSystemPrivMenuitem();
		menuitem.setMenuid(menuid);
		if("C".equals(clientType)){
			menuitem.setPrivid("-1");
			menuitem.setClienttype("C");
		}else{
			menuitem.setPrivid("-2");
			menuitem.setClienttype("M");
		}
		
		getSession().save(menuitem);		
	}
	@Override
	public void delByMenuId(String menuid) {
		Query query=getSession().createNativeQuery("delete from taw_system_priv_menuitem where menuid=:menuid");
		query.setParameter("menuid", menuid);	
		query.executeUpdate();		
	}

	
}
