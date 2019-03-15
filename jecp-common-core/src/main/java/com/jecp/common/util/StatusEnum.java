package com.jecp.common.util;

import com.alibaba.fastjson.JSONObject;

public enum StatusEnum {
	STATUS_UNAUTHORIZED("无权限",-1),
	STATUS_FAIL("执行失败",0),
	STATUS_SUCCESS("成功",1),
	STATUS_REPEAT("数据重复",2),
	STATUS_PARTIALSUCCESS ("部分成功",3),
	STATUS_NOLOGIN("未登录",4),
	STATUS_DATAERR("数据错误",5);
	// 成员变量  
    private String name;  
    private int index;  
    private String msg;
    // 构造方法  
    private StatusEnum(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    public void setMsg(String msg) {
    	this.msg=msg;
    }
    //覆盖方法  
    @Override  
    public String toString() {
    	JSONObject jSONObject=new JSONObject(true);
    	jSONObject.put("status", this.index);
    	if(this.index==0) {
    		if(msg==null||"".equals(msg)) {
    			jSONObject.put("msg",this.name);
    		}else {
    			jSONObject.put("msg", this.msg);
    		}    		
    	}
        return jSONObject.toJSONString();  
    }
	public String getName() {
		return name;
	}
	public int getIndex() {
		return index;
	}
	public String getMsg() {
		return msg;
	} 
    
}
