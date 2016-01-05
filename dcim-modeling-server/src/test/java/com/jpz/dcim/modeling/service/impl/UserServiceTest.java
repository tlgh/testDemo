package com.jpz.dcim.modeling.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.service.OrganizationService;
import com.jpz.dcim.modeling.service.UserService;

public class UserServiceTest extends BaseTestCase{
	
	@Autowired
	UserService service;
	@Autowired
	OrganizationService orgService;
	
	public void setup(){
		
	}
	
	@Test
	public void testGetDao() {
		BaseDao dao = service.getDao();
		assertNotNull(dao);
		assertTrue(dao instanceof UserDao);
	}

	@Test
	public void testAddUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testMoveUserBefore() {
		fail("Not yet implemented");
	}

	@Test
	public void testMoveUserTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersInOrg() {
		fail("Not yet implemented");
	}

}
