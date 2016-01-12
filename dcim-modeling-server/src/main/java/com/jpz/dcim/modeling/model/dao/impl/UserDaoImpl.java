package com.jpz.dcim.modeling.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.jpz.dcim.modeling.model.dao.UserDao;
import com.jpz.dcim.modeling.model.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {

	

	@Override
	public void method4Test() {
		throw new RuntimeException("unimplemented method ,only for test");
	}
}
