package com.jpz.dcim.modeling.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jpz.dcim.modeling.exception.ServiceException;

import pers.ksy.common.model.Result;

public class BaseController {
	Log log = LogFactory.getLog(BaseController.class);

	@ExceptionHandler
	public Result<String> exp(HttpServletRequest request, Exception ex) {
		log.error(ex.getMessage(), ex);
		if (ex instanceof ServiceException) {
			return Result.errorResult(ex.getMessage());
		}
		return Result.errorResult(ex.getMessage(), "服务器请求异常!");
	}
}
