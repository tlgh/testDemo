package com.jpz.dcim.modeling.aop;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.UndeclaredThrowableException;

import javax.annotation.Resource;

import org.junit.Test;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.exception.DAOException;
import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.service.PartyService;

public class ExceptionAdviceTest  extends BaseTestCase{
	@Resource 
	private UserDao dao = null;
	@Resource 
	private PartyService service = null;
	


	
	@Test
	public void testDaoCutAndThrow(){
		try{
			dao.method4Test();
		}catch(Throwable e){
			if(e instanceof UndeclaredThrowableException){
				e = e.getCause();
			}
			assertTrue(e instanceof DAOException);
			return;
		}
		fail("can not catch dao exception");
	}
	
	@Test
	public void testServiceCutAndThrow(){
		try{
			service.method4Test();
		}catch(Throwable e){
			if(e instanceof UndeclaredThrowableException){
				e = e.getCause();
			}
			assertTrue(e instanceof ServiceException);
			return;
		}
		fail("can not catch service exception");
	}
	
}
