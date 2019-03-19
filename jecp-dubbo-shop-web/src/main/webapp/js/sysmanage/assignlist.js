/**
 * 权限管理页面
 */
if(!assignlist){
	var assignlist={};
}
assignlist={
		ownerid:'',//选择部门时为deptid,选择人员为userid
		ownername:'',
		type:'',//分配部门或人员
		//添加左部区域
		addWest:function(){
			var w =$('#center').width();
			$('#assignmain').layout('resize',{width:w-212});//重设tabs中center宽度
			$('#assignmain').layout('add',{    
				doSize:true,
				region: 'west',    
			    width: $('#assignmain').width()-600,
			    title:'部门&人员列表',
			    split: false,
			    id:'assignwest',
			    iconCls:'other-dept-people',
			    style:{
			    	margin:0,
			    	float:'left'
			    }
			}); 
			$('#assignwest').html('<ul id="assigntree"></ul>');
			assignlist.addMenuTree();		
		},//添加人员树
		addMenuTree:function(){
			$('#assigntree').tree({   
				url:"usertree?deptid=-999",
				animate:true,
				lines:false,
				onContextMenu: function(e, node){					
					e.preventDefault();		
					if(node.attributes!=null&&'a1' in node.attributes&&'user'==node.attributes.a1){//选择人员
						$('#assigntree').tree('select', node.target);			
						$('#assignupdatemenu').menu('show', {
							left: e.pageX+5,
							top: e.pageY+10
						});							
						assignlist.type='user';
						assignlist.ownerid=node.attributes.a2;
						assignlist.ownername=node.text;
					}else{//选择部门
						$('#assigntree').tree('select', node.target);			
						$('#assignaddmenu').menu('show', {
							left: e.pageX+5,
							top: e.pageY+10
						});	
						assignlist.type='dept';
						assignlist.ownerid=node.id;
						assignlist.ownername=node.text;
					}								
				},onLoadSuccess: function(){				
					var n = $('#assigntree').tree('find', assignlist.ownerid);
					if(n!=null){
						$('#assigntree').tree('expandAll', n.target);
					}						
				},onBeforeExpand: function(node, param){					
					$(this).tree('options').url="usertree?deptid="+node.id;
				}
			}); 
		},//添加选项卡(人员操作)
		addTab:function(title){	
			if ($('#assigntabs').tabs('exists', title)){
				$('#assigntabs').tabs('close', title);
				$('#assigntabs').tabs('add',{
					title:title,
					href:'sysmanage/assigndetail.html',
					closable:true
				});
			}else {
				$('#assigntabs').tabs('add',{
					title:title,
					href:'sysmanage/assigndetail.html',
					closable:true
				});
			}
		},//人员操作(右键)
		updatemenu:function(type){
			if("dept"==type){
				assignlist.addTab("分配部门权限");
				$('#assigntabs').tabs('close', "分配人员权限"); 				
			}else if("user"==type){
				assignlist.addTab("分配人员权限");
				$('#assigntabs').tabs('close', "分配部门权限"); 				
			}
		}
}
$(function () {
	$.parser.parse();
	assignlist.addWest();
});