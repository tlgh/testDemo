package com.jpz.dcim.modeling.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import pers.ksy.common.MD5Util;
import pers.ksy.common.model.Page;
import pers.ksy.common.model.Result;
import pers.ksy.common.orm.QueryCondition;

import com.jpz.dcim.modeling.model.dao.OrganizationDao;
import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.PartyService;


@Transactional
@Service
public class PartyServiceImpl implements PartyService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrganizationDao organizationDao;

	@Override
	public Result<User> login(String username, String password) {
		Result<User> result = Result.errorResult(null);
		User user = userDao.getByProperty("username", username);
		if (null != user) {
			if (password.equals(user.getPassword())) {
				result.success(user, null);
			} else {
				result.setMessage("密码错误");
			}
		} else {
			result.setMessage("用户不存在");
		}
		return result;
	}
	
	@Override
	public List<Organization> organizationList() {
		return organizationDao.findAll();
	}

	@Override
	public List<User> findUserByOrg(String organizationId) {
		return userDao.findByProperty("organization.id", organizationId);
	}

	@Override
	public Result addUser(User user,String orgId) {
		user.setPassword(MD5Util.MD5("123456"));
		user.setCreateTime(new Date());
		
		if(orgId!=null && orgId.isEmpty()==false){
			Organization org = this.getOrganization(orgId);
			user.setOrganization(org);
		}
		userDao.save(user);
		return Result.successResult();
	}

	@Override
	public Result updateUser(User user) {
		Assert.notNull(user.getId());
		User old = userDao.get(user.getId());
		Result result = Result.errorResult(null);
		if (null != old) {
			user.setUsername(old.getUsername());
			user.setPassword(old.getPassword());
			user.setCreateTime(old.getCreateTime());
			user.setLastLoginTime(old.getLastLoginTime());
			user.setLastModifyTime(new Date());
			userDao.save(user);
			result.success();
		} else {
			result.setMessage("用户不存在");
		}
		return result;
	}

	@Override
	public User getUser(String userId) {
		return userDao.get(userId);
	}

	@Override
	public Result deleteUser(String userId) {
		userDao.deleteById(userId);
		return Result.successResult();
	}

	@Override
	public Result addOrganization(Organization organization,String parentId) {		
		organization.setCreateTime(new Date());		
		if(parentId!=null){
			Organization parent = this.getOrganization(parentId);
			organization.setParent(parent);
		}
		organizationDao.save(organization);
		return Result.successResult(organization, null);
	}
	
	@Override
	public Result updateOrganization(Organization Organization) {
		Assert.notNull(Organization.getId());
		Organization old = organizationDao.get(Organization.getId());
		Assert.notNull(old);
		Result result = null;
		
		Organization.setCreateTime(old.getCreateTime());
		Organization.setLastModifyTime(new Date());
		organizationDao.update(Organization);
		result = Result.successResult();
		
		return result;
	}

	@Override
	public Organization getOrganization(String organizationId) {
		return organizationDao.get(organizationId);
	}

	@Override
	public Result deleteOrganization(String organizationId) {
		organizationDao.deleteById(organizationId);
		return Result.successResult();
	}

	@Override
	public Page<User> findPage(int pageIndex, int pageSize,
			QueryCondition queryCondition) {
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

}
