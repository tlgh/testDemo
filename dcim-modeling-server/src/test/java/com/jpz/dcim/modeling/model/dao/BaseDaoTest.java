package com.jpz.dcim.modeling.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.jpz.dcim.modeling.BaseTestCase;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;


/**
 * 因为BaseDaoImpl是一个abstract类，所以，我们在userDao中测试baseDao方法
 * @author Administrator
 *
 */
@Transactional
public class BaseDaoTest extends BaseTestCase{
	@Resource 
	UserDao dao ;
	@Resource
	OrganizationDao orgDao;
	
	
	User user = null;
	
	@Before	
	public void setup() throws Exception{
		dao.deleteAll();
		orgDao.deleteAll();
		
		user = new User();
		user.setUsername("userName");
		user.setName("姓名");
		user.setCreateTime(new Date());
		user.setLastModifyTime(new Date());
		user.setSex((short)0);
		user.setPassword("123456");
		dao.save(user);
		
		create10Users();
	}
	
	private void create10Users(){
		for(int i=0;i<10;i++){
			User u = new User();
			u.setUsername("user_"+i);
			u.setName("测试用户_"+i);
			u.setCreateTime(new Date());
			u.setLastModifyTime(new Date());
			u.setSex((short)1);
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
		user1.setSex((short)0);
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
		assertEquals("updated",user.getName());
		assertEquals("userName",user.getUsername());
	}

	@Test
	public void testSaveOrUpdate() {
		User one = new User();
		one.setName("one");
		one.setUsername("one4Login");
		dao.saveOrUpdate(one);
		one = dao.get(one.getId());
		assertNotNull(one);
		assertNotNull(one.getId());
		assertEquals("one",one.getName());
		
		user.setName("updatedName");
		dao.saveOrUpdate(user);
		user = dao.get(user.getId());
		assertNotNull(user);
		assertEquals("updatedName",user.getName());
		assertEquals("userName",user.getUsername());
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
		assertEquals(one.getId(),user.getId());		
		User loaded = dao.get(user.getId());
		assertNull(loaded);
	}

	@Test
	public void testRefresh() {
		user.setName("updated");
		dao.refresh(user);
		assertEquals("姓名",user.getName());
	}

	@Test
	public void testFindByPageIntIntOrderArray() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPageIntIntMapOfStringObjectMapOfStringStringOrderArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPageQueryConditionIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQC() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPropertyStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPropertyStringObjectStringArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPropertyStringArrayObjectArrayStringArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByPropertyStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByPropertyStringObjectStringArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePropertiesById() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateProperties() {
		fail("Not yet implemented");
	}

	@Test
	public void testListByQCQueryCondition() {
		fail("Not yet implemented");
	}

	@Test
	public void testListByQCQueryConditionIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testUniqueByQC() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountByQC() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountByProperties() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAll() {
		fail("Not yet implemented");
	}

}
