(function () {
	/**
	$.fn.panel.defaults.onBeforeDestroy=function(){
		var frame=$('iframe',this);		
		if(frame.length>0){
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			frame=null;
		}
	};**/
	/**
	 * DataGrid扩展
	 * 1、美化表格，去掉最后空列(滚动条占位列)；
	 * 2、增加单元格提示功能；
	 */
	$.extend($.fn.datagrid.methods, {
		beautify: function(jq, index){
			return jq.each(function(){
				var opts = $.data(this, 'datagrid').options;	
				var td='<td id="null" field="null" style="width:20px;height:24px;display:inline-block;"></td>';
				var tr=$(this).parent().find(".datagrid-view2 .datagrid-btable tbody tr");				
				if($(tr).find("#null").length<=0){
					tr.append(td);				
					var btableTd = $('div.datagrid-body td[field="null"]');
					var stableTd=btableTd.prev();
					stableTd.css({'border-right':'none'});
					
					
					var htableTd = $('div.datagrid-header td');
					var lasttableTd=htableTd[htableTd.length-1];
					lasttableTd.style.borderRight='none';
				}
				
			});			
		},tip:function(jq,param){	
			return jq.each(function(){
				var rows = jq.datagrid('getRows');					
				$(param).each(function(i){
					var tableTd =$(jq).parent().find('div.datagrid-body td[field='+param[i]+']'); //content是列名	
					 if(rows!=''){
						 tableTd.each(function (){							 
					         var index = $(this).parent('tr').attr('datagrid-row-index');
					         var currentRow = rows[index];	
					         var tiptext=typeof(currentRow[param[i]])=='string'?currentRow[param[i]]:JSON.stringify(currentRow[param[i]]);
					         if(typeof tiptext=="undefined"||tiptext==''){
					        	 return true;
					         }
					         var content = '<div style=" max-width:250px;word-break: break-all; word-wrap: break-word;">' +tiptext+ '</div>'; //productname是列名
					         $(this).tooltip({
					              wrapColor : 'black',
					              position:'top',
					              deltaX:0,
					              deltaY:15,
					              content:content,
					              wrapColor:'black',
					              onShow: function(){
					    		  var t = $(this);
					    		  t.tooltip('tip').unbind().bind('mouseenter', function(){
					    				t.tooltip('show');
					    				}).bind('mouseleave', function(){
					    					t.tooltip('hide');
					    				});
					    				$(this).tooltip('tip').css({ 
					    					borderColor: '#b46006',
					    					color:'#333',
					    				});
					    		 }
					          });
						 });
						 
					 }
				});
			});
		},editCell : function (jq, param){
		    return jq.each(function (){
		        var opts = $(this).datagrid('options');
		        var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
		        for (var i = 0; i < fields.length; i++){
		            var col = $(this).datagrid('getColumnOption', fields[i]);
		            col.editor1 = col.editor;
		            if (fields[i] != param.field){
		                col.editor = null;
		            }
		        }
		        $(this).datagrid('beginEdit', param.index);
		        var ed = $(this).datagrid('getEditor', param);
		        if (ed){
		            if ($(ed.target).hasClass('textbox-f')){
		                $(ed.target).textbox('textbox').focus();
		            }else{
		                $(ed.target).focus();
		            }
		        }
		        for (var i = 0; i < fields.length; i++){
		            var col = $(this).datagrid('getColumnOption', fields[i]);
		            col.editor = col.editor1;
		        }
		        $(this).datagrid('beautify');
		    });
		},enableCellEditing : function (jq){
		    return jq.each(function (){
		        var dg = $(this);
		        var opts = dg.datagrid('options');
		        opts.oldOnClickCell = opts.onClickCell;
		        opts.onClickCell = function (index, field){
		            if (opts.editIndex != undefined){
		                if (dg.datagrid('validateRow', opts.editIndex)){
		                    dg.datagrid('endEdit', opts.editIndex);
		                    opts.editIndex = undefined;
		                }else{
		                    return;
		                }
		            }
		            dg.datagrid('selectRow', index).datagrid('editCell',{
		                index : index,
		                field : field
		            });
		            opts.editIndex = index;
		            opts.oldOnClickCell.call(this, index, field);
		        }
		    });
		}
	});
    $.extend($.fn.panel.methods, {//panel半透明遮罩
    	
        //显示遮罩
        loading: function (jq, msg) {
            return jq.each(function () {
                var panel = $(this);
                if (msg == undefined) {
                    msg = "正在加载数据，请稍候...";
                }
                $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: panel.width(), height: panel.height(),left:0,top:0 }).appendTo(panel);
                $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(panel).css({ display: "block", left: (panel.width() - $("div.datagrid-mask-msg", panel).outerWidth()) / 2, top: panel.height()/ 2 });
            });
        },
        //隐藏遮罩
        loaded: function (jq) {
            return jq.each(function () {
                var panel = $(this);
                panel.find("div.datagrid-mask-msg").remove();
                panel.find("div.datagrid-mask").remove();
            });
        }
    });
    $.extend($.fn.tabs.methods, {//tabs半透明遮罩
        //显示遮罩
        loading: function (jq, msg) {
        	alert(123);
            return jq.each(function () {
                var panel = $(this).tabs("getSelected");
                if (msg == undefined) {
                    msg = "正在加载数据，请稍候...";
                }
                $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: panel.width(), height: panel.height(),left:10,top:40 }).appendTo(panel);
                $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(panel).css({ display: "block", left: (panel.width() - $("div.datagrid-mask-msg", panel).outerWidth()) / 2, top: (panel.height() - $("div.datagrid-mask-msg", panel).outerHeight()) / 2 });
            });
        }
