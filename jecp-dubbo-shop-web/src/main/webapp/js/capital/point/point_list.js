/**
 * 商品列表页面
 */
var id;
if (!point_list) {
	var point_list = {};
}
point_list = {
	id : '',
	init : function() {
		$('#capital #pointlistdg').datagrid({ 			
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
			url:'capital/point/list',
			onClickRow:function(index, row){
				$(this).datagrid('unselectRow',index);	
			},columns:[[ 
				{field: 'id',hidden:true},	
				{field:'userId',title:'用户ID',width:80,align:'left'}, 
				{field:'accountType',title:'账户类型',width:50,align:'left'}, 
				{field:'totalPoints',title:'积分数量',width:80,align:'left'}, 
				{field:'createTime',title:'创建时间',width:60,align:'left'},
				{field:'updateDate',title:'更新时间',width:60,align:'left'}
			]],onLoadSuccess:function(data){
				
			}		
		});
	}
}
$(function() {
	
	point_list.init();

});