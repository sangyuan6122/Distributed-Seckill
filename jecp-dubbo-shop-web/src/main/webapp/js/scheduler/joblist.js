/**
 * 定时任务列表页面
 */
if(!joblist){
	var joblist={};
}
joblist={
		//初始化dg
		init:function(){			
			$('#joblistdg').datagrid({    
			    url:'schedulerload',
			    singleSelect:false,
			    rownumbers:true,
			    checkOnSelect:false,
			    fit:true,
			    cls:'otherdgheader',
			    fitColumns:true,
			    selectOnCheck:true,
			    toolbar:'#joblistdgbar',
			    columns:[[    
                    {checkbox:true,field:'id',title:'ID',width:100}, 
			        {field:'jobGroup',title:'任务组',width:80},    
			        {field:'jobName',title:'任务名称',width:300},    
			        {field:'jobClassName',title:'任务执行类',width:200},
			        {field:'triggerType',title:'定时器类型',width:70},
			        {field:'cronExpression',title:'时间表达式',width:100},
			        {field:'triggerState',title:'状态',width:70},
			    ]],onLoadSuccess:function(data){
			    	$(this).datagrid('beautify');
			    }   
			});  
		},deljob:function(){
			$.messager.confirm('确认','您确认想要删除本条定时任务吗?',function(r){    
			    if (r){    
			    	var rows=$('#joblistdg').datagrid('getSelections');			    	
			    	var jobs=[];
			    	var job;
			    	for(var i=0;i<rows.length;i++){
			    		job={};
			    		job.jobName=rows[i].jobName;
			    		job.jobGroup=rows[i].jobGroup;			    		
			    		jobs.push(job);
			    	}
			    	$.ajax({
						  type: "post",
						  url: 'schedulerdels?r='+Math.random(),
						  contentType:"application/json;charset=utf-8",//json格式提交
						  data: JSON.stringify(jobs),						  
						  traditional:true,
						  success: function(data){
							  var d=eval('('+data+')');
							  if("1"==d.status){
								  $("#joblistdg").datagrid('reload');   
							  }else if("-1"==d.status){
								  $.messager.alert('提示','权限不足，请联系管理员！'); 
							  }else{
								  $.messager.alert('提示','删除失败！'); 
							  }
						  }
					});    
			    }    
			});
		}
}
$(function () {
	joblist.init();	
});