/**
 * 个人信息修改
 */
if(!useredit){
	var useredit={};
}
useredit={
		init:function(){
			var user = $.ajax({
				url: "usergetByCookie",
				async: false
			}).responseText;
			user=eval('('+user+')');
			$("#usereditdiv #deptname").html(user.deptname);
			$("#usereditdiv #userid").html(user.userid);
			$("#usereditdiv #username").html(user.username);
			$("#usereditdiv #mobile").html(user.mobile);
		},
		updatepwd:function(){			
			$('#usereditpwdform').form({    
			    url:'userupdatePwd',    
			    onSubmit: function(){    

			    },    
			    success:function(data){ 
			    	var d =eval('('+data+')');
			    	if('1'==d.status){
			    		$.messager.alert('提示','密码更新成功!'); 
			    	}else{
			    		$.messager.alert('提示','密码更新失败,'+d.msg+'!'); 
			    	}					
			    }    
			});      
			$('#usereditpwdform').submit(); 	
		}		
}
$(function () {	
	useredit.init();
	$("#usereditpwdform #usereditpwdbtn").bind('click', function(){  
		useredit.updatepwd();
	});
});