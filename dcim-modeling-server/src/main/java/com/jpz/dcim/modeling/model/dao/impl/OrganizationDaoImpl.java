package com.jpz.dcim.modeling.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.jpz.dcim.modeling.model.dao.OrganizationDao;
import com.jpz.dcim.modeling.model.entity.Organization;

@Repository
public class OrganizationDaoImpl extends BaseDaoImpl<Organization, String>
		implements OrganizationDao {

	@Override
	protected Class<Organization> getEntityClass() {
		return Organization.class;
	}

}
