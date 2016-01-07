package com.jpz.dcim.modeling.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;

import pers.ksy.common.MD5Util;
import pers.ksy.common.model.Page;
import pers.ksy.common.orm.Order;
import pers.ksy.common.orm.QueryCondition;

/**
 * 因为BaseDaoImpl是一个abstract类，所以，我们在userDao中测试baseDao方法
 * 
 * @author Administrator
 *
 */

public class BaseDaoTest extends BaseTestCase {
	@Resource
	UserDao dao;
	@Resource
	OrganizationDao orgDao;

	User user = null;

	@Before
	@Transactional
	public void setup() throws Exception {

		dao.deleteAll();
		orgDao.deleteAll();

		Organization org = new Organization();
		org.setName("xxx机构");
		org.setCreateTime(new Date());
		dao.saveAny(org);

		user = new User();
		user.setUsername("userName");
		user.setName("测试用户_0");
		user.setCreateTime(new Date());
		user.setLastModifyTime(new Date());
		user.setSex((short) 0);
		user.setPassword("123456");
		user.setOrganization(org);
		dao.save(user);
		create9Users();
		System.out.println("--------------------------------------------------" + dao.findAll().size());
	}

	private void create9Users() {
		for (int i = 1; i < 10; i++) {
			User u = new User();
			u.setUsername("user_" + i);
			u.setName("测试用户_" + i);
			u.setCreateTime(new Date());
			u.setLastModifyTime(new Date());
			u.setSex((short) 1);
			u.setPassword("123456");
			dao.save(u);
		}
	}

	@Test
	public void testSave() {
		User user1 = new User();
		user1.setUsername("zss");
		user1.setName("张三");
		user1.setPassword("12345");
		user1.setSex((short) 0);
		user1.setCreateTime(new Date());
		user1.setLastModifyTime(new Date());
		dao.save(user1);
		assertNotNull(user1.getId());

		User user2 = null;
		user2 = dao.get(user1.getId());
		assertNotNull(user2);
	}

	@Test
	public void testSaveAny() {
		Organization org = new Organization();
		org.setName("xx机构");
		org.setCreateTime(new Date());
		dao.saveAny(org);

		assertNotNull(org.getId());
		Organization org2 = orgDao.get(org.getId());
		assertNotNull(org2);
	}

	@Test
	public void testUpdate() {
		user.setName("updated");
		dao.update(user);
		user = dao.get(user.getId());
		assertEquals("updated", user.getName());
		assertEquals("userName", user.getUsername());
	}

	@Test
	public void testSaveOrUpdate() {
		User one = new User();
		one.setName("one");
		one.setUsername("one4Login");
		one.setPassword(MD5Util.MD5("123456"));
		one.setCreateTime(new Date());
		dao.saveOrUpdate(one);
		one = dao.get(one.getId());
		assertNotNull(one);
		assertNotNull(one.getId());
		assertEquals("one", one.getName());

		user.setName("updatedName");
		dao.saveOrUpdate(user);
		user = dao.get(user.getId());
		assertNotNull(user);
		assertEquals("updatedName", user.getName());
		assertEquals("userName", user.getUsername());
	}

	@Test
	public void testDelete() {
		dao.delete(user);
		User loaded = dao.get(user.getId());
		assertNull(loaded);
	}

	@Test
	public void testDeleteById() {
		User one = dao.deleteById(user.getId());
		assertNotNull(one);
		assertEquals(one.getId(), user.getId());
		User loaded = dao.get(user.getId());
		assertNull(loaded);
	}

	@Test
	public void testRefresh() {
		user.setName("updated");
		dao.refresh(user);
		assertEquals("测试用户_0", user.getName());
	}

	@Test
	@Transactional
	public void testFindByPageIntIntOrderArray() {
		Order[] orders = new Order[] { Order.asc("username") };
		Page<User> page = dao.findByPage(0, 5, orders);
		assertEquals(5, page.getPageSize());
		assertEquals(2, page.getPageAmount());
		assertEquals(10, page.getTotal());
		assertEquals(5, page.getList().size());
		User f = page.getList().get(0);
		assertEquals("测试用户_0", f.getName());
	}

	@Test
	public void testFindByPageIntIntMapOfStringObjectMapOfStringStringOrderArray() {
		Map<String, Object> eqConditions = new HashMap<String, Object>();
		Map<String, String> likeConditions = new HashMap<String, String>();
		eqConditions.put("sex", (short) 1);
		likeConditions.put("name", "测试用户");
		Order[] orders = new Order[] { Order.asc("name") };
		Page<User> page = dao.findByPage(0, 5, eqConditions, likeConditions, orders);
		assertEquals(5, page.getPageSize());
		assertEquals(2, page.getPageAmount());
		assertEquals(9, page.getTotal());
		assertEquals(5, page.getList().size());
		User f = page.getList().get(0);
		assertEquals("测试用户_1", f.getName());
	}

