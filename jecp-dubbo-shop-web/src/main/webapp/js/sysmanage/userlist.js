/**
 * 人员管理页面
 */
if(!userlist){
	var userlist={};
}
userlist={
		id:'',//选择部门时为deptid,选择人员为人员表主键
		deptid:'',//选择部门时部门ID或选择人员所属部门ID
		deptname:'',//部门名称
		//添加左部区域
		addWest:function(){
			var w =$('#center').width();
			$('#usermain').layout('resize',{width:w-182});//重设tabs中center宽度
			$('#usermain').layout('add',{    
				doSize:true,
				region: 'west',    
			    width: $('#usermain').width()-400,
			    title:'人员列表',
			    split: false,
			    id:'userwest',
			    iconCls:'icon-add',
			    style:{
			    	margin:0,
			    	float:'left'
			    }  
			}); 
			$('#userwest').html('<ul id="usertree"></ul>');
			userlist.addMenuTree();		
		},//添加人员树
		addMenuTree:function(){
			$('#usertree').tree({   
				url:"usertree?deptid=-999",
				animate:true,
				lines:false,
				onContextMenu: function(e, node){
					e.preventDefault();							
					if(node.attributes!=null&&node.attributes.a1=='user'){//选择人员
						$('#usertree').tree('select', node.target);			
						$('#userupdatemenu').menu('show', {
							left: e.pageX+5,
							top: e.pageY+10
						});	
						userlist.deptid=node.pid;
						userlist.deptname =$("#usertree").tree('getParent',node.target)==null?"1":$("#usertree").tree('getParent',node.target).text;
					}else{//选择部门
						$('#usertree').tree('select', node.target);			
						$('#useraddmenu').menu('show', {
							left: e.pageX+5,
							top: e.pageY+10
						});	
						userlist.deptid=node.id;
						userlist.deptname=node.text;
					}
					userlist.id=node.id;		
				
				},onLoadSuccess: function(){				
					var n = $('#usertree').tree('find', userlist.id);
					if(n!=null){
						$('#usertree').tree('expandTo', n.target);
						$('#usertree').tree('expand', n.target);
					}						
				},onBeforeExpand: function(node, param){					
					$(this).tree('options').url="usertree?deptid="+node.id;
				}
			}); 
		},//添加选项卡(人员操作)
		addTab:function(title){	
			if ($('#usertabs').tabs('exists', title)){
				$('#usertabs').tabs('close', title);
				$('#usertabs').tabs('add',{
					title:title,
					href:'sysmanage/userdetail.html',
					closable:true
				});
			}else {
				$('#usertabs').tabs('add',{
					title:title,
					href:'sysmanage/userdetail.html',
					closable:true
				});
			}
		},//人员操作(右键)
		updatemenu:function(type){
			if("insert"==type){
				userlist.addTab("新增人员");
				$('#usertabs').tabs('close', "人员修改"); 				
			}else if("update"==type){
				userlist.addTab("人员修改");
				$('#usertabs').tabs('close', "新增人员"); 				
			}else if("delete"==type){//删除人员节点
				$.messager.confirm('确认','您确认要删除所选人员吗?',function(r){    
				    if (r){    
				    	$.ajax({
							type:"get",
							async:false,
							url:"userdel",
							data:"id="+userlist.id+"&r="+Math.random()*10000,
							success:function(msg){
								$('#usertree').tree('reload');
							}
						 });    
				    }    
				}); 
				
			}
		},syncrtx:function(){
			$.ajax({
				type:"get",
				url:"usersyncRtxAll",
				data:"r="+Math.random()*10000,
				success:function(msg){
					var r=eval('('+msg+')');
					if(r.status=='1'){
						$.messager.alert('提示','同步完成，共'+r.msg+'条!'); 
					}else{
						$.messager.alert('提示','执行失败，请联系管理员!'); 
					}					
				}
			 });  
		}
}
$(function () {
	$.parser.parse();
	userlist.addWest();
});