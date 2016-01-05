package com.jpz.dcim.modeling.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.model.dao.OrganizationDao;
import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.PartyService;

import pers.ksy.common.MD5Util;
import pers.ksy.common.model.Page;
import pers.ksy.common.orm.IsCondition;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.jpa.JpaHelper;

@Transactional
@Service
public class PartyServiceImpl extends BaseServiceImpl<User, String> implements PartyService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrganizationDao organizationDao;

	@Override
	public User login(String username, String password) throws ServiceException {
		User user = userDao.getByProperty("username", username);
		if (null == user) {
			throw new ServiceException("用户不存在");
		} else if (!password.equals(user.getPassword())) {
			throw new ServiceException("密码错误");
		}
		JpaHelper.initialize(user.getOrganization());
		return user;
	}

	@Override
	public List<Organization> organizationList() {
		List<Organization> list = organizationDao.findAll();
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Organization> organizationTree() {
		QueryCondition qc = organizationDao.getQC().is("parent.id", IsCondition.Type.NULL);
		List<Organization> list = organizationDao.listByQC(qc);
		for (Organization organization : list) {
			traverseOrganizationTree(organization);
		}
		return list;
	}

	private void traverseOrganizationTree(Organization organization) {
		if (null == organization) {
			return;
		}
		JpaHelper.initialize(organization.getPrincipal());
		for (Organization child : organization.getChildren()) {
			traverseOrganizationTree(child);
		}
	}

	@Override
	public List<User> findUserByOrg(String organizationId) {
		return userDao.findByProperty("organization.id", organizationId);
	}

	@Override
	public void addUser(User user, String orgId) {
		user.setPassword(MD5Util.MD5("123456"));
		user.setCreateTime(new Date());
		if (orgId != null && orgId.isEmpty() == false) {
			Organization org = this.getOrganization(orgId);
			user.setOrganization(org);
		}
		userDao.save(user);
	}

	@Override
	public void updateUser(User user) throws ServiceException {
		Assert.notNull(user.getId());
		User old = userDao.get(user.getId());
		if (null == old) {
			throw new ServiceException("用户不存在");
		}
		user.setUsername(old.getUsername());
		user.setPassword(old.getPassword());
		user.setCreateTime(old.getCreateTime());
		user.setLastLoginTime(old.getLastLoginTime());
		user.setLastModifyTime(new Date());
		userDao.save(user);
	}

	@Override
	public User getUser(String userId) {
		return userDao.get(userId);
	}

	@Override
	public void deleteUser(String userId) {
		userDao.deleteById(userId);
	}

	@Override
	public void addOrganization(Organization organization, String parentId) {
		organization.setCreateTime(new Date());
		if (parentId != null) {
			Organization parent = this.getOrganization(parentId);
			organization.setParent(parent);
		}
		organizationDao.save(organization);
	}

	@Override
	public void updateOrganization(Organization Organization) throws ServiceException {
		Assert.notNull(Organization.getId());
		Organization old = organizationDao.get(Organization.getId());
		if (null == old) {
			throw new ServiceException("部门不存在");
		}
		Organization.setCreateTime(old.getCreateTime());
		Organization.setLastModifyTime(new Date());
		organizationDao.update(Organization);
	}

	@Override
	public Organization getOrganization(String organizationId) {
		Organization organization = organizationDao.getByProperty("id", organizationId, new String[] { "principal" });
		if (null == organization) {
			throw new ServiceException("组织机构不存在");
		}
		return organization;
	}

	@Override
	public void deleteOrganization(String organizationId) {
		organizationDao.deleteById(organizationId);
	}

	@Override
	public Page<User> findPage(int pageIndex, int pageSize, QueryCondition queryCondition) {
		return userDao.findByPage(queryCondition, pageIndex, pageSize);
	}

	@Override
	public void deleteAllUsers() {
		userDao.deleteAll();
	}

	@Override
	public void deleteAllOrganizations() {
		organizationDao.deleteAll();
	}

	@Override
	public void method4Test() {
		throw new RuntimeException("unimplemented service method, only for test");
	}

	@Override
	public BaseDao<User, String> getDao() {
		return userDao;
	}

}
