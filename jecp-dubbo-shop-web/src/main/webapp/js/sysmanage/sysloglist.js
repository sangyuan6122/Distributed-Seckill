/**
 * 系统日志列表
 */
if(!sysloglist){
	var sysloglist={};
}
sysloglist={
	init:function(){	
		$('#sysloglist').find("#sysloglistdg").datagrid({    
			url:'slogload',
		    iconCls:'icon-sum',
			fit:true,
			striped:true,
			pageSize:20,
		    pagination:true,
		    rownumbers:true,
		    cls:'otherdgheader',
		    singleSelect:true,
		    style : {
	            padding : '8 8 10 8'
	        },
		    cache:false,
		    checkOnSelect:false,
		    selectOnCheck:true,
		    fitColumns:true,
		    remoteSort:true,
		    multiSort:true,
		    toolbar: '#sysloglistbar',
		    columns:[[    
	            {field:'id',hidden:true},  
				{field:'time',title:'时间',width:120},    
				{field:'module',title:'模块',width:100},   
				{field:'businessid',title:'业务ID',width:100},
				{field:'username',title:'操作人',width:50},
				{field:'loginip',title:'操作人IP',width:70},
				{field:'type',title:'操作类型',width:70},
				{field:'logtext',title:'内容',width:250,
					formatter: function(value,row,index){
						return JSON.stringify(value);
		        	}
				},
				{field:'remark',title:'备注',width:50}
			]], onLoadSuccess : function (){	
				$(this).datagrid('beautify');
				$(this).datagrid('tip',['logtext']);
	        }
		}); 
	}
}
$(function () {	
	sysloglist.init();
	$("#sysloglistbar").find("#queryBtn").on("click", function(){		
		$('#sysloglist').find("#sysloglistdg").datagrid({			
			queryParams:{
				mobile:$("#sysloglistbar").find("#mobile").textbox('getValue'),			
				beginTime:$("#sysloglistbar").find("#beginTime").datetimebox('getValue'),
				endTime:$("#sysloglistbar").find("#endTime").datetimebox('getValue'),
			}
		});
		//$('#sysloglist').find("#sysloglistdg").datagrid('reload');
	});	
	
});