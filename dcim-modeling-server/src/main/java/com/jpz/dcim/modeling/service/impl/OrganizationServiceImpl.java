package com.jpz.dcim.modeling.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.model.dao.OrganizationDao;
import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.service.OrganizationService;

import pers.ksy.common.orm.Conditions;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.QueryConditionImpl;

@Transactional
@Service
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, String> implements OrganizationService {
	@Autowired
	private OrganizationDao dao;
	
	@Override
	public BaseDao<Organization, String> getDao() {
		return dao;
	}

	@Override
	public Organization addOrg(Organization org, String parentId) {
		Organization parent = parentId==null?null:dao.get(parentId);
		if(parent!=null){
			org.setParent(parent);
		}
		dao.save(org);
		return org;
	}
	
	private Organization getOther(Collection<Organization> orgs ,String otherId){
		for(Organization one:orgs){
			if(one.getId().equals(otherId)){
				return one;
			}
		}
		return null;
	}

	@Override
	public Organization moveOrgBefore(Organization org, String otherId) {
		Organization parent = org.getParent();
		if(parent==null)
			throw new ServiceException("当前部门已经是根部门，无法移动位置！");
		
		List<Organization> brothers = parent.getChildren();
		Organization other = this.getOther(brothers,otherId);
		
		if(other ==null)
			throw new ServiceException("当前部门不存在id="+otherId+"的兄弟部门！");
		
		int oldIndex = brothers.indexOf(org);
		int newIndex = brothers.indexOf(other);
		brothers.add(newIndex, org);
		if(oldIndex!=-1){
			brothers.remove(oldIndex);
		}else
			throw new ServiceException("当前部门不存在！");
		Map<String,Object> upset = new HashMap<String,Object>();
		int i = 0;
		for(Organization one :brothers){
			upset.put("position", i);
			dao.updatePropertiesById(upset, one.getId());
			i++;
		}
		return org;
	}

	@Override
	public Organization getRoot() {
		QueryCondition qc = new QueryConditionImpl(Organization.class,null);
		qc.add(Conditions.eq("parent.id", null)).add(Conditions.eq("deleted", false));
		Organization root = dao.uniqueByQC(qc);
		return root;
	}


}
