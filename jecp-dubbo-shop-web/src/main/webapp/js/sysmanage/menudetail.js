/**
 * 菜单管理详细页面
 */
var type;//定义操作类型(更新、新增)
if(!menudetail){
	var menudetail={};
}
menudetail={
	init:function(){
		$('#privform #attchoose').filebox({
			buttonText: '选择文件',
			accept:'image/jpeg,image/png',
			onChange:function(newValue, oldValue){	
				var fileid=$(this).parent('td').find("input[id^='filebox_file_id_']").attr("id");				
				$.ajaxFileUpload({
		            url: 'privmenuupload?savePath=&filecnname='+newValue+'&uploader='+init.user.userid+'&module=sysmanage&remark=新建派发&r='+Math.random(),             
		            type: 'post',
		            secureuri: false, //一般设置为false
		            parentElementId:'privform',
		            fileElementId: fileid, // 上传文件的id、name属性名
		            dataType: 'json', //返回值类型，一般设置为json、application/json
		            //elementIds: 'commontaskmainattchoose', //传递参数到服务器
		            success: function(data, status){  	
		            	$('#privform #attchoose').filebox('initValue');
		            	$('#privform #attchoose').filebox('clear');
		            	if("success"==status){	
//		            		var burl=data.url.toString().replace(/\\/g,"/");
		            		if(data.url){
		            			var burl=data.url.toString();
			        			$("#privform #preview").css("background-image","url("+burl+")");
			        			$("#privform #ico").val(data.id);
		    				}
		            		
				    	}else if("-1"==status){
				    		//$(".commontaskmain-cover").css({'display':'none'});
				    		$.messager.alert('提示','权限不足，请联系管理员！'); 
				    	}else{
				    		//$(".commontaskmain-cover").css({'display':'none'});
				    		
				    		$.messager.alert('提示','上传失败！'); 
				    	}		
		            	
		            },
		            error: function(data, status, e){ 
		                
		            }
		        });
			}
		});
	},addTree:function(){	//添加菜单		
		$('#privform').form({    
		    url:'prvaddTree',    
		    onSubmit: function(param){   
		    	param.clienttype=menulist.clienttype;
		    	return $(this).form('validate');
		    	
		    },    
		    success:function(data){    
		    	var node;			    	
		    	if(menulist.clienttype=='C'){
		    		node = $('#menu1').tree('find', -1);
		    	}else{
		    		node = $('#menu1').tree('find', -2);			    		
		    	}		
				$('#menu1').tree('reload', node.target);
				$('#menu1').tree('expand', node.target);
				$('#menutabs').tabs('close', "新增菜单"); 
		    }    
		});      
		$('#privform').submit(); 	
	},//获得具体菜单对象
	getTree:function(){		
		$('#privform').form({
			onLoadSuccess:function(data){    
//				 var burl=data.path.toString().replace(/\\/g,"/");
				if(data.path){
					var burl=data.path.toString();
					 
	     			$("#privform #preview").css("background-image","url("+burl+")"); 
				}
				   
			 }
		});
				
		$('#privform').form('load','prvgetTree?pid='+$("#rightmenu").attr("pid"));
	},updateTree:function(){			
		$('#privform').form({    
		    url:'prvupdateTree',    
		    onSubmit: function(param){  
		    	param.clienttype=menulist.clienttype;
		    	return $(this).form('validate');
		    },    
		    success:function(data){    
		    	var node;
		    	if(menulist.clienttype=='C'){
		    		node = $('#menu1').tree('find', -1);
		    	}else{
		    		node = $('#menu1').tree('find', -2);
		    	}
				$('#menu1').tree('reload', node.target);
				$('#menu1').tree('select', node.target);
				$('#menutabs').tabs('close', "更新菜单"); 
		    }    
		});      
		$('#privform').submit(); 	
	}		
}
/**
 * 判断按钮点击事件，更新或新增
 */
$("#btn").on("click", function(){
	if("update"==type){
		menudetail.updateTree();
	}else if("insert"==type){
		menudetail.addTree();
	}
});
$(function () {
	menudetail.init();
	$("#parentprivid").val($("#rightmenu").attr("pid"));//设置父节点ID
	//判断当前操作
	if($('#menutabs').tabs('exists','更新菜单')){
		menudetail.getTree();
		type='update';
	}else{
		type='insert';
	}
	

});