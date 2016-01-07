package com.jpz.dcim.modeling.model.dao;

import java.io.Serializable;

import pers.ksy.common.orm.hibernate4.HibernateBaseDAO;

public interface BaseDao<T, ID extends Serializable> extends HibernateBaseDAO<T, ID> {


}