,
        //隐藏遮罩
        loaded: function (jq) {
            return jq.each(function () {
                var panel = $(this).tabs("getSelected");
                panel.find("div.datagrid-mask-msg").remove();
                panel.find("div.datagrid-mask").remove();
            });
        }
    });
    /**
     * 功能：增加Tree方法，可实现选中除本节点外第一层子节点
     * 1、cascadeCheck为false情况下有效；
     * 2、需在onLoadSuccess方法里调用；
     * 3、attributes中a3通常用于判断部门下是否存在人员
     */
    $.extend($.fn.tree.methods,{		
    	getCheckedExt: function(jq,param){						
    		var nodes=$(jq).find("span.tree-title").parent();						
			if(nodes.length>0){
				$.each(nodes, function(i, n){									
					if($(jq).tree('getChildren',n).length>0){
						$(n).append("<span class='checkall' style='display:none'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					};						  	
				});							
				$(jq).find(".checkall").parent().find(".tree-title").on("mouseenter", function(){								
					$(this).parent().find(".checkall").show();		
				});
				$(jq).find(".checkall").parent().on("mouseleave", function(){
					$(this).find(".checkall").hide();		
				});
				$(jq).find(".checkall").on("click", function(){								
					var select=$(jq).tree('getNode',$(this).parent());
					var childs=select.children;					
					var flag='uncheck';
					$.each(childs, function(i, node){
						if(node.attributes&&node.attributes.a3=='false'){//部门树下无人
							
						}else if(!node.checked){
							flag='check';
							return;
						}																																		
					});	
					$.each(childs, function(i, node){						
						if(flag=='check'){
							console.log(node);
							if(node.attributes&&node.attributes.a3=='false'){
								console.log(node.text);
							}else{
								$(jq).tree('check',node.target);
							}
							
						}else{
							$(jq).tree('uncheck',node.target);
						}																																						
					});								
				});				
			}						
		},
	    /**
	    *激活复选框  
	    *@param {Object} jq  
	    *@param {Object} target  
	    */  
	    enableCheck : function(jq, target) {    
	        return jq.each(function(){   
	            var realTarget;   
	            if(typeof target == "string" || typeof target == "number"){   
	                realTarget = $(this).tree("find",target).target;   
	            }else{   
	                realTarget = target;   
	            }   
	            var ckSpan = $(realTarget).find(">span.tree-checkbox");   
	            if(ckSpan.hasClass('tree-checkbox-disabled0')){   
	                ckSpan.removeClass('tree-checkbox-disabled0');   
	            }else if(ckSpan.hasClass('tree-checkbox-disabled1')){   
	                ckSpan.removeClass('tree-checkbox-disabled1');   
	            }else if(ckSpan.hasClass('tree-checkbox-disabled2')){   
	                ckSpan.removeClass('tree-checkbox-disabled2');   
	            }   
	        });   
	    },  
	    /** 
	     * 禁用复选框  
	     * @param {Object} jq  
	     * @param {Object} target  
	     */  
	    disableCheck : function(jq, target) {   
	        return jq.each(function() {   
	            var realTarget;   
	            var that = this;   
	            var state = $.data(this,'tree');   
	            var opts = state.options;   
	            if(typeof target == "string" || typeof target == "number"){   
	                realTarget = $(this).tree("find",target).target;   
	            }else{   
	                realTarget = target;   
	            }   
	            var ckSpan = $(realTarget).find(">span.tree-checkbox");   
	            ckSpan.removeClass("tree-checkbox-disabled0").removeClass("tree-checkbox-disabled1").removeClass("tree-checkbox-disabled2");   
	            if(ckSpan.hasClass('tree-checkbox0')){   
	                ckSpan.addClass('tree-checkbox-disabled0');   
	            }else if(ckSpan.hasClass('tree-checkbox1')){   
	                ckSpan.addClass('tree-checkbox-disabled1');   
	            }else{   
	                ckSpan.addClass('tree-checkbox-disabled2')   
	            }   
	            if(!state.resetClick){   
	                $(this).unbind('click').bind('click', function(e) {   
	                    var tt = $(e.target);   
	                    var node = tt.closest('div.tree-node');   
	                    if (!node.length){return;}   
	                    if (tt.hasClass('tree-hit')){   
	                        $(this).tree("toggle",node[0]);   
	                        return false;   
	                    } else if (tt.hasClass('tree-checkbox')){   
	                        if(tt.hasClass('tree-checkbox-disabled0') || tt.hasClass('tree-checkbox-disabled1') || tt.hasClass('tree-checkbox-disabled2')){   
	                            $(this).tree("select",node[0]);   
	                        }else{   
	                            if(tt.hasClass('tree-checkbox1')){   
	                                $(this).tree('uncheck',node[0]);   
	                            }else{   
	                                $(this).tree('check',node[0]);   
	                            }   
	                            return false;   
	                        }   
	                    } else {   
	                        $(this).tree("select",node[0]);   
	                        opts.onClick.call(this, $(this).tree("getNode",node[0]));   
	                    }   
	                    e.stopPropagation();   
	                });   
	            }   
	               
	        });   
	    },refreshCurrent : function(jq, url) {    
	        return jq.each(function(){   
	        	refreshTreeNode(this,url,false);
	        });   
	    },refreshParent : function(jq, url) {    
	        return jq.each(function(){   
	        	refreshTreeNode(this,url,true);
	        });   
	    }    
	});
    function refreshTreeNode(tree,url,isParent){    	
    	var json = $.ajax({
    		url: url,
    		async: false
    	}).responseText;
    	json=eval('('+json+')');
    	var node=$(tree).tree('getSelected');
    	if(isParent){
    		node=$(tree).tree('getParent',node.target);
    	}    	
    	var childs=json;
    	for(var i=0;i<childs.length;i++){
    		if($(tree).tree('find',childs[i].id)){//			    				
    			$(tree).tree('remove',$(tree).tree('find',childs[i].id).target);
    		}
    	}			    		
    	$(tree).tree('append',{
    		parent:node.target,
    		data:json
    	});
    }
    /**
     * 扩展DATAGRID中tooltip功能
     */
    function init(target){
        var opt = $.data(target, "tips").options;
        var tips = $(".easyui-tips-hover");
        if (tips.length == 0){
            tips = $("<div id='datagrid_tooltip'>").css({
                    "position" : "absolute",
                    "border-radius" : "5px",
                    "-webkit-border-radius" : "5px",
                    "-moz-border-radius" : "5px",
                    "padding" : "5px",
                    "background" : "#fff",
                    "display" : "none",
                    "border" : "1px solid gray"
            }).hide().addClass("easyui-tips-hover").addClass(opt.cls);
        }        
        opt.content = (opt.content || $(target).attr("tooltip"));
        tips.appendTo("body");        
        $(target).css("color", opt.wrapColor);        
        $(target).hover(function (){
            tips.html(opt.content);
            var offset = $(target).offset();
            //var outerWidth = tips.outerWidth();
            //            if (outerWidth > 200) {
            //                tips.width(200);
            //            }
            var scrollTop = $(document).scrollTop();
            var tipsHeight = tips.outerHeight();
            var outerWidth = tips.outerWidth();
            
            var targetHeight = $(target).outerHeight();
            var top = offset.top - tipsHeight;
            var left = offset.left;
            
            if ((offset.top - scrollTop) < top || top < 100){
                top = offset.top + targetHeight;
            }
            var bodyClienWidth = $("body")[0].clientWidth;
            if ((bodyClienWidth - left) < outerWidth){
                left = bodyClienWidth - outerWidth;
            }
            tips.css({
                top : top,
                left : left
            }).show();
        },function (e){
            tips.hide().width("auto");
        });
    }
    $.fn.tips = function (options, params){
        if (typeof options === 'string'){
            return $(this).tips.methods[options].call(this, params);
        }
        
        options = options || {};
        return this.each(function (){
            var opt = $.data(this, "tips");
            if (opt){
                $.extend(opt.options, options);
            }
            else{
	            $.data(this, "tips",{
	                options : $.extend({}, $.fn.tips.defaults, options)
	            });
	            init(this);
            }
        });
    };
    $.fn.tips.defaults ={
        cls : "",        
        content : null,
        wrapColor : "blue"
    };
    if ($.parser){
        $.parser.plugins.push('tips')
    }
})(jQuery);
//通用校验
$.extend($.fn.validatebox.defaults.rules, {    
    minLength: {    
        validator: function(value, param){    
            return value.length >= param[0];    
        },    
        message: '请至少填写 {0} 个字符！'   
    },
    maxLength: {    
        validator: function(value, param){    
            return value.length <= param[0];    
        },    
        message: '字数不能超过 {0} 个!'   
    },
    checkInt:{//验证整数
    	validator: function(value, param){ 
    		return /^([0-9]+)$/.test(value);
    	},
    	message:'请输入整数'
    },
    intOrFloat:{//验证整数或小数
    	validator: function(value, param){ 
    		return /^\d+(\.\d+)?$/i.test(value);
    	},
    	message:'请正确输入数字'
    },equalTo:{
    	validator: function(value, param){ 
    		return $(param[0]).val()==value;
    	},
    	message:'两次输入不一致'
    },vaildIP:{
    	validator: function(value, param){ 
    		var re =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/ 
    		return re.test(value);     		
    	},
    	message:'请输入合法IP地址'
    }
}); 
$.extend($.fn.tree.defaults, {    
    minLength: {    
        validator: function(value, param){    
            return value.length >= param[0];    
        },    
        message: 'Please enter at least {0} characters.'   
    },
    checkInt:{//验证整数
    	validator: function(value, param){ 
    		return /^([0-9]+)$/.test(value);
    	},
    	message:'请输入整数'
    },
    intOrFloat:{//验证整数或小数
    	validator: function(value, param){ 
    		return /^\d+(\.\d+)?$/i.test(value);
    	},
    	message:'请正确输入数字'
    }
});
$.fn.serializeObject=function(){
	var obj={};
	var array=this.serializeArray();
	$.each(array,function(){
		if(obj[this.name]){
			if(!obj[this.name].push){
				obj[this.name]=[obj[this.name]];
			}
			obj[this.name].push(this.value||'');
		}else{
			obj[this.name]=this.value||'';
		}
		
	});
	return obj
};
/**      
* 对Date的扩展，将 Date 转化为指定格式的String      
* 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符      
* 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)      
* eg:      
* (new Date()).formate("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423      
* (new Date()).formate("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
* (new Date()).formate("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
* (new Date()).formate("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
* (new Date()).formate("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
*/        
Date.prototype.formate=function(fmt) {         
	var o = {         
	"M+" : this.getMonth()+1, //月份         
	"d+" : this.getDate(), //日         
	"h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
	"H+" : this.getHours(), //小时         
	"m+" : this.getMinutes(), //分         
	"s+" : this.getSeconds(), //秒         
	"q+" : Math.floor((this.getMonth()+3)/3), //季度         
	"S" : this.getMilliseconds() //毫秒         
	};         
	var week = {         
	"0" : "\u65e5",         
	"1" : "\u4e00",         
	"2" : "\u4e8c",         
	"3" : "\u4e09",         
	"4" : "\u56db",         
	"5" : "\u4e94",         
	"6" : "\u516d"        
	};         
	if(/(y+)/.test(fmt)){         
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
	}         
	if(/(E+)/.test(fmt)){         
		fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);         
	}         
	for(var k in o){         
		if(new RegExp("("+ k +")").test(fmt)){         
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
		}         
	}         
	return fmt;         
}
if(!init){
	var init={};
}
init={//注意这里是全局属性
	network:null,	//taaver全局对象
	socketmap:{},//websocket
	pfIntervalMap:new Map(),//性能(包括server、storage)
	user:{},//用户对象
	disableForm:function(formId,isDisabled){
		var attr="disable";  
	    if(!isDisabled){  
	       attr="enable";  
	    }  
	    
	    $("#" + formId + " input[class*='textbox-f']").each(function () {      	
	        if (this.id) {          
	        	console.log("textbox#" + formId+" #" + this.id)   
	           $("#" + formId+" #" + this.id).textbox(attr);  
	        }  
	    }); 
	    $("#" + formId + " input[class*='combo-f']").each(function () {      	
	        if (this.id) {   
	        	console.log("combobox#" + formId+" #" + this.id)        
	            $("#" + formId+" #" + this.id).combobox(attr);  	                
	        }  
	    });  
	    $("#" + formId + " input[class*='easyui-datetimebox']").each(function () {  
	        if (this.id) { 
	        	console.log("datetimebox#" + formId+" #" + this.id) 	        
	            $("#" + formId+" #" + this.id).datebox(attr);  
	        }  
	    });
	},
	fullScreen:function(element) {//全屏  
	    if (!document.fullscreenElement && // alternative standard method  
	        !document.mozFullScreenElement && !document.webkitFullscreenElement) {// current working methods  
	        if (element.requestFullscreen) {  
	        	element.requestFullscreen();  
	        } else if (element.mozRequestFullScreen) {  
	        	element.mozRequestFullScreen();  
	        } else if (document.documentElement.webkitRequestFullscreen) {  
	        	element.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);  
	        }  
	    } else {  
	        if (document.cancelFullScreen) {  
	            document.cancelFullScreen();  
	        } else if (document.mozCancelFullScreen) {  
	            document.mozCancelFullScreen();  
	        } else if (document.webkitCancelFullScreen) {  
	            document.webkitCancelFullScreen();  
	        }  
	    }  
	},
	//延迟执行
	pause:function(time){
		var now=new Date();
		var exitTime=now.getTime()+time;
		while(true){
			now=new Date();
			if(now.getTime()>exitTime)return;
		}
	},messager:function(msg,height,timeout){
		height=height==null?100:height;
		timeout=timeout==null?1000:timeout;
		$.messager.show({			
			title:'消息提示',
			height:height,
			msg:msg,
			showType:'fade',
			timeout:800,
			border:'thin',
			style:{
				right:'',
				top:document.body.scrollTop+($(window).height()-100) * 0.5,
				bottom:'',				
			}
		});			
	},getFormData:function(formid){//获得from数据,返回json格式
		var frmdata = $("#"+formid).serializeArray(); 
		var obj = {};
		$.each(frmdata,function(i,v){
			obj[v.name] = v.value;
		});
		return obj;
	},
	/**
	 * 参数说明：ID、主题、宽、高、url、传参，关闭时回调函数、content方式加载内容
	 * 1、增加销毁方法，解决window跟tabs一起使用时关闭tabs无法删除对应窗体DOM(渲染后的)；
	 * 2、通过传html方式解决控件销毁后无法再次使用;
	 */
	winshow:function(id,title,w,h,url,queryParams,onClosecallback,html){
		function overstep(t,left, top){//防止超出屏幕
			var parentObj = $(t).panel('panel').parent();
            var width = $(t).panel('options').width;
            var height = $(t).panel('options').height;
            var parentWidth = $("body").width();
            var parentHeight = $("body").height();
            var scrollLeft = document.body.scrollLeft;
            var scrollTop = document.body.scrollTop;
            // 当弹出框尺寸大于浏览器大小时，弹出框自动缩小为浏览器当前尺寸
            if (width > parentWidth)
                $(t).window('resize', {
                    width: parentWidth - 1
                });
            if (height > parentHeight)
                $(t).window('resize', {
                    height: parentHeight - 1
                });
            // 当弹出框被拖动到浏览器外时，将弹出框定位至浏览器边缘
            if (left < scrollLeft) {
                $(t).window('move', {
                    left: scrollLeft
                });
            }
            if (top < scrollTop) {
                $(t).window('move', {
                    top: scrollTop
                });
            }
            if (left > scrollLeft && left > parentWidth - width + scrollLeft) {
                $(t).window('move', {
                    left: parentWidth - width + scrollLeft
                });
            }
            if (top > scrollTop && top > parentHeight - height + scrollTop) {
                $(t).window('move', {
                    top: parentHeight - height + scrollTop
                });
            }
		}
		if(url!=null&&''!=url){			
			$('<div></div>').window({ 
				id:id,
				href:url+'?r='+Math.random(), 
				width:w,    
			    height:h,
			    top:($(window).height()*0.2),
			    title:title,
			    queryParams:queryParams,
			    modal:true,
			    shadow:true,
			    closed:true,
			    cache:false,
			    border:'thin',
			    minimizable:false,
			    maximizable:false,
			    collapsible:false,
			    onClose:function(){	
			    	if(typeof onClosecallback ==='function'){//回调
			    		onClosecallback();
			    	}
			    	$(this).window('destroy');
			    },onMove: function (left, top) { // popwindow拖动时触发，限制弹出框拖动范围
			    	overstep(this,left, top);	                
	            },
			});
		}else{
			$('<div></div>').window({ 
				id:id,
				width:w,    
			    height:h,
			    content:html,
			    top:($(window).height()*0.2),
			    title:title,
			    queryParams:queryParams,
			    modal:true,
			    closed:true,
			    cache:false,
			    border:'thin',
			    minimizable:false,
			    maximizable:false,
			    collapsible:false,
			    onClose:function(){		    	
			    	$(this).window('destroy');
			    	if(typeof onClosecallback ==='function'){
			    		onClosecallback();
			    	}
			    },onMove: function (left, top) { // popwindow拖动时触发，限制弹出框拖动范围
			    	overstep(this,left, top);                
	            },
			});
		}
		$('#'+id).window('open');	
		$('#'+id).window('center');
	},addCenter:function(url,type){//添加center
		if('0'==type){
			var content = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			$('#main').layout('add',{
		    	region:'center',	
		    	id:'center',
		    	border:false,
		    	content:content
			});	
		}else{
			$('#main').layout('add',{
		    	region:'center',	
		    	id:'center',
		    	href:url
			});	
		}
			 
	},//添加center、tabs
	addCenterTabs:function(url){		
		$('#main').layout('add',{
	    	region:'center',
	    	/*cls:'other-index-center',*/
	    	id:'center',
	    	
		});
		$('#center').html('<div id="tabs" style="height:32px"></div>'); 
		$('#tabs').tabs({   
		     border:false, 
		     fit:true,	
		     plain:true,
		     tabHeight:38,
		     /*headerCls:'other-index-center-tabs',*/
		     onSelect:function(title){   
		    	 
		     },onBeforeClose:function(title,index){
		    	var t = $('#tabs').tabs('getSelected'); 
	        	var url=t.panel('options').href;
	        	
		 		var wype=init.getUrlParam(url,'websocket');
		        var websocket=init.socketmap.get(wype);	
	        	if(websocket){
	        		websocket.close();//关闭websocket
		    		websocket=null;
		    		init.socketmap.removeByKey(wype);//集合中删除websocket
	        	}

		    	//清除interval
		 		var mype=init.getUrlParam(url,'mtype');
		 		if(mype){		 			
		 			var intervals=init.pfIntervalMap.values();
		 			$.each(intervals, function(i,n){
		 				clearInterval(n);
		 			});
		 			init.pfIntervalMap.clear();		 			
		 		}		 		
		     }
		});
	},//删除center
	delCenter:function(){
		if($("#tabs").length>0){
			$.each($("#tabs").tabs('tabs'),function(idx,tab){
				$("#tabs").tabs('close',idx);
			});
		}
		$('#main').layout('remove','center');
	},//添加选项卡
	addTab:function(title, url,object){
		$('#tabs').tabs('close', title);	
		$('#tabs').tabs('add',{
			title:title,
			href:url,
			border:false,
			closable:true,
			queryParams:object,	
			tools:[{    
		        iconCls:'icon-mini-refresh',    
		        handler:function(){ 
		        	if(init.network!=null){
		        		init.network.getDataBox().clear();		 
		        		init.network.dispose();
		        		init.network = null;
		        	}
		        	var ctitle=$(this).parent().prev().find(".tabs-title").text();			        	
		        	$('#tabs').tabs('select',ctitle);
		        	var t = $('#tabs').tabs('getSelected'); 
		        	var url=t.panel('options').href;
			 		var wype=init.getUrlParam(url,'websocket');
			        var websocket=init.socketmap.get(wype);	
		        	if(websocket){
		        		websocket.close();//关闭websocket
			    		websocket=null;
			    		init.socketmap.removeByKey(wype);//集合中删除websocket
		        	}
		        	//如果为性能监控则清除interval
		        	var mype=init.getUrlParam(url,'mtype');
		        	if(mype){		 			
			 			var intervals=init.pfIntervalMap.values();
			 			$.each(intervals, function(i, n){
			 				clearInterval(n);
			 			});
			 			init.pfIntervalMap.clear();		 			
			 		}
		        	t.panel('refresh');
		        }    
		    }]
		});
		
	},//添加左部区域
	addWest:function(title,privid){
		var west={region:'west'};
		west.width=210;
		west.split=false;
		west.cls='other-index-west',
		west.headerCls='other-index-header',
		west.title=title;
		west.iconCls='other-icon-paneltitle',
		west.id='west';
		$('#main').layout('add',west);
		$('#west').html('<ul id="menu"></ul>');
		init.addMenuTree (privid);

	},//删除左部区域
	delWest:function(){		
		$('#main').layout('remove','west');
	},//添加菜单树
	addMenuTree:function(pid){
		$('#menu').accordion({    
		    animate:true,
		}); 
		$.ajax({
			type:"get",
			async:false,
		
			url:"prvmenuTree",
			data:"pid="+pid+"&r="+Math.random()*10000,
			success:function(data){
				data=eval('('+data+')');
				console.log(data)
				for(var i=0;i<data.length;i++){				
					console.log(data[i]);
					
					$('#menu').accordion('add', {
						title: data[i].text,
						content: '<ul id="menutree'+i+'"></ul> ',
						selected: false
					});
					
					$('#menutree'+i).tree({   
							url:"prvmenuTree?pid="+data[i].id,
							lines:false,
							animate:true,
							onClick:function(node){
							  if(node.url){
							 	 init.addTab(node.text,node.url);
							  }		
							  if(node.state=='closed'){				
								  $(this).tree('expand',node.target);
							  }else{
								  $(this).tree('collapse',node.target);
							  }
							},
							onBeforeExpand:function(node,param){								
								$(this).tree('options').url="prvmenuTree?pid="+node.id
							},onDblClick:function(node){
												
							}
					}); 
				}
				
			}
		});
	},
	//获得URL参数
	getUrlParam:function(url,name){
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	    url=url.substr(url.indexOf('?')+1);    
	    var r = url.match(reg);
	    if (r != null) {
	    	return unescape(r[2]); 
	    }
	    return null;
	},//初始化一级菜单导航
	getNavigation:function(){
		var nav_html="";
		 $.ajax({
			type:"get",
			async:false,
			url:"prvmenuTree",
			data:"pid=-1&r="+Math.random()*10000,
			success:function(msg){	
				try{
					var data=eval(msg);	 
					$.each(data,function(idx,item){		
						nav_html=nav_html+"<li><a href='#' url='"+item.url+"&privid="+item.id+"' class='menu_level' onfocus=this.blur()><span>"+item.text+"</span></a></li>";										
					});
					if(data&&data.length>0){					
						init.mainshow(data[0].url+"&privid="+data[0].id,data[0].text); 
					}
				}catch(err){
					console.log('prvmenuTree未授权，或返回数据出错!');
				}				
			}
		 });
		 $('#nav').html(nav_html);
		
	},mainshow:function(url,title){
		var menu_level=	init.getUrlParam(url,"l");	
		var privid=init.getUrlParam(url,"privid");
		
		$(this).parent().parent().find("a").removeClass('current');		
		$(this).addClass('current');					
		if(menu_level==0){//首页展示
			init.delWest();
			init.delCenter();
			init.addCenter(url,'0');
		}else if(menu_level==1){
			init.delWest();
			init.delCenter();
			init.addCenterTabs(url);
			init.addWest(title,privid);
		}
	},getFileName:function(path){
		var p1=path.lastIndexOf("/");
		var p2=path.lastIndexOf("\\");
		var p=Math.max(p1,p2);
		if(p<0){
			return path;
		}else{
			return path.substring(p+1);
		}
	}
}
$(document).ready(function(){
	init.socketmap=new Map();//初始化websocket集合	
	//监控所有ajax发送/form提交无效
	$(document).ajaxStart(function(evt, request, settings){

		//检测当前cookie是否被覆盖
		if(init.user.cookieid!=undefined&&init.user.cookieid!=$.cookie('k')){
			
		}
	});
	//监控所有ajax返回
	$(document).ajaxComplete(function(evt, request, settings){		
		if(request.responseText.indexOf('<input id="sbmtbtn" type="button" class="loginbtn" value="登录" />')>-1){			
		  location.href='login.html';
		}else if('{"status":"-1"}'==request.responseText){
			$.messager.alert('提示',settings.url+',权限不足!'); 
		}
	});
	init.getNavigation();
	//一级菜单点击
	$(".menu_level").click(function(){
		var menulevel=$('.menu_level');
		menulevel.removeClass('selected');
		var url=$(this).attr("url");
		$(this).addClass('selected');
		init.mainshow(url,$(this).find('span').html());
	});
	//加载登陆信息
	$.ajax({
		  url: "usergetCookie",
		  data:"r="+Math.random()*10000,
		  success: function(data){		  
			  init.user =eval("("+data+")");			  
			  $("#login_userid").html("<a  href='#'style='color:#fff;'>"+init.user.username+"</a>");
			  $("#login_users").html("<div id='onlineuser'><a href='#' style='color:#fff;'>当前在线"+init.user.usercount+"人</a></div>");		
			  $("#onlineuser").click( function () { 
				  	init.winshow('onlineuserwin','在线用户',600,400,'onlineuser.html');					
			  });
		  }
	});
	//注销
	$('#logout').bind('click', function(){    
		$.ajax({
		url: "userlogout",
			async: false,
			data:"r="+Math.random()*10000,
			success: function(data){		  
				var d = eval("("+data+")");	    	
				if(d.status=="1"){
					window.location.href="login.html"
					return;
				}	    	
			}
		});
		window.location.href="login.html";
    }); 
	$("#login_userid").bind('click', function(){//修改个人信息    
		init.winshow('usereditwin','修改个人信息',400,250,'sysmanage/useredit.html');
	});
	
	var totalWidth = 0, current = 1;
	
	$.each($('.nav_content').find('li'), function(){
		totalWidth += $(this).outerWidth();
	});
	
	$('.nav_content').width(totalWidth);
	
	function currentLeft(){
		return -(current - 1) * 93;	
	}
	$('.nav-btn a').click(function(e) {
		var tempWidth = totalWidth - ( Math.abs($('.nav_content').css('left').split('p')[0]) + $('.menu_nav').width() );
        if($(this).hasClass('nav-prev-btn')){
			if( parseInt($('.nav_content').css('left').split('p')[0])  < 0){
				current--;
				Math.abs($('.nav_content').css('left').split('p')[0]) > 93 ? $('.nav_content').animate({'left': currentLeft()}, 200) : $('.nav_content').animate({'left': 0}, 200);
			}
		}else{

			if(tempWidth  > 0)	{
				
			   	current++;
				tempWidth > 93 ? $('.nav_content').animate({'left': currentLeft()}, 200) : $('.nav_content').animate({'left': $('.nav_content').css('left').split('p')[0]-tempWidth}, 200);
			}
		}
    });
	
	
	//解析完成，执行的内容 例如：添加CSS
	$.parser.onComplete=function(){  
		  $(".loading").hide();
	}
	
});





