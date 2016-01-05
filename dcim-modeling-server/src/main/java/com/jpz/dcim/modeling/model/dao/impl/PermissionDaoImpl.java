package com.jpz.dcim.modeling.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.jpz.dcim.modeling.model.dao.PermissionDao;
import com.jpz.dcim.modeling.model.entity.Permission;

@Repository
public class PermissionDaoImpl extends BaseDaoImpl<Permission, String>
		implements PermissionDao {

	@Override
	protected Class<Permission> getEntityClass() {
		return Permission.class;
	}


}
