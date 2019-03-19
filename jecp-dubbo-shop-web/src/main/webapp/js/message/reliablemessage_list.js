/**
 * 商品列表页面
 */
var id;
if (!reliablemessage_list) {
	var reliablemessage_list = {};
}
reliablemessage_list = {
	id : '',
	init : function() {
		$('#message #reliablemessagelistdg').datagrid({ 			
		    brecord:true,
			fit:true,
			striped:true,
			pageSize:20,
		    pagination:true,
		    singleSelect:true,
		    cache:false,
		    rownumbers:true,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    fitColumns:true,
			url:'message/list',
			toolbar: '#message #reliablemessagelistdgbar',
			onClickRow:function(index, row){
				$(this).datagrid('unselectRow',index);	
			},columns:[[ 		
				{field:'id',checkbox:true},
				{field:'messageKey',title:'消息ID',width:60,align:'left'},
				{field:'messageBody',title:'消息内容',width:100,align:'left'},
				{field:'messageDataType',title:'数据类型 ',width:40,align:'left'},
				{field:'confirmTimeoutQueue',title:'确认超时队列',width:120,align:'left'},
				{field:'consumerQueue',title:'消费者队列  ',width:120,align:'left'},
				{field:'checkProducerTimes',title:'回查次数',width:30,align:'left'},	
				{field:'resendTimes',title:'重发次数 ',width:30,align:'left'},
				{field:'alreadyDead',title:'消息是否死亡',width:30,align:'left'},	
				{field:'status',title:'消息状态',width:50,align:'left'},
				{field:'createTime',title:'创建时间 ',width:60,align:'left'}, 
				{field:'lastUpdateTime',title:'最后更新时间',width:60,align:'left'},				
			]],onLoadSuccess:function(data){
				$(this).datagrid('beautify');
				$(this).datagrid('tip',['messageBody']);
			}		
		});
		$('#message #status').combobox({    
		    data:[{
		    	"id":"",    
		        "text":"全部",    
		        "selected":true  
		    },
		    {    
		        "id":"UNCONFIRMED",    
		        "text":"待确认"   
		    },{    
		        "id":"UNCONSUMED",    
		        "text":"待消费"   
		    }] ,    
		    valueField:'id',    
		    textField:'text',
		    onSelect:function(d){		
		    	if(d.id){		
		    		$('#message #reliablemessagelistdg').datagrid('options').queryParams={'status':d.id};
		    		$('#message #reliablemessagelistdg').datagrid('reload');
		    	}else{
		    		$('#message #reliablemessagelistdg').datagrid('options').queryParams={};
		    		$('#message #reliablemessagelistdg').datagrid('reload');
		    	}		    	
		    }
		}); 
		$('#message #bath').linkbutton({    
		    iconCls: 'icon-ok',
		    onClick:function(){
		    	var checkedItems = $('#message #reliablemessagelistdg').datagrid('getChecked');
		    	if(checkedItems.length==0){
		    		init.messager('请选择需要重发的消息!');
		    		return;
		    	}
		    	var unfirmed=[];
		    	var unconsumed=[];
		    	$.each(checkedItems, function(index, row){
		    		if(row.status=='待确认'){
		    			unfirmed.push(row.id);
		    		}else if(row.status=='待消费'){
		    			unconsumed.push(row.id);
		    		}		    		
		        }); 
		    	$.messager.confirm('提示', '待确认:<label style="color:red;font-weight: bold">'+unfirmed.length+'</label>条,待消费:<label style="color:red;font-weight: bold">'+unconsumed.length+'</label>条,确认重发?', function(r){
		    		if (r){
		    			var ids=JSON.stringify(unfirmed.concat(unconsumed));
		    			$.ajax({
		    				url:'message/exceptionHandling',
		    				type:'post',
		    				contentType: "application/json;charset=utf-8",
		    				dataType:'json',
		    				data: ids,
		    				success:function(data){
		    					$('#shop #orderlistdg').datagrid('loaded');
		    			    	if("1"==data.code){
		    			    		init.messager("处理完成！");
		    			    	}else if("-1"==data.code){
		    			    		init.messager("权限不足，请联系管理员！");		    		
		    			    	}else if("0"==data.code){
		    			    		init.messager("处理失败，请联系管理员！！");		    		
		    			    	}else{
		    			    		init.messager('处理失败,'+data.message);		    		
		    			    	}		
		    				}				
		    			});
		    		}
		    	});
		    }
		});  
	},statistics:function(){
		$.ajax({
			url:'message/getStatusStatistics',
			type:'post',
			dataType:'json',
			data: 'r='+Math.random(),
			success:function(data){
				$('#message #unfirmed').html(data.unfirmed);
				$('#message #unconsumed').html(data.unconsumed);
				$('#message #alreadyDead').html(data.alreadyDead);
			}				
		})
	}
}
$(function() {
	
	reliablemessage_list.init();
	reliablemessage_list.statistics();
});