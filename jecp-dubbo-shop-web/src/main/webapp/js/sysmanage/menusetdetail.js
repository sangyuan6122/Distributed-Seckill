/**
 * 部门管理页面
 */
if(!menusetdetail){
	var menusetdetail={};
}
menusetdetail={
		init:function(){
			$('#menusetdetailform #clienttype').combobox({    
			    data:[{"id":"C","text":"电脑端"},{"id":"M","text":"移动端"}],  
			    required:true,
			    panelHeight:50,
			    valueField:'id',    
			    textField:'text'   
			});  
		},
		add:function(){
			$('#menusetdetailform').form({    
			    url:'privmenuadd',
			    cache:false,
			    onSubmit: function(param){ 
			    	param.r=Math.random();
			    	return $("#menusetdetailform").form('validate');
			    },    
			    success:function(data){    
			    	var d=eval('('+data+')');
			    	if("1"==d.status){
			    		$('#menusetdetailwin').window('close');	
			    		menusetlist.reload();
			    	}else{
			    		$.messager.alert('提示','数据错误，请联系管理员！'); 
			    	}			    	
			    }    
			});
			$('#menusetdetailform').submit();
		},//更新
		update:function(){
			$('#menusetdetailform').form({    
			    url:'privmenuupdate',
			    cache:false,
			    onSubmit: function(param){ 
			    	param.r=Math.random();
			    	return $("#menusetdetailform").form('validate');
			    },    
			    success:function(data){    
			    	var d=eval('('+data+')');
			    	if("1"==d.status){
			    		$('#menusetdetailwin').window('close');	
			    		menusetlist.reload();
			    	}else{
			    		$.messager.alert('提示','更新失败，请联系管理员！'); 
			    	}			    	
			    }    
			});
			$('#menusetdetailform').submit();
		},
		load:function(){
			$('#menusetdetailform').form('load','privmenuget?id='+menusetlist.menuid);
		}		
}
$(function () {
	menusetdetail.init();
	if(menusetlist.type=='update'){
		menusetdetail.load();
	}
	$("#menusetdetailbtn").on("click", function(){				
		if(menusetlist.type=='update'){
			menusetdetail.update();
		}else{
			menusetdetail.add();
		}		
	});
	
});