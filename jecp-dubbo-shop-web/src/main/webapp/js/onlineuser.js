/**
 * 在线用户页面脚本
 */
if(!onlineuser){
	var onlineuser={};
}
onlineuser={
		//在线用户
		init:function(){
			$('#onlineuserdg').datagrid({    
			    url:'useronline', 
			    rownumbers:true,
			    fitColumns:true,
			    scrollbarSize:0,
			    nowrap:false,
			    singleSelect:true,
			    columns:[[    
			        {field:'username',title:'用户名称',width:100},
			        {field:'deptname',title:'部门',width:100}, 
			        {field:'loginip',title:'登陆IP',width:100},    
			        {field:'lastoperatetime',title:'最后操作时间',width:100}    
			    ]]    
			});
		}
}
$(function () {
//	$.parser.parse();
	onlineuser.init();


});