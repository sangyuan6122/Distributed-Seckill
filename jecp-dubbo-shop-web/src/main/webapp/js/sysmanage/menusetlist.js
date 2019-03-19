/**
 * 部门管理页面
 */
if(!menusetlist){
	var menusetlist={};
}
menusetlist={
	menuid:'',//菜单方案ID
	type:'',//新增或更新
	clienttype:'C',
	//添加左部区域
	addWest:function(){
		var w =$('#center').width();
		$('#menusetlistmain').layout('resize',{width:w-222});//重设tabs中center宽度
		$('#menusetlistmain').layout('add',{    
			doSize:true,
			region: 'west',    
		    width: $('#menusetlistmain').width()-650,
		    title:'菜单方案',
		    split: false,
		    id:'menusetwest',
		    iconCls:'icon-all',
		    style:{
		    	margin:0,
		    	float:'left'
		    }  
		}); 
		$('#menusetwest').html('<table id="menusetlistdg"></table> ');
		menusetlist.addDG();			
	},//添加菜单方案列表
	addDG:function(){
		$('#menusetlistdg').datagrid({    
		    url:'privmenuload',   		
		    pagination:true,
		    fitColumns:true,
		    scrollbarSize:0,
		    nowrap:true,
		    style:{margin:'10px'},
		    width:385,
		    singleSelect:true,
		    loadMsg:'加载中...',
		    toolbar: [{
				iconCls: 'icon-add',text:'新建菜单方案',
				handler: function(){
					menusetlist.type='insert';
					init.winshow('menusetdetailwin','新建菜单方案',410,232,'sysmanage/menusetdetail.html');
				}
			},'-',{
				iconCls: 'icon-edit',text:'更新',
				handler: function(){
					menusetlist.type='update';
					if(''==menusetlist.menuid){
						$.messager.alert('提示','请选择要更新的菜单方案');
					}else{
						init.winshow('menusetdetailwin','更新菜单方案',410,232 ,'sysmanage/menusetdetail.html');
					}						
				}
			},'-',{
				iconCls: 'icon-remove',text:'删除',
				handler: function(){
					if(''==menusetlist.menuid){
						$.messager.alert('提示','请选择要删除菜单方案'); 
					}else{
						$.messager.confirm('确认','您确认要删除菜单方案吗？',function(r){    
						    if (r){    
						    	menusetlist.del(menusetlist.menuid); 
						    }    
						});  	
					}
				}
			}],
		    columns:[[    
		        {field:'id',hidden:true},    
		        {field:'menuname',title:'名称(双击查看)',width:120},
		        {field:'clienttype',title:'客户端类型',width:70},
		        {field:'remark',title:'备注',width:120}  
		    ]],
		    onClickRow:function(rowIndex, rowData){
		    	menusetlist.menuid=rowData.id;
		    },
		    onDblClickRow:function(rowIndex, rowData){
		    	menusetlist.menuid=rowData.id;
		    	if(rowData.clienttype=='移动端'){
		    		menusetlist.clienttype='M';
		    	}else{
		    		menusetlist.clienttype='C';
		    	}		    	
		    	menusetlist.addTab('编辑详细菜单方案','sysmanage/menusetupdate.html');			    	
		    }    
		}); 
		
		var p =$('#menusetlistdg').datagrid('getPager');
		$(p).pagination({
			pageSize:10,
			beforePageText:'',
			afterPageText:'',
			displayMsg:'',
			showPageList:false
		});
	},//添加选项卡(部门操作)
	addTab:function(title,url){				
		if ($('#menusetlisttabs').tabs('exists', title)){
			$('#menusetlisttabs').tabs('close', title);
			$('#menusetlisttabs').tabs('add',{
				title:title,
				href:url,
				closable:true
			});
		}else {
			$('#menusetlisttabs').tabs('add',{
				title:title,
				href:url,
				closable:true
			});
		}
	},//删除菜单方案
	del:function(menuid){			
		$.ajax({
			type: "POST",
			url: "privmenudel",
			data: "id="+menusetlist.menuid,
			cache: false,
			success: function(data){
				var d=eval('('+data+')');
		    	if("1"==d.status){			    		
		    		menusetlist.reload();
		    		$('#menusetlisttabs').tabs('close', '编辑详细菜单方案');
		    	}else{
		    		$.messager.alert('提示','数据错误，请联系管理员！'); 
		    	}	
			}
		});
	},//刷新menusetlistdg
	reload:function(){
		$('#menusetlistdg').datagrid('reload');
		menusetlist.menuid='';
	}
}
$(function () {
	$.parser.parse();
	menusetlist.addWest();
});


