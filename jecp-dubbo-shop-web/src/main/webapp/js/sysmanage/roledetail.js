/**
 * 角色详细页面
 */
if(!roledetail){
	var roledetail={};
}
roledetail={		
	init:function(){
		if('update'==rolelist.type){//更新
			$("#roleform").find("#roleid").val(rolelist.roleid);
			$('#roleform').form('load','roleget?roleid='+rolelist.roleid);
		}else{//添加
			$("#roleform").find("#parentid").val(rolelist.roleid);
		}
						
	},update:function(){
		if('update'==rolelist.type){//更新
			$('#roleform').form({    
			    url:'roleupdate?'+Math.random(),
			    cache:false,
			    onSubmit: function(){      
			    	return $("#roleform").form('validate');
			    },    
			    success:function(data){    
			    	var d=eval('('+data+')');
			    	if("1"==d.status){
			    		$('#rolelistwin').window('close');	
			    		$('#rolelisttree').tree('reload');    		
			    	}else{
			    		$.messager.alert('错误','数据错误，请联系管理员!'); 
			    	}		    	 
			    }    
			});
			$('#roleform').submit();
		}else{//新增
			$('#roleform').form({    
			    url:'roleadd',
			    cache:false,
			    onSubmit: function(param){  
			    	param.r=Math.random();
			    	return $("#roleform").form('validate');
			    },    
			    success:function(data){    
			    	var d=eval('('+data+')');
			    	if("1"==d.status){
			    		$('#rolelistwin').window('close');
			    		$('#rolelisttree').tree('reload');    		
			    	}else{
			    		$.messager.alert('错误','数据错误，请联系管理员!'); 
			    	}		    	 
			    }    
			});
			$('#roleform').submit();
		}
		
	}
}
$(function () {
	$.parser.parse();
	roledetail.init();
	$("#roleform").find("#rolebtn").on("click", function(){	
		roledetail.update();		
	});
});