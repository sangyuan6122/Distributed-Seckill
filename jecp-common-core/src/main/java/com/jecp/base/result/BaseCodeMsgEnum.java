package com.jecp.base.result;

public enum BaseCodeMsgEnum implements CodeMsg {
	UNAUTHORIZED(-1, "无权限"), SUCCESS(1, "成功"), FAILED(0, "失败"), DATA_REPEAT(2, "数据重复"), 
		PARTIAL_SUCCESS(3,"部分成功"), NO_LOGIN(4, "未登录"), DATA_ERROR(5, "数据错误"),INVAILD_DATA(6,"非法数据"),BIZ_EXCEPTION(7,"业务异常"),RPC_EXCEPTION(8,"远端服务异常");
	private int code;
	private String message;

	private BaseCodeMsgEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public static void main(String[] args) {
		
	}
}
