/**
 * 部门管理页面
 */
if(!deptlist){
	var deptlist={};
}
deptlist={
		//添加左部区域
		addWest:function(){
			var w =$('#center').width();
			$('#deptmain').layout('resize',{width:w-182});//重设tabs中center宽度
			$('#deptmain').layout('add',{    
				doSize:true,
				region: 'west',    
			    width: $('#deptmain').width()-300,
			    title:'部门列表',
			    split: false,
			    id:'deptwest',
			    iconCls:'icon-add',
			    style:{
			    	margin:0,
			    	float:'left'
			    } 
			}); 
			$('#deptwest').html('<ul id="depttree"></ul>');
			deptlist.addMenuTree();
			//$("#deptwest").layout().css('height','300');			
		},//添加部门树
		addMenuTree:function(){
			$('#depttree').tree({   
				url:"depttree?parentdeptid=-999",
				animate:true,
				lines:false,
				onContextMenu: function(e, node){
					e.preventDefault();
					// 查找节点
					$('#depttree').tree('select', node.target);
					// 显示快捷菜单
					$('#deptrightmenu').menu('show', {
						left: e.pageX+5,
						top: e.pageY+10
					});
					$("#deptrightmenu").attr("pid",node.id);
					$("#deptrightmenu").attr("rtxparentdeptid",node.attributes.a1);
				},onBeforeExpand: function(node, param){					
					$('#depttree').tree('options').url="depttree?parentdeptid="+node.id;
				},
				onClick:function(node){	
					if(node.state=='closed'){				
						$(this).tree('expand',node.target);
					}else{
						$(this).tree('collapse',node.target);
					}
				}
			}); 
		},//添加选项卡(部门操作)
		addTab:function(title,type){	
			if ($('#depttabs').tabs('exists', title)){
				$('#depttabs').tabs('close', title);
				$('#depttabs').tabs('add',{
					title:title,
					href:'sysmanage/deptdetail.html',
					closable:true
				});
			}else {
				$('#depttabs').tabs('add',{
					title:title,
					href:'sysmanage/deptdetail.html',
					closable:true
				});
			}
		},//部门操作(右键)
		updatemenu:function(type){
			var deptid=$('#depttree').tree('getSelected');
			if("insert"==type){
				deptlist.addTab("新增部门","insert");
				$('#depttabs').tabs('close', "部门修改"); 				
			}else if("update"==type){
				deptlist.addTab("部门修改","update");
				$('#depttabs').tabs('close', "新增部门"); 				
			}else if("delete"==type){//删除部门节点
				$.messager.confirm('确认','您确认要删除当前下所有部门吗?',function(r){    
				    if (r){    
				    	$.ajax({
							type:"get",
							async:false,
							url:"deptdel",
							data:"deptid="+$("#deptrightmenu").attr("pid")+"&r="+Math.random()*10000,
							success:function(msg){
								var r=eval('('+msg+')');
								if('1'==r.status){		
									var selected = $('#depttree').tree('getSelected');
									$('#depttree').tree('remove',selected.target);
								}else{
									init.messager(r.msg);  
								}
								
							}
						 });    
				    }    
				}); 
				
			}
		},syncrtx:function(){
			$.ajax({
				type:"get",
				url:"deptsyncRtx",
				data:"r="+Math.random()*10000,
				success:function(msg){
					var status=eval('('+msg+')');
					if(status.status=='1'){
						$.messager.alert('提示','同步完成!'); 
					}else{
						$.messager.alert('提示','执行失败，请联系管理员!'); 
					}					
				}
			 });  
		}
}
$(function () {
	$.parser.parse();
	deptlist.addWest();
});