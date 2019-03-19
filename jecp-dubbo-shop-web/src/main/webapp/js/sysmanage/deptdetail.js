/**
 * 数据部门详细页面
 */
var type;//定义操作类型(更新、新增)
if(!deptdetail){
	var deptdetail={};
}
deptdetail={
		//添加部门菜单
		addTree:function(){				
			$('#deptform').form({    
			    url:'deptadd',    
			    onSubmit: function(){    
			    	
			    },    
			    success:function(data){ 
			    	var d =eval('('+data+')');
			    	if('1'==d.status){
			    		var node=d.node;			    			
			    		var selected = $('#depttree').tree('getSelected');
			    		if(selected.state=='closed'){
			    			$('#depttree').tree('expand',selected.target);
			    		}else{
			    			$('#depttree').tree('append', {
				    			parent: selected.target,
				    			data: node
				    		});
			    		}
			    		$('#depttabs').tabs('close', "新增部门");
//			    		$('#depttree').tree('refreshCurrent',"depttree?parentdeptid="+d.parentdeptid+"&r="+Math.random());
			    	}else{
			    		$.messager.alert('提示','添加失败!'); 
			    	}
			    }    
			});      
			$('#deptform').submit(); 	
		},//获得具体菜单对象
		getTree:function(){		
			$('#deptform').form('load','deptget?deptid='+$("#deptrightmenu").attr("pid"));
		},updateTree:function(){			
			$('#deptform').form({    
			    url:'deptupdate',    
			    onSubmit: function(){    

			    },    
			    success:function(data){ 
			    	var d =eval('('+data+')');
			    	if('0'==d.status){
			    		init.messager('更新失败!');
			    	}else{			    		 
			    		var selected=$('#depttree').tree('getSelected');
			    		var node=d.node;
			    		$('#depttree').tree('update', {
			    			target: selected.target,
			    			text: node.text
			    		});
			    		init.messager('更新成功!'); 	
			    		$('#depttabs').tabs('close', "部门修改");
			    	}				
			    }    
			});      
			$('#deptform').submit(); 	
		}
		
}
/**
 * 判断按钮点击事件，更新或新增
 */
$("#deptdetailbtn").on("click", function(){
	if("update"==type){
		deptdetail.updateTree();
	}else if("insert"==type){
		deptdetail.addTree();
	}
});
$(function () {	
	//判断当前操作
	if($('#depttabs').tabs('exists','部门修改')){
		deptdetail.getTree();
		type='update';
	}else{
		type='insert';
		$("#deptDetail #parentdeptid").val($("#deptrightmenu").attr("pid"));//设置父节点ID
		$("#deptDetail #rtxparentdeptid").val($("#deptrightmenu").attr("rtxparentdeptid"));//设置rtx父节点ID
	}
});