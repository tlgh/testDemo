package com.jpz.dcim.modeling.service;

import java.util.List;

import pers.ksy.common.model.Page;
import pers.ksy.common.model.Result;
import pers.ksy.common.orm.QueryCondition;

import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;

@SuppressWarnings("rawtypes")
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
	
	
	void addUser(User user,String orgId);

	void updateUser(User user);
	
	User getUser(String userId);
	
	void deleteUser(String userId);
	
	/**
	 * 将一个机构增加到父机构下，如果parentId为空，则当前机构为顶级机构
	 * @param organization
	 * @param parentId
	 * @return
	 */
	void addOrganization(Organization organization,String parentId);
	
	void updateOrganization(Organization Organization);

	Organization getOrganization(String organizationId);
	
	void deleteOrganization(String organizationId);

	Page<User> findPage(int pageIndex, int pageSize,
			QueryCondition queryCondition);

	User login(String username, String password);
	
	void deleteAllUsers();
	
	void deleteAllOrganizations();

	/**
	 * 组织机构树
	 * 
	 * @return
	 */
	List<Organization> organizationTree();
	
}
