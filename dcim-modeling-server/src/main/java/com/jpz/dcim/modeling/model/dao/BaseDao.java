package com.jpz.dcim.modeling.model.dao;

import java.io.Serializable;

import pers.ksy.common.orm.jpa.JpaBaseDAO;

public interface BaseDao<T, ID extends Serializable> extends JpaBaseDAO<T, ID> {


}