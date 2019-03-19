/**
 * I6000许可页面
 */
if(!licence){
	var licence={};
}
licence={
		
		addWest:function(){
				
		},
}
$(function () {
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+m+'-'+d;
	};
	$("#licencebtn").on("click", function(){
		$('#licenceform').form({    
		    url:'../usergeti6000lic',    
		    onSubmit: function(){    
		       return $(this).form('validate');  
		    },    
		    success:function(data){    
		    	var d=eval("("+data+")");
		    	if("0"==d.status){
		    		$('#licence').textbox('setValue', '输入错误，请重新输入!');		    		
		    	}else if(d.licence){
		    		$('#licence').textbox('setValue',d.licence);
		    	}
		    	
		    }    
		});    
		// submit the form    
		$('#licenceform').submit();  
	});

});