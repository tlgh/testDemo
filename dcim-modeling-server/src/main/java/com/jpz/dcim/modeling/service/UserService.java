package com.jpz.dcim.modeling.service;

import java.util.List;

import com.jpz.dcim.modeling.exception.ServiceException;
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
	 * 更新用户
	 */
	public User update(User user);
	
	/**
	 * 逻辑删除用户
	 * @param user
	 * @return
	 */
	public User delete(String id);
	
	/**
	 * 移动用户位置到当前部门另一个用户之后
	 * @param user
	 * @param index
	 * @return
	 */
	public User moveUserBefore(User user,String otherId);
	
	/**
	 * 移动用户到当前部门的最后位置
	 * @param user1
	 * @return
	 */
	public User moveUserLast(User user1);
	
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
	
	/**
	 * 根据登录名查找用户
	 * @return
	 */
	public User getUserByUsername(String username);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	User login(String username, String password) throws ServiceException;

	
}
