/**
 * 定时任务详细页面
 */
if(!jobdetail){
	var jobdetail={};
}
jobdetail={
		//初始化
		init:function(){
			$('#jobdetailfrm #timingjob').combobox({ 
				url:'dictgetCodeToJson?parentdictid=823',
				valueField:'id',    
			    textField:'text',
			    editable:false,
			    panelHeight:'50',
			    onChange:function(newValue,oldValue){			
			    	$('#jobName').textbox('setValue',$(this).combobox('getText'));
			    	$('#jobClassName').textbox('setValue',newValue);
			    	
			    	
			    }
			});  
			$('#jobdetailfrm #jobName').textbox({ 
				readonly:true,	
				required:true
			});  
			$('#jobdetailfrm #jobClassName').textbox({ 
				readonly:true,	
				required:true	    
			});  
			$('#jobGroup').combobox({    			     
			    valueField:'jobGroup',    
			    textField:'jobGroup',
			    value:'default-group',
			    editable:false,
			    panelHeight:'25',
			    data:[{
			    	jobGroup:'default-group'
			    }],
			    
			});
			$('#triggerType').combobox({    			     
			    valueField:'triggerType',    
			    textField:'triggerType',
			    editable:false,
			    panelHeight:'25',
			    value:'cron',
			    data:[{
			    	triggerType:'cron',			    	
			    }]   
			});
		},
		addjob:function(){
			$('#jobdetailfrm').form({    
			    url:'scheduleradd',    
			    onSubmit: function(){    
			    	return $("#jobdetailfrm").form('validate');
			    },    
			    success:function(d){ 
			    	d=eval('('+d+')');
			    	if("1"==d.status){			    		  
						  $("#joblistdg").datagrid('reload');
						  $('#joblistwin').window('close');
					  }else if("-1"==d.status){
						  $.messager.alert('提示','权限不足，请联系管理员！'); 
					  }else{
						  $.messager.alert('提示','添加失败！'); 
					  }
			    }    
			});    
			$('#jobdetailfrm').submit(); 
		}
}
$(function () {
	jobdetail.init();	
});