	@Test
	public void testFindByPageQueryConditionIntInt() {
		QueryCondition qc = dao.getQC();
		Page<User> page = dao.findByPage(qc, 0, 5);
		assertEquals(5, page.getPageSize());
		assertEquals(2, page.getPageAmount());
		assertEquals(10, page.getTotal());
		assertEquals(5, page.getList().size());
		User f = page.getList().get(0);
		assertEquals("测试用户_0", f.getName());
	}

	@Test
	public void testGetQC() {
		QueryCondition qc = dao.getQC();
		assertNotNull(qc);
	}

	@Test
	public void testFindAll() {
		List<User> list = dao.findAll();
		assertEquals(10, list.size());
		User f = list.get(0);
		assertEquals("测试用户_0", f.getName());
	}

	@Test
	public void testFindByPropertyStringObject() {
		List<User> list = dao.findByProperty("sex", (short) 1);
		assertEquals(9, list.size());
		User f = list.get(0);
		assertEquals(Short.valueOf((short) 1), f.getSex());
	}

	@Test
	public void testFindByPropertyStringObjectStringArray() {
		List<User> list = dao.findByProperty(new String[] { "sex", "name" }, new Object[] { (short) 1, "测试用户_5" },
				null);
		assertEquals(1, list.size());
		User f = list.get(0);
		assertEquals(Short.valueOf((short) 1), f.getSex());
		assertEquals("测试用户_5", f.getName());
	}

	@Test
	public void testFindByPropertyStringArrayObjectArrayStringArray() {
		List<User> list = dao.findByProperty(new String[] { "sex", "name" }, new Object[] { (short) 0, "测试用户_0" },
				new String[] { "organization" });
		assertEquals(1, list.size());
		User f = list.get(0);
		assertEquals(Short.valueOf((short) 0), f.getSex());
		assertEquals("测试用户_0", f.getName());
		boolean initialized = Hibernate.isInitialized(f.getOrganization());
		assertEquals(true, initialized);
	}

	@Test
	public void testGetByPropertyStringObject() {
		User user = dao.getByProperty("username", "userName");
		assertNotNull(user);
		assertEquals("userName", user.getUsername());
	}

	@Test
	public void testGetByPropertyStringObjectStringArray() {
		User user = dao.getByProperty("username", "userName", new String[] { "organization" });
		assertNotNull(user);
		assertEquals("userName", user.getUsername());

		boolean initialized = Hibernate.isInitialized(user.getOrganization());
		assertEquals(true, initialized);
	}

	@Test
	public void testUpdatePropertiesById() throws Exception {
		Map<String, Object> updateSet = new HashMap<>();
		updateSet.put("name", "测试用户_0_new");
		updateSet.put("sex", (short) 1);
		int result = dao.updatePropertiesById(updateSet, user.getId());
		assertEquals(1, result);
		User u = dao.load(user.getId());
		dao.refresh(u);
		assertNotNull(u);
		assertEquals("测试用户_0_new", u.getName());
		assertEquals(Short.valueOf((short) 1), u.getSex());
	}

	@Test
	public void testUpdateProperties() {
		Map<String, Object> updateSet = new HashMap<>();
		Map<String, Object> updateWhere = new HashMap<>();
		updateWhere.put("username", "userName");
		updateSet.put("name", "测试用户_0_new");
		updateSet.put("sex", (short) 1);
		int result = dao.updateProperties(updateSet, updateWhere);
		assertEquals(1, result);
		User u = dao.load(user.getId());
		dao.refresh(u);
		assertNotNull(u);
		assertEquals("测试用户_0_new", u.getName());
		assertEquals(Short.valueOf((short) 1), u.getSex());
	}

	@Test
	public void testListByQCQueryCondition() {
		QueryCondition qc = dao.getQC();
		qc.eq("sex", (short) 1);
		List<User> list = dao.listByQC(qc);
		assertEquals(9, list.size());
		User f = list.get(0);
		assertEquals(Short.valueOf((short) 1), f.getSex());
		assertEquals("测试用户_1", f.getName());
	}

	@Test
	public void testListByQCQueryConditionIntInt() {
		QueryCondition qc = dao.getQC();
		qc.eq("sex", (short) 1);
		List<User> list = dao.listByQC(qc, 1, 5);
		assertEquals(5, list.size());
		User f = list.get(0);
		assertEquals(Short.valueOf((short) 1), f.getSex());
		assertEquals("测试用户_2", f.getName());
	}

	@Test
	public void testUniqueByQC() {
		QueryCondition qc = dao.getQC();
		qc.eq("username", "userName");
		User user = dao.uniqueByQC(qc);
		assertNotNull(user);
		assertEquals("userName", user.getUsername());
	}

	@Test
	public void testCountByQC() {
		QueryCondition qc = dao.getQC();
		qc.eq("sex", (short) 1);
		long count = dao.countByQC(qc);
		assertEquals(9, count);
	}

	@Test
	public void testCountByProperties() {
		long count = dao.countByProperties(new String[] { "sex" }, new Object[] { (short) 1 });
		assertEquals(9, count);
	}

	@Test
	public void testDeleteAll() {
		dao.deleteAll();
		List<User> list = dao.findAll();
		assertEquals(0, list.size());
	}

}
