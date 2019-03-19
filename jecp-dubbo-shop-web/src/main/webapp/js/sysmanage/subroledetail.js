/**
 * 子角色列表详细页面
 */
if(!subroledetaillist){
	var subroledetaillist={};
}
subroledetaillist={
		init:function(){
			$('#subroledetailform').find('#rolename').textbox({       
			    iconCls:'other-role', 
			    iconAlign:'left',
			    width:300,
			    value:rolelist.rolename,
			    readonly:true
			});
			$('#subroledetailform').find('#roleid').val(rolelist.roleid);
			$('#subroledetailform #subrolename').datagrid({ 			   
				fitColumns:true,
				checkbox: false, 
			    showHeader:false,
			    scrollbarSize:0,
			    nowrap:true,
			    striped:false,
			    border:false,
			    height:80,
			    columns:[[    
		              {field:'roleid',hidden:true},
		              {field:'deptid',hidden:true},
		              {field:'deptname',hidden:true},
		              {field:'subrolename',title:'子角色名称',width:200,
		            	  editor:{
								type:'combobox',
								options:{
									valueField:'productid',
									textField:'productname',
									required:true
								}
							}
		              },    			               
			    ]]  
			});  
			$('#subroledetailform').find("#deptid").combotree({    
			    url:'depttree',  
			    width:300,
			    height:60,
			    valueField:'id',    
			    textField:'text',
			    checkbox:true,
			    multiple:true,
				cascadeCheck:false,
			    onChange:function(newValue, oldValue){
			    	$('#subroledetailform #subrolename').datalist('loadData',{total:0,rows:[]});
			    	var rows=$('#subroledetailform #deptid').combotree('tree').tree('getChecked');	
			    	$.each(rows,function(idx,d){	
			    		$('#subroledetailform #subrolename').datalist('appendRow',{
				    		roleid: rolelist.roleid,
				    		deptid:d.id,
				    		deptname:d.text,
				    		subrolename: "("+rolelist.parentname+"-"+$('#subroledetailform').find('#rolename').combotree('getText')+")"+
			    			d.text
				    	});
			    	});			    				    
			    }   
			});  
		
		},//添加子角色
		addsubrole:function(){
			var json=$('#subroledetailform #subrolename').datalist('getData');
			$.ajax({
				  type:"post",
				  url: 'subroleadd?r='+Math.random(),
				  contentType:"application/json;charset=utf-8",//json格式提交
				  data:JSON.stringify({"subroles":json.rows}),
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
		}
}
$(function () {	
	subroledetaillist.init();
	$('#subroledetailform').find("#subroledetailbtn").on("click", function(){			
		subroledetaillist.addsubrole();
	});
});