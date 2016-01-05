package com.jpz.dcim.modeling.service;

import java.util.List;

import com.jpz.dcim.modeling.model.entity.User;

public interface UserService extends BaseService<User,String>{
	/**
	 * 在某机构下新增一个用户
	 * @param user
	 * @param orgId
	 * @return
	 */
	public User addUser(User user,String orgId);
	
	/**
	 * 移动用户位置到当前部门另一个用户之后
	 * @param user
	 * @param index
	 * @return
	 */
	public User moveUserBefore(User user,String otherId);
	
	/**
	 * 转移用户到另一个部门，放在该部门的最后一位
	 * @param user
	 * @param orgId
	 * @return
	 */
	public User moveUserTo(User user,String orgId);
	
	/**
	 * 获取当前部门内所有用户
	 * @param orgId
	 * @return
	 */
	public List<User> getUsersInOrg(String orgId);
}
