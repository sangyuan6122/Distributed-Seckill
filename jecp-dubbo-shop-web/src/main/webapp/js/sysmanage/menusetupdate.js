/**
 * 菜单方案详细页面
 */
if(!menusetupdate){
	var menusetupdate={};
}
menusetupdate={
		privid:'',//左菜单ID
		privid2:'',//右菜单ID
		//添加菜单树
		init:function(){				
			$('#menusetp1').panel({    
				  width:260,    
				  height:550, 
				  left:600,
				  title: '所有菜单',
				  content:"<ul id='menusetmenutree1'></ul>"
			}); 
			$('#menusetp2').panel({    
				  width:260,    
				  height:550, 
				  left:600,
				  title: '已分配菜单',
				  content:"<ul id='menusetmenutree2'></ul>"
			});
			menusetupdate.addMenuTree();
		},//添加菜单树
		addMenuTree:function(){			
			var url='prvmenuTree?pid=-999&showAll=1';
			if(menusetlist.clienttype=='M'){
				url='prvmenuTree?pid=-998&showAll=1';
			}
			$('#menusetmenutree1').tree({   
				url:url,
				animate:true,
			    lines:false,
			    dnd:false,
				onBeforeExpand:function(node,param){
					$('#menusetmenutree1').tree('options').url="prvmenuTree?&showAll=1&pid="+node.id
				},
				onSelect:function(node){
					menusetupdate.privid=node.id;
				}
			});

			$('#menusetmenutree2').tree({   
				url:"privitemget?menuid="+menusetlist.menuid+'&clienttype='+menusetlist.clienttype,
				animate:true,
			    lines:false,
				onSelect:function(node){
					menusetupdate.privid2=node.id;
				}
			});
		}
		
}

$(function () {
	menusetupdate.init();
	$('#menusetupdateadd').bind('click', function(){    
        if(''==menusetupdate.privid){
        	$.messager.alert('提示','请选择要分配菜单!');  
        }else{
        	var node = $('#menusetmenutree2').tree('find', menusetupdate.privid);
        	if(node!=null){
        		$.messager.alert('提示','菜单已存在!');
        		return;
        	}
        	$.ajax({
    		   type: "POST",
    		   url: "privitemadd",
    		   data: "menuid="+menusetlist.menuid+"&privid="+menusetupdate.privid,
    		   success: function(data){
    			   var m=eval('('+data+')');
    			   if('1'!=m.status){
    				   $.messager.alert('提示',m.status);
    				   return;
    			   }
    			   $('#menusetmenutree2').tree('reload');
    		   }
    		});
        }		   
    }); 
	$('#menusetupdatedel').bind('click', function(){           
    	
    	if(''==menusetupdate.privid2){
    		$.messager.alert('提示','请在右边菜单树中选择要删除的菜单项!');
    		return;
    	}else if('-1'==menusetupdate.privid2){
    		$.messager.alert('提示','不能删除根节点!');
    		return;
    	}
    	$.ajax({
		   type: "POST",
		   url: "privitemdel",
		   data: "menuid="+menusetlist.menuid+"&privid="+menusetupdate.privid2,
		   success: function(msg){
			   $('#menusetmenutree2').tree('reload');
		   }
		});
        		   
    });
});