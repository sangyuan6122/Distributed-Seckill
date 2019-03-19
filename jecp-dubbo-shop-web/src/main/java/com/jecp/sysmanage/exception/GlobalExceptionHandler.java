package com.jecp.sysmanage.exception;

import java.util.List;

import javax.validation.ValidationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcException;
import com.jecp.base.result.BaseCodeMsgEnum;
import com.jecp.base.result.BaseResult;
import com.jecp.base.result.Result;
import com.jecp.exceptions.BizException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@ExceptionHandler(BindException.class)
	public Result bindExceptionHandler(BindException ex) {
		log.error("数据绑定异常",ex);
		List<ObjectError> errors = ex.getAllErrors();
		ObjectError error = errors.get(0);
		String msg = error.getDefaultMessage();
		return Result.of(BaseCodeMsgEnum.INVAILD_DATA, msg);
	}
	@ExceptionHandler(ValidationException.class)
	public BaseResult validationExceptionHandler(ValidationException ex) {
		log.error("数据校验异常",ex);
		return BaseResult.of(BaseCodeMsgEnum.INVAILD_DATA);
	}
	@ExceptionHandler(BizException.class)
	public Result bizException(BizException ex) {
		log.error("业务异常",ex);
		String msg = ex.getErrorMsg();
		return Result.of(BaseCodeMsgEnum.BIZ_EXCEPTION, msg);
	}
	@ExceptionHandler(RpcException.class)
	public BaseResult rpcException(RpcException ex) {
		log.error("RPC异常",ex);		
		return Result.of(BaseCodeMsgEnum.RPC_EXCEPTION);
	}
	
	@ExceptionHandler(Exception.class)
	public BaseResult exception(Exception ex) {
		log.error("服务运行异常",ex);
		return BaseResult.of(BaseCodeMsgEnum.FAILED);
	}
}
