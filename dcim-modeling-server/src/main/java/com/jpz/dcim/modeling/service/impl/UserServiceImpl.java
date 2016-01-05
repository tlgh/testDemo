package com.jpz.dcim.modeling.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.model.dao.OrganizationDao;
import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.UserService;

import pers.ksy.common.orm.Conditions;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.QueryConditionImpl;


@Transactional
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	OrganizationDao orgDao;

	@Override
	public BaseDao<User, String> getDao() {
		return userDao;
	}

	

	@Override
	public User addUser(User user, String orgId)throws ServiceException {
		if(orgId==null)
			throw new ServiceException("未指定该用户所在的部门！");
		Organization org = orgDao.get(orgId);
		if(org==null)
			throw new ServiceException("指定的用户部门不存在！");
		
		user.setOrganization(org);
		userDao.save(user);
		return user;
	}

	private User getOther(List<User> users,String otherId){
		for(User user:users){
			if(user.getId().equals(otherId)){
				return user;
			}	
		}
		return null;
	}
	
	@Override
	public User moveUserBefore(User user, String otherId)throws ServiceException {
		List<User> brothers = this.getUsersInOrg(user.getOrganization().getId());
		User other = this.getOther(brothers,otherId);
		if(other ==null)
			throw new ServiceException("当前部门不存在id="+otherId+"的用户！");
		int oldIndex = brothers.indexOf(user);
		int newIndex = brothers.indexOf(other);
		brothers.add(newIndex, user);
		if(oldIndex!=-1){
			brothers.remove(oldIndex);
		}else
			throw new ServiceException("当前用户不存在！");
		Map<String,Object> upset = new HashMap<String,Object>();
		int i = 0;
		for(User one :brothers){
			upset.put("position", i);
			userDao.updatePropertiesById(upset, one.getId());
			i++;
		}
		return user;
	}

	@Override
	public User moveUserTo(User user, String orgId) {
		Organization org = orgDao.get(orgId);
		if(org==null)
			throw new ServiceException("指定的部门不存在！");
		
		user.setOrganization(org);
		userDao.update(user);
		return user;
	}

	@Override
	public List<User> getUsersInOrg(String orgId) {
		QueryCondition condition = new QueryConditionImpl(User.class,null);
		Conditions.and(Conditions.eq("organization.id", orgId), Conditions.eq("deleted", false));		
		List<User> users = userDao.listByQC(condition);
		return users;
	}

}
