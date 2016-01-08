package com.jpz.dcim.modeling.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import pers.ksy.common.orm.MatchMode;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.QueryConditionImpl;

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
			user.setSex((short) (i%2));
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
		User user1 = service.getUserByUsername("user_1");
		Organization child = new Organization();
		child.setCreateTime(new Date());
		child.setLastModifyTime(new Date());
		child.setDescription("测试用子部门");
		child.setName("子部门");
		child.setPrincipal(null);		
		orgService.addOrg(child,root.getId());	
		
		service.moveUserTo(user1, child.getId());

		root = orgService.getRoot();
		child = orgService.get(child.getId());
		
		assertEquals(1,child.getMembers().size());
		assertEquals(2,root.getMembers().size());
		
		assertEquals(child.getMembers().get(0).getUsername(),"user_1");
	}

	@Test
	public void testUpdateUser(){
		User user1 = service.getUserByUsername("user_0");
		user1.setName("用户100");
		service.update(user1);
		user1 = service.get(user1.getId());
		assertEquals("用户100",user1.getName());
	}
	@Test
	public void testDeleteUser(){
		User user1 = service.getUserByUsername("user_1");
		service.delete(user1);
		service.get(user1.getId());
	}
	@Test
	public void testQueryByCondition(){
		QueryCondition condition = new QueryConditionImpl(User.class,null);
		condition.like("name", "用户", MatchMode.START);
		List<User> list = service.findList(condition);
		assertEquals(3,list.size());
		
		condition = new QueryConditionImpl(User.class,null);
		condition.like("name", "用户", MatchMode.START)
			.eq("sex", (short)0);
		list = service.findList(condition);
		assertEquals(2,list.size());
		
		condition = new QueryConditionImpl(User.class,null);
		condition.like("name", "用户", MatchMode.START)
			.eq("sex", (short)0)
			.eq("organization.id", root.getId());
		list = service.findList(condition);
		assertEquals(2,list.size());
	}
	
	

}
