package com.jpz.dcim.modeling.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.OrganizationService;

import pers.ksy.common.orm.Conditions;
import pers.ksy.common.orm.IsCondition.Type;
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
			parent.addChildren(org);
			org.setParent(parent);
			org.setPosition(parent.getChildren().size()-1);
		}
		dao.save(org);
		if(org.getShouldBeforeAt()!=null && org.getShouldBeforeAt().equals("-1")==false){
			this.moveOrgBefore(org, org.getShouldBeforeAt());
		}
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
		if(other.equals(org))
			return org;
		
		int oldIndex = brothers.indexOf(org);
		int newIndex = brothers.indexOf(other);
		
		if(oldIndex!=-1){
			if(newIndex<oldIndex){
				brothers.remove(oldIndex);
				brothers.add(newIndex, org);
			}else{
				brothers.add(newIndex,org);
				brothers.remove(oldIndex);
			}
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
	public Organization moveToLast(Organization org){
		Organization parent =org.getParent();
		List<Organization> brothers = org.getChildren();
		int p = brothers.indexOf(org);
		brothers.remove(p);
		brothers.add(org);
		for(int i=p;i<brothers.size();i++){
			Organization o = brothers.get(i);
			o.setPosition(i);
			this.update(o, "position");
		}
		return org;
	}

	@Override
	public Organization getRoot() {
		QueryCondition qc = new QueryConditionImpl(Organization.class,null);
		qc.add(Conditions.is("parent.id", Type.NULL)).add(Conditions.eq("deleted", false));
		Organization root = dao.uniqueByQC(qc);
		return root;
	}

	public Organization update(Organization org){
		org.setLastModifyTime(new Date());
		return dao.update(org, "name","description","principal","deleted","lastModifyTime");
	}

}
