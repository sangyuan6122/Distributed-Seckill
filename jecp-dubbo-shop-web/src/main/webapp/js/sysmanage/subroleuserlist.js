/**
 * 子角色人员列表页面
 */
if(!subroleuserlist){
	var subroleuserlist={};
}
subroleuserlist={
		init:function(){
			$('#subroleusertree').tree({   
				url:"usertree",
				animate:true,
			    lines:false,
			    checkbox:true
			    ,onLoadSuccess:function(node, data){
					var nodes=$(this).tree("getChildren");
					$.each(nodes,function(idx,n){
						if(n&&n.attributes!=null&&n.attributes.a1!='user'){
							$('#subroleusertree').tree("disableCheck",n.id);
						}
					});					
				}
			}); 
			$('#subroleuserdl').datalist({ 
				 url:'usubroleget?subroleid='+subrolelist.subroleid,
				 checkbox:false, 
				 border:false,
				 lines: true ,
				 headerCls:'other-subroleuserlist-header',
				 columns:[[    
			           {field:'id',hidden:true},  
			           {field:'roleid',hidden:true},
			           {field:'subroleid',hidden:true},
			           {field:'userid',hidden:true}, 
			           {field:'username',hidden:false},   
				 ]],onDblClickRow:function(index, row){
					 $.ajax({
						type:"get",
						async:true,
						url:"usubroledel",
						data:"id="+row.id+"&r="+Math.random()*10000,
						success:function(msg){							
							$('#subroleuserdl').datalist('reload');
						}
					}); 
				 }
			});  

			$('#subroleuserlist-panel-west').panel({    
				  width:250,    
				  height:430,  
				  closable:false,    
	              collapsible:false,
				  title: '用户', 
				}); 
			
			$('#subroleuserlist-panel-center').panel({    
				  width:60,    
				  height:230, 
				  border:false,
				  iconCls:'icon-redo',
				  style:'{float:left}'
				}); 
			$('#subroleuserlist-panel-east').panel({    
				  width:250,    
				  height:430,  
				  title:'已选择(双击名称删除)',
				  closable:false,    
	              collapsible:false,
	              style:'{float:left}'
				});   

		},del:function(){
			
		},select:function(){
			var nodes = $('#subroleusertree').tree('getChecked');				
			if(nodes.length==0){
				$.messager.alert('提示','请选择人员!');  
				return;
			}
			var obj;
			var subusers=new Array();
			for(var j=0;j<nodes.length;j++){				
				if(!subroleuserlist.findDatalist(nodes[j].attributes.a2)){
					continue;
				}else{						
					obj={};
					obj.id=nodes[j].id;					
					obj.username=nodes[j].text;
					obj.roleid=rolelist.roleid;
					obj.subroleid=subrolelist.subroleid;
					obj.userid=nodes[j].attributes.a2;						
					$("#subroleuserlistwin #subroleuserdl").datalist('appendRow',obj);
					subusers.push(obj);
				}
			}			
			if(subusers.length>0){
				$.ajax({
					  type:"post",
					  url: 'usubroleadds?r='+Math.random(),
					  contentType:"application/json;charset=utf-8",//json格式提交
					  data:JSON.stringify({"subroleusers":subusers}),
					  traditional:true,
					  success: function(data){
						  var r=eval('('+data+')');
						  if(r.status=='1'){
							  $("#subroledetailwin").window('close');
						  }else if(r.status=='2'){					
							  $.messager.alert('添加失败','子角色重复:<br>'+r.msg.toString());  
						  }
					  }
				});
			}else{
				$.messager.alert('提示','子角色中部分人员已存在!');
			}
			
			
		},findDatalist: function (value){//排重
			var hangs=$("#subroleuserlistwin #subroleuserdl").datalist('getRows');			
			for(var k=0;k<hangs.length;k++){
				if(hangs[k].userid==value){
					return false;
				}else{
					continue;
				}
			}
			return true;
		}
}
$(function () {	
	subroleuserlist.init();	
	$("#subroleuserlistwin #addbtn").on("click", function(){	
		subroleuserlist.select();
	});
});