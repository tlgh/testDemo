package com.jpz.dcim.modeling.aop;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemPointcuts {
	
	@Pointcut("execution(* com.jpz.dcim.*.service.impl.*.*(..))")
	public void cutinService() {}
	
	@Pointcut("within(com.jpz.dcim.modeling.model.dao..*)")
	public void cutinDao() {}
	

}
