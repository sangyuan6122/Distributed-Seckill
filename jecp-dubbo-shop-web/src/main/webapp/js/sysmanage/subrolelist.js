/**
 * 子角色列表页面
 */
if(!subrolelist){
	var subrolelist={};
}
subrolelist={
		subroleid:'',
		init:function(){
			$('#subrolelistdg').datagrid({    
			    url:'subrolegetByRoleId?roleid='+rolelist.roleid, 
			    height:400,
			    border:false,
			    striped:true,
			    fitColumns:true,
			    scrollbarSize:0,
			    nowrap:true,
			    pagination:true,
			    checkOnSelect:false,
			    selectOnCheck:true,
			    toolbar:'#subrolelisttb',
			    columns:[[    
			        {checkbox:true,field:'id',title:'ID',width:100},
			        {field:'subrolename',title:'子角色列表',width:350},
			        {field:'deptname',title:'部门名称',width:250}    
			          
			    ]],
			    loadFilter: function(data){	
			    
			    	$.each(data.rows, function(i,d){
			    		if (data.rows[i]){
			    			data.rows[i].subrolename='<img src="themes/icons/other/subrole.png">'+data.rows[i].subrolename;
							return data;
						}else{
							return data;
						} 
			    	 });
			    	return data;
				},onDblClickRow: function(index, row){//双击
					$("#subrolelistdlshow").show();
					subrolelist.subroleid=row.id;
					$('#subrolelistdl').datalist('reload','usubroleget?subroleid='+row.id); 
					
				}

			});
			$('#subrolelistdl').datalist({ 
			    checkbox: false, 
			    border:true,
			    lines: true ,
			    margin:'10px',
			    title:"("+rolelist.parentname+"-"+rolelist.rolename+")角色的人员列表",
			    width:300,	
			    height:210,
			    style:{
			    	margin:'10px',
			    	'overflow-y':'auto',
			    },
			    columns:[[    
		              {field:'id',hidden:true},    
		              {field:'subroleid',hidden:true},    
		              {field:'username',width:100}    
			    ]],
			    loadFilter: function(data){			    	
			    	$.each(data, function(i,d){
			    		if (data[i]){
							data[i].username='<img src="themes/icons/other/people.png">'+data[i].username;
							return data;
						}else{
							return data;
						} 
			    	 });
			    	return data;
				}
			});  
		},del:function(){
			var row=$("#subrolelistdg").datagrid('getChecked');
			var ids=[];
			$.each(row,function(idx,data){
				ids.push(data.id);
			});
			if(ids.length<=0){
				$.messager.alert('提示','请选择!');
				return;
			}			
			$.messager.confirm('确认','您已选择'+ids.length+'条记录，确认要删除?',function(r){    
			    if (r){    
			    	$.ajax({
						  type: "post",
						  url: 'subroledel?r='+Math.random(),
						  data:{
							  ids:ids
						  },
						  traditional:true,
						  success: function(msg){
							  $("#subrolelistdg").datagrid('reload');   
						  }
					});    
			    }    
			});
		}
}
$(function () {	
	subrolelist.init();
	$("#subrolelistbtn").bind('click', function(){  
		init.winshow('subrolelistwin','角色人员列表',600,486,'sysmanage/subroleuserlist.html','',function(){
			$('#subrolelistdl').datalist('reload');
		});	
	});
	$("#subrolelisttb #addbtn").bind('click', function(){  		
		init.winshow('subroledetailwin','新建子角色','520','340','sysmanage/subroledetail.html','',function(){
			$('#subrolelistdg').datalist('reload');
		});	
	});
});