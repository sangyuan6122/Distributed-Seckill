/**
 * 数据字典详细页面
 */
var type;//定义操作类型(更新、新增)
if(!dictdetail){
	var dictdetail={};
}
dictdetail={
		olddictname:'',//修改前
		//添加字典菜单
		addTree:function(){			
			$('#dictform').form({    
			    url:'dictadd',    
			    onSubmit: function(){    
			    	
			    },    
			    success:function(data){ 
			    	var d =eval('('+data+')');
			    	if('1'==d.status){
			    		$('#dicttabs').tabs('close', "新增字典");  			    		
			    	}else if('2'==d.status){
			    		$.messager.alert('提示','数据重复!'); 
			    		return;
			    	}else{
			    		$.messager.alert('提示','添加失败!'); 
			    		return;
			    	}
			    	var node = $('#dicttree').tree('find', -1);					
					$('#dicttree').tree('reload', node.target);
					$('#dicttree').tree('select', node.target);
						
			    }    
			});      
			$('#dictform').submit(); 	
		},//获得具体菜单对象
		getTree:function(){		
			$('#dictform').form({
				onLoadSuccess:function(data){
					dictdetail.olddictname=data.dictname;
				}
			});
			$('#dictform').form('load','dictget?dictid='+dictlist.pid);
		},updateTree:function(){			
			$('#dictform').form({    
			    url:'dictupdate',    
			    onSubmit: function(param){    
			    	param.olddictname=dictdetail.olddictname;
			    },    
			    success:function(data){ 
			    	var d =eval('('+data+')');
			    	if('1'==d.status){
			    		$.messager.alert('提示','更新成功!'); 
			    	}else if('2'==d.status){
			    		$.messager.alert('提示','数据重复!'); 
			    		return;
			    	}else{
			    		$.messager.alert('提示','添加失败!'); 
			    		return;
			    	}
			    	var node = $('#dicttree').tree('find', -1);		
					$('#dicttree').tree('reload', node.target);
					$('#dicttree').tree('select', node.target);
					$('#dicttabs').tabs('close', "更新字典"); 
			    }    
			});      
			$('#dictform').submit(); 	
		}
		
}
/**
 * 判断按钮点击事件，更新或新增
 */
$("#dictbtn").on("click", function(){
	if("update"==type){
		dictdetail.updateTree();
	}else if("insert"==type){
		dictdetail.addTree();
	}
});
$(function () {
	$("#parentdictid").val(dictlist.pid);//设置父节点ID
	//判断当前操作
	if($('#dicttabs').tabs('exists','更新字典')){
		dictdetail.getTree();
		type='update';
	}else{
		type='insert';
	}
	

});