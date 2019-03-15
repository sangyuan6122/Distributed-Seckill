package com.jecp.common.util;

import java.util.ArrayList;
import java.util.List;

import com.jecp.common.model.Tree;

public class TreeUtil {

	    public final static List<Tree> getfatherNode(List<Tree> treeDataList) {
	        List<Tree> newTreeDataList = new ArrayList<Tree>();
	        for (Tree Tree : treeDataList) {
	            if(Tree.getPid() == null||Tree.getPid().equals("-999")) {
	                //获取父节点下的子节点
	                Tree.setChildren(getChildrenNode(Tree.getId(),treeDataList));
	                Tree.setState("open");
	                newTreeDataList.add(Tree);
	            }
	        }
	        return newTreeDataList;
	    }
	    public final static List<Tree> getfatherNode(String rootid,List<Tree> treeDataList) {
	        List<Tree> newTreeDataList = new ArrayList<Tree>();
	        for (Tree Tree : treeDataList) {
	            if(Tree.getPid() == null||Tree.getPid().equals(rootid)) {
	                //获取父节点下的子节点
	                Tree.setChildren(getChildrenNode(Tree.getId(),treeDataList));
	                Tree.setState("open");
	                newTreeDataList.add(Tree);
	            }
	        }
	        return newTreeDataList;
	    }
	    private final static List<Tree> getChildrenNode(String pid , List<Tree> treeDataList) {
	        List<Tree> newTreeDataList = new ArrayList<Tree>();
	        for (Tree Tree : treeDataList) {
	            if(Tree.getPid() == null)  continue;
	            //这是一个子节点
	            if(Tree.getPid().equals(pid)){
	                //递归获取子节点下的子节点
	                Tree.setChildren(getChildrenNode(Tree.getId() , treeDataList));
	                newTreeDataList.add(Tree);
	            }
	        }
	        return newTreeDataList;
	    }
}
