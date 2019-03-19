//var dicthtml="<div id='dictDetail'><form method='post'><table><tr><td>父字典值:<br><input class='easyui-textbox' name='name'></input></td></tr><tr><td>字典值：<br><input class='easyui-textbox' name='name'></input></td></tr><tr><td>字典名称:<br><input class='easyui-textbox' name='phone'></input></td></tr><tr><td>字典说明：<br><input class='easyui-textbox' data-options='multiline:true' style='width:150px;height:100px' name='addr'></input></td></tr></table></form></div><div style='text-align:center;padding:5px; width: 136px'><a href='javascript:void(0)' onclick='saveUser()' class='easyui-linkbutton' id='btn-save' icon='icon-save'>保存</a></div>";
/**
 * 字典管理页面
 */
if(!dictlist){
	var dictlist={};
}
dictlist={
		pid:'',
		//添加左部区域
		addWest:function(){
			var w =$('#center').width();
			$('#dictmain').layout('resize',{width:w-182});//重设tabs中center宽度
			$('#dictmain').layout('add',{    
				doSize:true,
				region: 'west',    
			    width: $('#dictmain').width()-300,
			    title:'数据字典',
			    headerCls:'other-dictlist-header',
			    split: false,
			    id:'dictwest',
			    iconCls:'other-dict-page',
			    style:{
			    	margin:0,
			    	float:'left'
			    }
			}); 
			$('#dictwest').html('<ul id="dicttree"></ul>');
			dictlist.addMenuTree();
			//$("#dictwest").layout().css('height','300');			
		},//添加字典树
		addMenuTree:function(){
			$('#dicttree').tree({   
				url:"dicttree?parentdictid=-999",
				animate:true,
				lines:false,
 				onContextMenu: function(e, node){
					e.preventDefault();
					// 查找节点
					$('#dicttree').tree('select', node.target);
					// 显示快捷菜单
					$('#dictrightmenu').menu('show', {
						left: e.pageX+5,
						top: e.pageY+10
					});
					dictlist.pid=node.id;					
				},onBeforeExpand:function(node,param){
					$('#dicttree').tree('options').url="dicttree?&parentdictid="+node.id
				}
			}); 
		},//添加选项卡(字典操作)
		addTab:function(title,type){	
			if ($('#dicttabs').tabs('exists', title)){
				$('#dicttabs').tabs('close', title);
				$('#dicttabs').tabs('add',{
					title:title,
					tabHeight:33,
					href:'sysmanage/dictdetail.html',
					closable:true
				});
			}else {
				$('#dicttabs').tabs('add',{
					title:title,
					tabHeight:33,
					href:'sysmanage/dictdetail.html',
					closable:true
				});
			}
		},//字典操作(右键)
		updatemenu:function(type){
			var dictid=$('#dicttree').tree('getSelected');
			if("insert"==type){
				dictlist.addTab("新增字典","insert");
				$('#dicttabs').tabs('close', "更新字典"); 				
			}else if("update"==type){
				dictlist.addTab("更新字典","update");
				$('#dicttabs').tabs('close', "新增字典"); 				
			}else if("delete"==type){//删除菜单节点
				$.messager.confirm('确认','您确认要删除当前下所有数据字典吗?',function(r){    
				    if (r){    
				    	$.ajax({
							type:"get",
							async:false,
							url:"dictdel",
							data:"dictid="+dictlist.pid+"&r="+Math.random()*10000,
							success:function(msg){
								var node = $('#dicttree').tree('find', -1);
								$('#dicttree').tree('reload', node.target);
							}
						 });    
				    }    
				}); 
				
			}
		}
}
$(function () {
	$.parser.parse();
	dictlist.addWest();


});
