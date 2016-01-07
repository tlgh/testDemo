package com.jpz.dcim.modeling.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.dao.OrganizationDao;
import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.PartyService;

import pers.ksy.common.MD5Util;
import pers.ksy.common.StringUtil;
import pers.ksy.common.model.Page;
import pers.ksy.common.orm.IsCondition;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.jpa.JpaHelper;

@Transactional
@Service
public class PartyServiceImpl extends CommonService implements PartyService {
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
		user.setLastLoginTime(new Date());
		userDao.update(user);
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
	public User addUser(User user, String orgId) {
		user.setPassword(MD5Util.MD5("123456"));
		if (StringUtil.notEmpty(orgId)) {
			user.setOrganization(organizationDao.get(orgId));
		}
		return (User) save(user);
	}

	@Override
	public User updateUser(User user) throws ServiceException {
		return (User) update(user, new String[] { "roles", "username", "password", "lastLoginTime" }, false);
	}
	
	@Override
	public User getUser(String userId) {
		User user = userDao.getByProperty("id", userId, new String[] { "organization" });
		if (null == user) {
			throw new ServiceException("用户不存在");
		}
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		userDao.deleteById(userId);
	}

	@Override
	public Organization addOrganization(Organization organization, String parentId) {
		if(StringUtil.notEmpty(parentId)){
			organization.setParent(organizationDao.get(parentId));
		}
		return (Organization) save(organization);
	}

	@Override
	public Organization updateOrganization(Organization organization) throws ServiceException {
		return (Organization) update(organization);
	}

	@Override
	public Organization getOrganization(String organizationId) throws ServiceException{
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

}
