/**
 * 商品列表页面
 */
var id;
if (!product_list) {
	var product_list = {};
}
product_list = {
	id : '',
	init : function() {
		$('#shop #productlistdg').datagrid({ 			
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
			url:'shop/product/list',
			toolbar: '#shop #productlistdgbar',
			onClickRow:function(index, row){
				$(this).datagrid('unselectRow',index);	
			}, 			
			columns:[[ 
				{field: 'checked', title: '', width: 10,
                    formatter: function(value, row, index) {
                        return '<input id="productId" name="productId" type="radio" value="'+index+'" />';
                    }
                },
				{field:'pictureUrl',title:'商品图片',width:80,align:'left',formatter: 
					function(value,row,index){
						return '<img src="'+value+'"> ';						
					}
				}, 
				{field:'title',title:'商品标题',width:200,align:'left'}, 
				{field:'subtitle',title:'商品副标题',width:200,align:'left'}, 
				{field:'price',title:'单价',width:40,align:'left'}, 
				{field:'stock',title:'库存数量',width:40,align:'left'}, 
				{field:'status',title:'商品状态',width:40,align:'left'},
				{field:'createTime',title:'创建时间',width:80,align:'left'},
				{field:'updateTime',title:'更新时间',width:80,align:'left'},
			]], 			
		});
	}
}
$(function() {
	
	product_list.init();
	$("#shop #addSecKill").on("click", function(){	
		var index=$("#shop #productId:checked").val();
		if(typeof(index)=='undefined'||!index){
			init.messager("请选择要进行秒杀的商品!");
		}else{
			var product=$('#shop #productlistdg').datagrid('getData').rows[index];			
			init.winshow('shopaddSecKillwin','创建秒杀活动',500,450,'shop/product/seckill_add.html',product);
		}
		
	});
});