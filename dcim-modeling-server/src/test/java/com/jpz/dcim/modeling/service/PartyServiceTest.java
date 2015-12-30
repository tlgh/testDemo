package com.jpz.dcim.modeling.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.model.entity.Organization;


public class PartyServiceTest extends BaseTestCase {
	
	@Resource 
	PartyService service;
	
	private Organization root = null;
	@Before
	public void beforeMethod(){
		
		
		//初始化一个根部门,两个字部门
		root = new Organization();
		root.setCreateTime(new Date());
		root.setLastModifyTime(new Date());
		root.setDescription("测试用组织根节点");
		root.setName("根部门");
		root.setParent(null);
		root.setPrincipal(null);
		
		service.addOrganization(root);

		Organization child1 = new Organization();
		child1.setName("子部门1");
		child1.setParent(root);
		child1.setDescription("测试用组织子节点");
		child1.setCreateTime(new Date());
		child1.setLastModifyTime(new Date());
		child1.setPrincipal(null);
		service.addOrganization(child1);
		
		
		Organization child2 = new Organization();
		child2.setName("子部门2");
		child2.setParent(root);
		child2.setDescription("测试用组织子节点");
		child2.setCreateTime(new Date());
		child2.setLastModifyTime(new Date());
		child2.setPrincipal(null);
		service.addOrganization(child2);
		
	}
	
	@Test
	public void testOrganizationList() {
		List<Organization> orgs = service.organizationList();
		assertEquals(3,orgs.size());
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
