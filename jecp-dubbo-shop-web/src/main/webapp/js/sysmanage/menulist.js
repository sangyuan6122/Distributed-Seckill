//var dicthtml="<div id='dictDetail'><form method='post'><table><tr><td>父字典值:<br><input class='easyui-textbox' name='name'></input></td></tr><tr><td>字典值：<br><input class='easyui-textbox' name='name'></input></td></tr><tr><td>字典名称:<br><input class='easyui-textbox' name='phone'></input></td></tr><tr><td>字典说明：<br><input class='easyui-textbox' data-options='multiline:true' style='width:150px;height:100px' name='addr'></input></td></tr></table></form></div><div style='text-align:center;padding:5px; width: 136px'><a href='javascript:void(0)' onclick='saveUser()' class='easyui-linkbutton' id='btn-save' icon='icon-save'>保存</a></div>";
/**
 * 字典管理页面
 */
if(!menulist){
	var menulist={};
}
menulist={
		clienttype:'C',
		//添加左部区域
		addWest:function(){
			var w =$('#center').width();
			$('#menumain').layout('resize',{width:w-182});//重设tabs中center宽度
			$('#menumain').layout('add',{    
				doSize:true,
				region: 'west',    
			    width: $('#menumain').width()-300,
			    title:'菜单树展示',
			    split: false,
			    id:'menuwest',
			    iconCls:'other-menu-tree',
			    style:{
			    	margin:0,
			    	float:'left'
			    },tools:[{
						iconCls:'other-computer',
						handler:function(){
							$('#menutabs').tabs('close', "更新菜单"); 
							$('#menutabs').tabs('close', "新增菜单"); 
							menulist.clienttype='C';
							$('#menu1').tree({url:"prvmenuTree?pid=-999&showAll=1"});
						}
					},{
						iconCls:'other-mobile',
						handler:function(){
							$('#menutabs').tabs('close', "更新菜单"); 
							$('#menutabs').tabs('close', "新增菜单"); 
							$('#menu1').tree({url:"prvmenuTree?pid=-998&showAll=1"});
							menulist.clienttype='M';
						}
					}]  
			}); 
			$('#menuwest').html('<ul id="menu1"></ul>');
			menulist.addMenuTree();
			//$("#dictwest").layout().css('height','300');			
		},//添加菜单树
		addMenuTree:function(){
			$('#menu1').tree({   
				url:"prvmenuTree?pid=-999&showAll=1",
				animate:true,
			    lines:false,
			    headerCls:'menusetlist-header',
				onContextMenu: function(e, node){
					e.preventDefault();
					// 查找节点
					$('#menu1').tree('select', node.target);
					// 显示快捷菜单
					$('#rightmenu').menu('show', {
						left: e.pageX+5,
						top: e.pageY+10
					});
					$("#rightmenu").attr("pid",node.id);
				},onBeforeExpand:function(node,param){
					$('#menu1').tree('options').url="prvmenuTree?&showAll=1&pid="+node.id
				},
				onClick:function(node){
					if(node.state=='closed'){				
						$('#menu1').tree('expand',node.target);
					}else{
						$('#menu1').tree('collapse',node.target);
					}
				}				
			}); 
		},//添加选项卡(字典操作)
		addTab:function(title,type){	
			
			if ($('#menutabs').tabs('exists', title)){
				$('#menutabs').tabs('close', title);
				$('#menutabs').tabs('add',{
					title:title,
					href:'sysmanage/menudetail.html',
					closable:true
				});
			}else {
				$('#menutabs').tabs('add',{
					title:title,
					href:'sysmanage/menudetail.html',
					closable:true
				});
			}
		},//菜单操作(右键)
		updatemenu:function(type){			
			var dictid=$('#menu1').tree('getSelected');
			if("insert"==type){
				menulist.addTab("新增菜单","insert");
				$('#menutabs').tabs('close', "更新菜单"); 				
			}else if("update"==type){
				menulist.addTab("更新菜单","update");
				$('#menutabs').tabs('close', "新增菜单"); 				
			}else if("delete"==type){//删除菜单节点
				$.messager.confirm('确认','您确认要删除当前及所有子菜单吗?',function(r){    
				    if (r){    
				    	var rootid='-1';
				    	if(menulist.clienttype=='C'){
				    		rootid='-1';
				    	}else{
				    		rootid='-2';
				    	}
				    	$.ajax({
							type:"get",
							async:false,
							url:"prvdelTree",
							data:"pid="+$("#rightmenu").attr("pid")+"&r="+Math.random()*10000,
							success:function(msg){
								var node = $('#menu1').tree('find', rootid);
								$('#menu1').tree('reload', node.target);
							}
						 });    
				    }    
				}); 
				
			}
		}
}
$(function () {
	$.parser.parse();
	menulist.addWest();
	

});