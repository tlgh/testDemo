package pers.ksy.common.spring4;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 
 * Spring Controller 序列化拦截器
 *
 * @author 孔思宇 2015年4月3日
 * @see
 * @since 1.1
 */
public class ControllerSerializeAdviceFilter extends ControllerSerializeSupport {
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object obj = pjp.proceed();
		MethodSignature msig = (MethodSignature) pjp.getSignature();
		return doAround(obj, msig.getMethod());
	}
    

}
