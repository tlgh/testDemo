package com.jpz.dcim.modeling.service;

import java.util.List;

import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;

import pers.ksy.common.model.Page;
import pers.ksy.common.orm.QueryCondition;

public interface PartyService {
	/**
	 * 组织机构列表
	 * 
	 * @return
	 */
	@Deprecated
	List<Organization> organizationList();

	/**
	 * 根据组织机构获取用户列表
	 * @param organizationId
	 * @return
	 */
	List<User> findUserByOrg(String organizationId);
	
	
	/**
	 * 新增用户
	 * @param user
	 * @param orgId
	 * @return 
	 */
	User addUser(User user,String orgId);

	/**
	 * 修改用户
	 * @param user
	 * @throws ServiceException
	 */
	User updateUser(User user) throws ServiceException;
	
	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	User getUser(String userId) throws ServiceException;
	
	/**
	 * 删除用户
	 * @param userId
	 */
	void deleteUser(String userId);
	
	/**
	 * 将一个机构增加到父机构下，如果parentId为空，则当前机构为顶级机构
	 * @param organization
	 * @param parentId
	 * @return
	 */
	Organization addOrganization(Organization organization,String parentId);
	
	/**
	 * 更新机构
	 * @param organization
	 * @throws ServiceException
	 */
	Organization updateOrganization(Organization organization) throws ServiceException;

	/**
	 * 获取机构信息
	 * @param organizationId
	 * @return
	 */
	Organization getOrganization(String organizationId) throws ServiceException;
	
	/**
	 * 删除机构
	 * @param organizationId
	 */
	void deleteOrganization(String organizationId);

	/**
	 * 用户分页查询
	 * @param pageIndex
	 * @param pageSize
	 * @param queryCondition
	 * @return
	 */
	Page<User> findPage(int pageIndex, int pageSize,
			QueryCondition queryCondition);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	User login(String username, String password) throws ServiceException;
	
	/**
	 * 删除所有用户
	 */
	void deleteAllUsers();
	
	/**
	 * 删除所有组织机构
	 */
	void deleteAllOrganizations();

	/**
	 * 组织机构树
	 * 
	 * @return
	 */
	List<Organization> organizationTree();
	
	void method4Test();
	
}
