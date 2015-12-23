package com.jpz.dcim.modeling.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.jpz.dcim.modeling.model.dao.RoleDao;
import com.jpz.dcim.modeling.model.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, String> implements RoleDao {

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}

}
