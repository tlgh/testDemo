package com.jpz.dcim.modeling.model.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.jpz.dcim.modeling.model.dao.BaseDao;

import pers.ksy.common.orm.hibernate4.AbstractHibernateBaseDAO;

@Repository
public abstract class BaseDaoImpl<T, ID extends Serializable> extends AbstractHibernateBaseDAO<T, ID>
		implements BaseDao<T, ID> {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}