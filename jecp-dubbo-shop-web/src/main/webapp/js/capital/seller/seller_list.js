/**
 * 商品列表页面
 */
var id;
if (!seller_list) {
	var seller_list = {};
}
seller_list = {
	id : '',
	init : function() {
		$('#capital #sellerlistdg').datagrid({ 			
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
			url:'capital/seller/list',
			onClickRow:function(index, row){
				$(this).datagrid('unselectRow',index);	
			},columns:[[ 
				{field: 'id',hidden:true},	
				{field:'userId',title:'用户ID',width:80,align:'left'}, 
				{field:'accountType',title:'账户类型',width:50,align:'left'}, 
				{field:'balance',title:'账户余额',width:80,align:'left'}, 
				{field:'balanceFrozen',title:'冻结资金',width:80,align:'left'}, 
				{field:'createTime',title:'创建时间',width:60,align:'left'},
				{field:'updateDate',title:'更新时间',width:60,align:'left'}
			]],onLoadSuccess:function(data){
				
			}		
		});
	}
}
$(function() {
	
	seller_list.init();

});