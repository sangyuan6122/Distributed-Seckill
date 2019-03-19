/**
 * 秒杀活动页面
 */
var id;
if (!seckill_list) {
	var seckill_list = {};
}
seckill_list = {
	serverTime :'',
	init : function() {
		$.ajax({  
			type: "get",  
			url: "shop/secKill/serverTime",  
			cache:false,  
			async:false,  			 
			success: function(time){  
				seckill_list.serverTime=time;
	        }  
		});
		$('#shop #seckilldg').datagrid({ 			
		    border:true,
			fit:true,
			nowrap:false,
			striped:true,
			pageSize:20,
		    pagination:true,
		    singleSelect:true,
		    cache:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    fitColumns:true,
			url:'shop/secKill/list',			
			onClickRow:function(index, row){
				$(this).datagrid('unselectRow',index);	
			}, 			
			columns:[[ 
				{field:'productId',hidden:true},
				{field:'seckillId',hidden:true},
				{field:'pictureUrl',title:'商品图片',width:80,align:'left',formatter: 
					function(value,row,index){
						return '<img src="'+value+'"> ';						
					}
				}, 
				{field:'title',title:'商品标题',width:200,align:'left'}, 
				{field:'subtitle',title:'商品副标题',width:100,align:'left'}, 
				{field:'seckillPrice',title:'秒杀价格 ',width:40,align:'left'}, 
				{field:'seckillStock',title:'数量',width:40,align:'left'},
				{field:'countDown',title:'活动时间',width:80,align:'left',formatter: function(value,row,index){					
					var startTime=new Date(Date.parse(row.startTime));
					var endTime=new Date(Date.parse(row.endTime));
	                return "<p id='"+row.seckillId+"' class='countDown' value='"+startTime.getTime()+"' endTime='"+endTime.getTime()+"'></p>";  					
				}}, 
				{field:'endTime',title:'结束时间',width:80,align:'left'},
				{field:'codeStrategy',title:'代码策略',width:100,align:'left'},
				{field:'b',width:80,align:'center',formatter: function(value,row,index){					
					var currentTime=new Date(Date.parse(seckill_list.serverTime));
					var endTime=new Date(Date.parse(row.endTime));
					var startTime=new Date(Date.parse(row.startTime));			
					var disabled=true;
					if(startTime<currentTime&&currentTime<endTime){// 进行中
						disabled=false;
					}
	                var btn = '<a seckillid="'+row.seckillId+'" onclick="seckill_list.seckill(\''+row.seckillId+'\')" href="javascript:void(0)" data-options="disabled:'+disabled+'">抢购</a>';  
	                return btn;  					
				}},				
			]],onLoadSuccess:function(data){  
				$('[seckillid]').linkbutton({});  				
				var serverTime=new Date(Date.parse(seckill_list.serverTime)).getTime();						
				var diffTime=new Date().getTime()-serverTime;
				clearInterval(window.countDownTask);
				window.countDownTask=setInterval(function(){ 
				   $(".countDown").each(function(){ 
					    var obj = $(this); 
					    var id=obj.attr('id'); 
					    var startTime = parseInt(obj.attr('value')); 
					    var endTime = parseInt(obj.attr('endTime')); 	
					    var localTime= new Date().getTime();
						localTime=localTime-diffTime;						
					    var nMS=startTime-localTime ;  
					    var myD=Math.floor(nMS/(1000 * 60 * 60 * 24)); // 天
					    var myH=Math.floor(nMS/(1000*60*60)) % 24; // 小时
					    var myM=Math.floor(nMS/(1000*60)) % 60; // 分钟
					    var myS=Math.floor(nMS/1000) % 60; // 秒
					    var myMS=Math.floor(nMS/100) % 10; // 拆分秒
					    var str='';									   
					    if(startTime>localTime){// 未开始
					    	str = myD+"天"+myH+"小时"+myM+"分"+myS+"秒"; 
					    	$('[seckillid="'+id+'"]').linkbutton('disable'); 
						}else if(startTime<localTime&&localTime<endTime){// 进行中
							str='进行中	';	
							$('[seckillid="'+id+'"]').linkbutton('enable');  
						}else{// 结束
							str='已结束';
							$('[seckillid="'+id+'"]').linkbutton('disable');  
						}				   
					    obj.html(str); 
				   }); 
				}, 1000); // 每个1秒执行一次
			} 
 			
		});
	},seckill:function(seckillId){
		
		$.ajax({
	        type: "POST",
	        url: "shop/secKill/secKill",
	        dataType: "json",
	        data: "seckillId="+seckillId,            
	        success: function(r){
	        	init.messager(r);
	        }
		});
	}
}
$(function() {
	seckill_list.init();
	$("#shop #addSecKill").on("click", function(){	
		var index=$("#shop #productId:checked").val();
		if(typeof(index)=='undefined'||!index){
			init.messager("请选择要进行秒杀的商品!");
		}else{
			var product=$('#shop #productlistdg').datagrid('getData').rows[index];			
			init.winshow('shopaddSecKillwin','创建秒杀活动',500,400,'shop/product/seckill_add.html',product);
		}		
	});
});