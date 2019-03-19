/**
 * 创建秒杀活动页面
 */
var id;
if (!seckill_add) {
	var seckill_add = {};
}
seckill_add = {
	id : '',
	init : function() {
		var product=$("#shopaddSecKillwin").panel('options').queryParams;
		$("#seckillAddForm #productId").val(product.productId); 
		$("#seckillAddForm #pictureUrl").attr("src", product.pictureUrl); 
		$("#seckillAddForm #title").html(product.title);
		$("#seckillAddForm #seckillPrice").numberbox({    
		    min:1,    
		    max:product.price,
		    precision:0,
		    required: true
		});  
		$("#seckillAddForm #seckillStock").numberbox({    
		    min:1,   
		    max:product.stock,
		    precision:0,
		    required: true   
		});
		var startTime=new Date();
		var endTime=new Date(new Date(startTime.valueOf()).getTime()+24*60*60*1000);		
		$('#seckillAddForm #startTime').datetimebox({    
		    value: startTime.formate("yyyy-MM-dd HH:mm:ss"),    
		    required: true,    
		    showSeconds: true   
		}); 
		$('#seckillAddForm #endTime').datetimebox({    
		    value: endTime.formate("yyyy-MM-dd HH:mm:ss"),    
		    required: true,    
		    showSeconds: true   
		}); 
		$('#seckillAddForm #codeStrategy').combobox({    
			data:[{"id":"REDIS_OPTIMISTIC_LOCK"},{"id":"REDIS_DISTRIBUTED_LOCK"},{"id":"REDIS_QUEUE"}],    
		    valueField:'id',    
		    textField:'id',
		    panelHeight:80,
		    editable:false,
		    required: true,
		    value:'REDIS_QUEUE',
		});
	},createSecKill:function(){
		$('#seckillAddForm').form({    
		    url:'shop/secKill/add?r='+Math.random(),
		    cache:false,
		    onSubmit: function(){      			    	
		    	return $("#seckillAddForm").form('validate');
		    },    
		    success:function(data){    
		    	var d=eval('('+data+')');
		    	if("1"==d.code){
		    		init.messager("创建完成");
		    		$('#shopaddSecKillwin').window('close'); 
		    	}else if("-1"==d.status){
		    		init.messager("权限不足，请联系管理员！");		    		
		    	}else{
		    		init.messager("添加失败！");		    	
		    	}
		    	  
		    }    
		});
		$('#seckillAddForm').submit();
	}
}
$(function() {	
	seckill_add.init();
	$("#seckillAddForm #save").on("click", function(){	
		seckill_add.createSecKill();
	});
});