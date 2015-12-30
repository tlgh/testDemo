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
	List<Organization> organizationList();

	/**
	 * 根据组织机构获取用户列表
	 * @param organizationId
	 * @return
	 */
	List<User> findUserByOrg(String organizationId);
	
	
	Result addUser(User user);

	Result updateUser(User user);
	
	User getUser(String userId);
	
	Result deleteUser(String userId);

	Result addOrganization(Organization organization);

	Result updateOrganization(Organization Organization);

	Organization getOrganization(String organizationId);
	
	Result deleteOrganization(String organizationId);

	Page<User> findPage(int pageIndex, int pageSize,
			QueryCondition queryCondition);

	Result login(String username, String password);
	
	void deleteAllUsers();
	
	void deleteAllOrganizations();
	
}
