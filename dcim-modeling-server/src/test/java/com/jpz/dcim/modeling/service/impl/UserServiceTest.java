package com.jpz.dcim.modeling.service.impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.OrganizationService;
import com.jpz.dcim.modeling.service.UserService;

public class UserServiceTest extends BaseTestCase{	
	@Autowired
	UserService service;
	@Autowired
	OrganizationService orgService;
	
	Organization root;
	
	
	@Before
	public void setup(){
		service.deleteAll();
		orgService.deleteAll();
		//增加一个机构，并增加3个用户
		root = new Organization();
		root = new Organization();
		root.setCreateTime(new Date());
		root.setLastModifyTime(new Date());
		root.setDescription("测试用组织根节点");
		root.setName("根部门");
		root.setPrincipal(null);		
		orgService.addOrg(root,null);			
		
		create3Users();
	}
	
	private void create3Users(){
		for(int i=0;i<3;i++){
			User user = new User();
			user.setUsername("user_"+i);
			user.setName("用户_"+i);
			user.setPassword("123456");
			user.setSex((short)1);
			user.setCreateTime(new Date());
			user.setLastModifyTime(new Date());
			service.addUser(user, root.getId());
		}
	}
	
	@Test
	public void testGetDao() {
		BaseDao dao = service.getDao();
		assertNotNull(dao);
		assertTrue(dao instanceof UserDao);
	}

	@Test
	public void testAddUser() {
		User user = new User();
		user.setName("name");
		user.setUsername("username");
		user.setSex((short)1);
		user.setCreateTime(new Date());
		user.setLastModifyTime(new Date());
		user.setPassword("123456"); 
		service.addUser(user, root.getId());
		
		User loaded = service.get(user.getId());
		assertNotNull(loaded);
		assertEquals("name",loaded.getName());
		assertEquals(3,loaded.getPosition());
	}
	
	@Test
	public void testGetUsersOfOrg(){
		List<User> members = service.getUsersInOrg(root.getId());
		assertNotNull(members);
		assertEquals(3,members.size());
	}
	
	@Test
	public void testGetUserByUsername(){
		User user = service.getUserByUsername("user_1");
		assertNotNull(user);
		assertEquals(1,user.getPosition());
		assertEquals("用户_1",user.getName());
	}

	@Test
	@Transactional
	public void testMoveUserBefore() {
		User user1 = service.getUserByUsername("user_1");
		assertEquals(1,user1.getPosition());
		User user0= service.getUserByUsername("user_0");
		service.moveUserBefore(user1, user0.getId());
		assertEquals(0,user1.getPosition());
		
		User user2 = service.getUserByUsername("user_2");
		assertEquals(2,user2.getPosition());
		service.moveUserBefore(user1, user2.getId());
		assertEquals(1,user1.getPosition());
	}
	
	@Test
	public void testMoveUserLast(){
		User user1 = service.getUserByUsername("user_1");
		assertEquals(1,user1.getPosition());
		service.moveUserLast(user1);
		
		assertEquals(2,user1.getPosition());
	}

	@Test
	public void testMoveUserTo() {
		fail("Not yet implemented");
	}

	
	public void testUpdateUser(){
		User user1 = service.getUserByUsername("user_0");
		user1.setName("用户100");
		service.update(user1);
		user1 = service.get(user1.getId());
		assertEquals("用户100",user1.getName());
	}
	
	public void testDeleteUser(){
		User user1 = service.getUserByUsername("user_1");
		service.delete(user1);
		service.get(user1.getId());
	}

}
