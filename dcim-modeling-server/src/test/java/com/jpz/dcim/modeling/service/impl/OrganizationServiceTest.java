package com.jpz.dcim.modeling.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.model.dao.OrganizationDao;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.service.OrganizationService;

public class OrganizationServiceTest extends BaseTestCase{
	@Autowired
	OrganizationService service;
	
	private Organization root = null;
	private Organization child1 = null;
	
	@Before
	public void setup(){
		service.deleteAll();
		//初始化一个根部门,两个子部门
		root = new Organization();
		root.setCreateTime(new Date());
		root.setLastModifyTime(new Date());
		root.setDescription("测试用组织根节点");
		root.setName("根部门");
		root.setPrincipal(null);		
		service.addOrg(root,null);				

		child1 = new Organization();
		child1.setName("子部门1");
		child1.setDescription("测试用组织子节点");
		child1.setCreateTime(new Date());
		child1.setLastModifyTime(new Date());
		child1.setPrincipal(null);
		service.addOrg(child1, root.getId());
		
		
		
		Organization child2 = new Organization();
		child2.setName("子部门2");
		child2.setDescription("测试用组织子节点");
		child2.setCreateTime(new Date());
		child2.setLastModifyTime(new Date());
		child2.setPrincipal(null);
		service.addOrg(child2, root.getId());
	}
	
	@Test
	public void testGetDao() {
		BaseDao dao = service.getDao();
		assertNotNull(dao);
		assertTrue(dao instanceof OrganizationDao);
	}

	@Test
	public void testAddOrg() {
		Organization org = new Organization();
		org.setName("子部门3");
		org.setDescription("测试用组织子节点");
		org.setCreateTime(new Date());
		org.setLastModifyTime(new Date());
		org.setPrincipal(null);
		service.addOrg(org, root.getId());
		
		Organization loaded = service.get(org.getId());
		assertNotNull(loaded);
		assertEquals("子部门3",loaded.getName());
		assertEquals(root,loaded.getParent());
	}

	@Test
	public void testMoveOrgBefore() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoot() {
		Organization loadedRoot = service.getRoot();
		assertNotNull(loadedRoot);
		assertEquals(root,loadedRoot);
		List<Organization> children = loadedRoot.getChildren();
		assertNotNull(children);
		assertEquals(2,children.size());
	}

}
