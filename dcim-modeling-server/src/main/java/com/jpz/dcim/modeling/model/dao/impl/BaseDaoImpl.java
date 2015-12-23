package com.jpz.dcim.modeling.model.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import pers.ksy.common.orm.jpa.AbstractJpaBaseDAO;

import com.jpz.dcim.modeling.model.dao.BaseDao;

@Repository
public abstract class BaseDaoImpl<T, ID extends Serializable> extends
		AbstractJpaBaseDAO<T, ID> implements BaseDao<T, ID> {

	// 注入实体管理器
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}
}