package com.jpz.dcim.modeling.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;


public class PartyServiceTest extends BaseTestCase {
	
	@Resource 
	PartyService service;
	
	private Organization root = null;
	private Organization child1 = null;
	
	@Before
	public void setup(){
		service.deleteAllUsers();
		service.deleteAllOrganizations();
		
		//初始化一个根部门,两个子部门
		root = new Organization();
		root.setCreateTime(new Date());
		root.setLastModifyTime(new Date());
		root.setDescription("测试用组织根节点");
		root.setName("根部门");
		root.setPrincipal(null);		
		service.addOrganization(root,null);
		
		User user = new User();
		user.setName("张三");
		user.setUsername("zss");
		user.setPassword("123456");
		user.setSex((short) 1);		
		service.addUser(user,root.getId());
		
		

		child1 = new Organization();
		child1.setName("子部门1");
		child1.setDescription("测试用组织子节点");
		child1.setCreateTime(new Date());
		child1.setLastModifyTime(new Date());
		child1.setPrincipal(null);
		service.addOrganization(child1, root.getId());
		
		
		
		Organization child2 = new Organization();
		child2.setName("子部门2");
		child2.setDescription("测试用组织子节点");
		child2.setCreateTime(new Date());
		child2.setLastModifyTime(new Date());
		child2.setPrincipal(null);
		service.addOrganization(child2, root.getId());
		
	}
	
	@Test
	public void testOrganizationList() {
		List<Organization> orgs = service.organizationList();
		assertEquals(3,orgs.size());
	}
	
	@Test
	public void testRemoveOrganization(){
		service.deleteOrganization(child1.getId());
		try {
			Organization org = service.getOrganization(child1.getId());
		} catch (ServiceException e) {
			assertTrue(true);
		}
	}
	/*
	@Test
	public void testFindUserByOrg() {
		fail("Not yet implemented");
	}
	@Test
	public void testAddUser() {
		fail("Not yet implemented");
	}
	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}
	@Test
	public void testGetUser() {
		fail("Not yet implemented");
	}
	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}
	@Test
	public void testAddOrganization() {
		fail("Not yet implemented");
	}
	@Test
	public void testUpdateOrganization() {
		fail("Not yet implemented");
	}
	@Test
	public void testGetOrganization() {
		fail("Not yet implemented");
	}
	@Test
	public void testDeleteOrganization() {
		fail("Not yet implemented");
	}
	@Test
	public void testFindPage() {
		fail("Not yet implemented");
	}
	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}
*/
}
