/**
 * 角色管理页面
 */
if(!rolelist){
	var rolelist={};
}
rolelist={
		roleid:'',//角色ID
		rolename:'',//角色名称
		parentname:'',//父栏目名称
		type:'',//操作类型
		//添加右部区域
		init:function(){
			$('#rolelistmain').layout('add',{
				doSize:true,
				region: 'west',
				width:'360px',
				title: '角色列表(双击查看子角色)',  
				split:false,
				content:'<ul id="rolelisttree"></ul>'
			});			
			$('#rolelisttree').tree({//添加角色树
				url:"roletree",
				animate:true,
			    lines:false,
				onContextMenu: function(e, node){
					e.preventDefault();
					// 查找节点
					$('#rolelisttree').tree('select', node.target);
					// 显示快捷菜单
					if("menu"==node.attributes.a1){
						$('#rolerightmenu').menu('show', {
							left: e.pageX+5,
							top: e.pageY+10
						});												
					}else{
						$('#rolerightmenu1').menu('show', {
							left: e.pageX+5,
							top: e.pageY+10
						});					
					}
					rolelist.roleid=node.id;
					rolelist.rolename=node.text;
				},onDblClick:function(node){
					if('role'!=node.attributes.a1){
						return;
					}
					rolelist.roleid=node.id;
					rolelist.rolename=node.text;
					var pnode=$("#rolelisttree").tree('getParent',node.target);	
					rolelist.parentname=pnode.text;
					var title='正在修改'+pnode.text+"-"+node.text+"下子角色";
					if($('#rolelisttabs').tabs('exists', 1)){						
						var tab = $('#rolelisttabs').tabs('getTab',1);  // 获取选择的面板						
						$('#rolelisttabs').tabs('update', {
							tab: tab,
							options: {
								title: title,
								href: 'sysmanage/subrolelist.html?r='+Math.random()*10000
							}
						});
						tab.panel('refresh');
					}else{						
						$('#rolelisttabs').tabs('add',{    
						    title:title,    
						    href: 'sysmanage/subrolelist.html?'+Math.random()*10000,    
						    closable:true
						}); 
					} 
					
					 
				}
			}); 
			
		},//角色操作(右键)
		updatemenu:function(type){		
			if("insert"==type){
				init.winshow('rolelistwin','角色新增',460,295,'sysmanage/roledetail.html');	
				rolelist.type='insert';
			}else if("update"==type){
				init.winshow('rolelistwin','角色修改',460,295,'sysmanage/roledetail.html');	
				rolelist.type='update';
			}else if("delete"==type){//删除角色或角色分类
				$.messager.confirm('警告','此操作可能会对系统造成不良影响，您确定要继续?',function(r){    
				    if (r){
				    	$.ajax({				
							async:false,
							url:"roledel",
							data:"roleid="+rolelist.roleid+"&r="+Math.random()*10000,
							success:function(msg){
								$('#rolelisttree').tree('reload');
							}
						 });    
				    }    
				}); 				
			}else if("addsubrole"==type){
				init.winshow('subroledetailwin','角色修改',420,240,'sysmanage/subroledetail.html');
			}else if("selectsubrole"==type){
				
			}
		}
}
$(function () {
	$.parser.parse();
	rolelist.init();
});