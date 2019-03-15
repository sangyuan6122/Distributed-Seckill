package com.jecp.base.result;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jecp.sysmanage.model.TawSystemUser;

/**
 * @Title 返回详细结果
 * @author WWT
 * @date 2018年5月1日
 */
public class Result<T> extends BaseResult {
	private final T data;

	private Result(CodeMsg codeMsg, T data) {
		super(codeMsg);
		this.data = data;
	}
	public static <T> Result<T> of(CodeMsg codeMsg, T data) {
		return new Result<T>(codeMsg,data);
	}
	public T getData() {
		return data;
	}
	public static void main(String[] args) {
		List<TawSystemUser> list=new ArrayList<>();
		TawSystemUser u1=new TawSystemUser();
		TawSystemUser u2=new TawSystemUser();
		u1.setDeptname("AAA");
		u2.setDeptname("BBB");
		list.add(u1);
		list.add(u2);
		System.out.println(JSON.toJSONString(Result.of(list)) );
		System.out.println(JSON.toJSONString(Result.of(BaseCodeMsgEnum.NO_LOGIN,list)) );
		System.out.println(JSON.toJSONString(Result.of(BaseCodeMsgEnum.NO_LOGIN)) );
		System.out.println(JSON.toJSONString(BaseResult.of(BaseCodeMsgEnum.SUCCESS)));
	}
}
