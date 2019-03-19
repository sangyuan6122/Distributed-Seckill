/**
 * 权限分配详细页面
 */
if(!assigndetail){
	var assigndetail={};
}
assigndetail={
		menuid2:'',	
	init:function(){
		//分配对象
		$('#ownername').textbox({       
		    iconCls:'icon-tip', 
		    iconAlign:'right',
		    readonly:true,
		    value:assignlist.ownername
		});
		//分配对象ID
		console.log(assignlist.ownerid)
		$('#assigndetailform1').find('#owner').val(assignlist.ownerid);
		//分配对象类型
		$('#assigndetailform1').find('#type').val(assignlist.type);
		$('#assigndetailpanel1').panel({    
			  width:220,    
			  height:506, 
			  title: '未分配菜单方案',
			  content:'<table id="assigndetaildg1"></table>'
		}); 
		$('#assigndetailpanel2').panel({    
			  width:220,    
			  height:506, 
			  title: '已分配菜单方案',
			  content:'<table id="assigndetaildg2"></table>'
		}); 		
		$('#assigndetaildg1').datagrid({    
		    url:'privassigngetunassign?owner='+assignlist.ownerid,  
		    height:461,
		    fitColumns:true,
		    singleSelect:true,
		    showHeader:false,
		    scrollbarSize:0,
		    nowrap:true,
		    striped:false,
		    columns:[[    
		        {field:'id',title:'id',hidden:true},    
		        {field:'menuname',title:'menuname',width:100}  
		    ]],
		    onClickRow:function(rowIndex, rowData){
		    	$('#assigndetailform1').find("#menuid").val(rowData.id);
		    	$('#assigndetailform1').find("#menuname").val(rowData.menuname);
		    }    
		}); 
		$('#assigndetaildg2').datagrid({    
			url:'privassigngetassigned?owner='+assignlist.ownerid,
			height:461,
		    fitColumns:true,
		    singleSelect:true,
		    showHeader:false,
		    scrollbarSize:0,
		    nowrap:true,
		    striped:false,
		    columns:[[    
				{field:'id',title:'id',hidden:true},    
				{field:'menuname',title:'menuname',width:100,styler:function(value,row,index){
					return 'border:0';
				}}   
		    ]],
		    onClickRow:function(rowIndex, rowData){
		    	assigndetail.menuid2=rowData.id;
		    }		    	
		}); 
	},//重新加载左右DG
	reload:function(){
		$('#assigndetailform1').find("#menuid").val('');
    	$('#assigndetailform1').find("#menuname").val('');
    	assigndetail.menuid2='';
		$('#assigndetaildg1').datagrid('reload'); 
		$('#assigndetaildg2').datagrid('reload'); 
	}
		
}

$(function () {
	assigndetail.init();	
	$("#assigndetailbtn1").on("click", function(){//添加权限方案	
		$('#assigndetailform1').form({    
			url: "privassignadd", 	   
		    onSubmit: function(){    
		    	if($('#assigndetailform1').find("#menuid").val()==''){
					$.messager.alert('提示','请选择分配方案!');
					return false;
				}
		    },    
		    success:function(data){  				
				var m=eval('('+data+')');
				if('1'==m.status){
					assigndetail.reload();
				}else if('2'==m.status){
					assigndetail.reload();
					$.messager.alert('提示','数据已存在，无需重复添加!');					
				}else{
					$.messager.alert('警告','数据错误，请联系管理员!'); 
				}
		    }    
		});      
		$('#assigndetailform1').submit();  	 	
	});
	$("#assigndetailbtn2").on("click", function(){
		if(assigndetail.menuid2==''){
			$.messager.alert('提示','请选择要删除对象!');
			return;
		}
		$.ajax({
		   type: "POST",
		   url: "privassigndel",
		   data: "menuid="+assigndetail.menuid2+"&owner="+$('#assigndetailform1').find('#owner').val(),
		   success: function(data){
			   var m=eval('('+data+')');
			   if('1'==m.status){				   
				   assigndetail.reload();
			   }else{
				   $.messager.alert('警告','数据错误，请联系管理员!');
				   assigndetail.reload();
			   }			  
		   }
		});
	});
	
});