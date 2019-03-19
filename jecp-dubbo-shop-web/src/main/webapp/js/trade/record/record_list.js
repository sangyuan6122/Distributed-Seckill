/**
 * 商品列表页面
 */
var id;
if (!trade_record_list) {
	var trade_record_list = {};
}
trade_record_list = {
	id : '',
	init : function() {
		$('#trade #recordlistdg').datagrid({ 			
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
			url:'trade/record/list',
			toolbar: '#trade #recordlistdgbar',
			onClickRow:function(index, row){
				$(this).datagrid('unselectRow',index);	
			},columns:[[ 
				{field: 'tradeId',hidden:true},	
				{field:'buyerId',title:'买家账户',width:100,align:'left'}, 
				{field:'sellerId',title:'商家账户',width:100,align:'left'}, 
				{field:'tradeAmount',title:'交易金额',width:40,align:'left'}, 
				{field:'payMethod',title:'支付方法',width:40,align:'left'}, 
				{field:'gtid',title:'全局ID',width:60,align:'left'},
				{field:'status',title:'支付状态',width:60,align:'left'},
				{field:'createTime',title:'创建时间',width:60,align:'left'},
				{field:'updateDate',title:'更新时间',width:70,align:'left'},	
				{field: 'payment',width: 30,
                    formatter: function(value, row, index) {
                    
                        return '<a style="color:blue;text-decoration:underline" href="javascript:void(0);" onclick="trade_record_list.pay('+index+')">支付</a>';
                    }
                },	
			]],onLoadSuccess:function(data){
				
			}		
		});
	},pay:function(index){
		var row=$('#trade #recordlistdg').datagrid('getRows')[index];
		$('#trade #recordlistdg').datagrid("loading","支付中...");
		$.ajax({
			url:'shop/order/pay',
			type:'post',
			dataType:'json',
			data: 'gtid='+row.gtid+'&r='+Math.random(),
			success:function(data){
				$('#trade #recordlistdg').datagrid('loaded');
		    	if("1"==data.code){
		    		init.messager("支付成功！");
		    	}else if("-1"==data.code){
		    		init.messager("权限不足，请联系管理员！");		    		
		    	}else if("0"==data.code){
		    		init.messager("支付失败，请联系管理员！！");		    		
		    	}else{
		    		init.messager('支付失败,'+data.message);		    		
		    	}		
			}				
		})
	}
}
$(function() {
	
	trade_record_list.init();

});