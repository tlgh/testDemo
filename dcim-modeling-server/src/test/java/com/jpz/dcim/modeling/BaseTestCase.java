package com.jpz.dcim.modeling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-config/application-context.xml"})
@Transactional
public class BaseTestCase {
	
	@Test
	public void empyRunnable(){
		//do nothing
	}
}
