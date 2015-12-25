package com.jpz.dcim.modeling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

public class ContextHolder extends ContextLoaderListener {
	public static ApplicationContext context = null;	
	
	public static ApplicationContext getContext() {
		if (ContextHolder.context == null) {
			ContextHolder.context = new ClassPathXmlApplicationContext(new String[] { getContextPath()});
		}
		return ContextHolder.context;
	}
	
	public static String getContextPath() {
		return "/META-INF/appcontext.xml";
	}
	
	
}