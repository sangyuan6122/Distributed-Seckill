/**
 * 商品列表页面
 */
var id;
if (!order_list) {
	var order_list = {};
}
order_list = {
	id : '',
	init : function() {
		$('#shop #orderlistdg').datagrid({ 			
		    border:true,
			fit:true,
			striped:true,
			pageSize:20,
		    pagination:true,
		    singleSelect:true,
		    cache:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    fitColumns:true,
			url:'shop/order/list',
			toolbar: '#shop #orderlistdgbar',
			onClickRow:function(index, row){
				$(this).datagrid('unselectRow',index);	
			},columns:[[ 
				{field: 'checked',width: 10,
                    formatter: function(value, row, index) {
                        return '<input id="orderId" name="orderId" type="radio" value="'+index+'" />';
                    }
                },	
				{field:'productTitle',title:'商品名称',width:300,align:'left'}, 
				{field:'productCount',title:'商品数量',width:40,align:'left'}, 
				{field:'productPrice',title:'商品价格',width:40,align:'left'}, 
				{field:'totalAmount',title:'总金额',width:40,align:'left'}, 
				{field:'userId',title:'购买用户',width:60,align:'left'},
				{field:'salesWay',title:'促销方式',width:60,align:'left'},
				{field:'orderChannel',title:'订单渠道',width:60,align:'left'},
				{field:'createTime',title:'创建时间',width:70,align:'left'},
				{field:'payTime',title:'支付时间',width:70,align:'left'},
				{field:'status',title:'订单状态',width:40,align:'left'},
				{field: 'payment',width: 30,
                    formatter: function(value, row, index) {
                    
                        return '<a style="color:blue;text-decoration:underline" href="javascript:void(0);" onclick="order_list.pay('+index+')">支付</a>';
                    }
                },	
			]],onLoadSuccess:function(data){
				
			}		
		});
	},pay:function(index){
		var row=$('#shop #orderlistdg').datagrid('getRows')[index];
		$('#shop #orderlistdg').datagrid("loading","支付中...");
		$.ajax({
			url:'shop/order/pay',
			type:'post',
			dataType:'json',
			data: 'gtid='+row.gtid+'&r='+Math.random(),
			success:function(data){
				$('#shop #orderlistdg').datagrid('loaded');
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
		});
	}
}
$(function() {
	
	order_list.init();

});