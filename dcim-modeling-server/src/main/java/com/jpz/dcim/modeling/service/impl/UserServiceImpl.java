package com.jpz.dcim.modeling.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

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
		
		org.addMembers(user);
		user.setPosition(org.getMembers().size()-1);
		user.setOrganization(org);
		userDao.save(user);
		return user;
	}
	
	@Override
	public User update(User user)throws ServiceException{		
		User u = userDao.update(user, "name","sex","email","mobile","birthday","lastModifyTime");
		return u;
	}
	
	@Override
	public User delete(String id)throws ServiceException{
		User user = userDao.get(id);
		user.setDeleted(true);
		userDao.update(user);
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
		if(oldIndex==newIndex)
			return user;
		
		if(oldIndex!=-1){
			if(newIndex<oldIndex){
				brothers.remove(oldIndex);
				brothers.add(newIndex, user);
			}else{
				brothers.add(newIndex,user);
				brothers.remove(oldIndex);
			}
		}else
			throw new ServiceException("当前用户不存在！");
		Map<String,Object> upset = new HashMap<String,Object>();
		int i = 0;
		for(User one :brothers){
			one.setPosition(i);
			userDao.update(one, "position");
			i++;
		}
		return user;
	}
	
	@Override
	public User moveUserLast(User user) {
		Organization org =user.getOrganization();
		List<User> members = org.getMembers();
		int p = members.indexOf(user);
		members.remove(p);
		members.add(user);
		for(int i=p;i<members.size();i++){
			User u = members.get(i);
			u.setPosition(i);
			this.update(u, "position");
		}
		return user;
	}

	@Override
	public User moveUserTo(User user, String orgId) {		
		Organization org = orgDao.get(orgId);
		if(org==null)
			throw new ServiceException("指定的部门不存在！");
		Organization old = user.getOrganization();
		if(old!=null)
			old.getMembers().remove(user);
		
		org.addMembers(user);
		user.setOrganization(org);
		userDao.update(user, "organization");
		return user;
	}

	@Override
	public List<User> getUsersInOrg(String orgId) {
		QueryCondition condition = new QueryConditionImpl(User.class,null);
		Conditions.and(Conditions.eq("organization.id", orgId), Conditions.eq("deleted", false));		
		List<User> users = userDao.listByQC(condition);
		return users;
	}


	@Override
	public User getUserByUsername(String username) {
		QueryCondition qc = new QueryConditionImpl(User.class,null);
		qc.add(Conditions.eq("username", username));
		User user = userDao.uniqueByQC(qc);
		return user;
	}


	

}
