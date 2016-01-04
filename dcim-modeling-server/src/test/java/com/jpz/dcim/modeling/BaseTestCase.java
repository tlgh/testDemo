package com.jpz.dcim.modeling;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-config/application-context.xml"})
public class BaseTestCase {


}
