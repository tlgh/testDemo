package com.jpz.dcim.modeling.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;

import pers.ksy.common.model.Result;

public class BaseController {

	@ExceptionHandler
	public Result<String> exp(HttpServletRequest request, Exception ex) {
		// 记录日志
		// logger.error(ex.getMessage(), ex);
		// 根据不同错误转向不同页面
		ex.printStackTrace();
		//System.out.println(ex.getMessage());
		return Result.errorResult(ex.getMessage(), "服务器请求异常");
	}

}
