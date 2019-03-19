
if(!login){
	var login={};
}
login={
	encrypt:{},
	init:function(){
		$('#login').form({   
			type: "POST",
		    url:'userlogin',  
		    onSubmit: function(){    		    	
		        return true;  
		    },    
		    success:function(data){ 
		    	var d = eval("("+data+")");	    	
		    	if(d.status=="1"){
		    		window.location.href="index.html";
		    		return;
		    	}
		    	$("#pwd").val('');
		    	$('#logintips').css({display:'inline'});
		    	return;
		    }    
		});		
	},login:function(){
		var encrypt=new JSEncrypt();
		encrypt.setPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKw1fu5cSRGtHf4sSomHS3QOGWyhVB6InYT1aZ/bzW81AK2O4doS1459/ApIcEx3vRMDi3ffhwiqEAzyGb9lhNUCAwEAAQ==");
		var thisPwd = encrypt.encrypt($("#pwd").val());			
	  	$('#login').form('submit', {    	  	   
	  	    onSubmit: function(param){    
	  	    	param.password = thisPwd;    	  	   
	  	    }    
	  	});  		
	}
}
$(function () {
	login.init();
	$('#sbmtbtn').bind('click', function(){    
	  	login.login();
	});    
	$("input").bind('keydown', function(e){
		if (e.keyCode == 13){
			login.login();
		}
	});
});