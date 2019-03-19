/**
 * 数据人员详细页面
 */
var type;//定义操作类型(更新、新增)
if(!userdetail){
	var userdetail={};
}
userdetail={
	init:function(){
		$("#userform #deptname").textbox({
			readonly:true
		});
		$("#userform #external").combobox({
			data:[{"external":false,"text":'否'},{"external":true,"text":'是'}],
			valueField:'external',    
		    textField:'text'   
		});
	},addTree:function(){//添加人员菜单			
		$('#userform').form({    
		    url:'useradd',    
		    onSubmit: function(){    
		    	
		    },    
		    success:function(data){ 
		    	var d =eval('('+data+')');
		    	if('1'==d.status){
		    		$.messager.alert('提示','添加成功!'); 
		    	}else{
		    		$.messager.alert('提示','添加失败!'); 
		    	}
		    	$('#usertree').tree('reload');				
				
				$('#usertabs').tabs('close', "新增人员"); 	
		    }    
		});      
		$('#userform').submit(); 	
	},//获得具体菜单对象
	getTree:function(){		
		$('#userform').form('load','userget?id='+userlist.id);
	},updateTree:function(){			
		$('#userform').form({    
		    url:'userupdate',    
		    onSubmit: function(){    

		    },    
		    success:function(data){ 
		    	var d =eval('('+data+')');
		    	if('1'==d.status){
		    		$.messager.alert('提示','更新成功!'); 
		    	}else{
		    		$.messager.alert('提示','更新失败!'); 
		    	}
				$('#usertree').tree('reload');
				
				userlist.deptid=$("#userform #deptid").val();					

				$('#usertabs').tabs('close', "人员修改"); 
		    }    
		});      
		$('#userform').submit(); 	
	}		
}
/**
 * 判断按钮点击事件，更新或新增
 */
$("#userdetailbtn").on("click", function(){
	if("update"==type){
		userdetail.updateTree();
	}else if("insert"==type){
		userdetail.addTree();
	}
});


$(function () {
	userdetail.init();
	//判断当前操作
	if($('#usertabs').tabs('exists','人员修改')){
		userdetail.getTree();
		type='update';
//		$("#userform").find("#id").val(userlist.id);//设置父节点ID
	}else{
		type='insert';
		$("#userform #deptid").val(userlist.deptid);
		$("#userform #deptname").textbox('setValue',userlist.deptname);
	}
	$("#userdetailgetpwdbtn").on("click", function(){
		$.ajax({
			url: "usergetPwd?id="+userlist.id,
			cache: false,
		    success: function(data){
		    	var d =eval('('+data+')');
		    	$("#userform").find("#password").textbox('setValue', d.pwd);
		    	console.log(d.pwd);
		    }
		});
		
	});

});