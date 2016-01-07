package com.jpz.dcim.modeling.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.jpz.dcim.modeling.service.BaseService;

import pers.ksy.common.model.Page;
import pers.ksy.common.orm.Order;
import pers.ksy.common.orm.QueryCondition;


@Transactional
public abstract class BaseServiceImpl<T ,ID extends Serializable> implements BaseService<T,ID> {	
	public T get(ID id) {
		return getDao().get(id);
	}

	public T save(T t) {
		return getDao().save(t);
	}

	public Serializable saveAny(Serializable t) {
		return getDao().saveAny(t);
	}

	public T update(T entity) {
		return getDao().update(entity);
	}
	
	public T update(T entity,String... fieldNames){
		return getDao().update(entity,fieldNames);
	}

	public T saveOrUpdate(T entity) {
		return getDao().saveOrUpdate(entity);
	}

	public T delete(T entity) {
		return getDao().delete(entity);
	}

	public T deleteById(ID id) {
		return getDao().deleteById(id);
	}

	public void refresh(Object entity) {
		getDao().refresh(entity);
	}

	public Page<T> findByPage(int pageIndex, int pageSize, Order[] orders) {
		return getDao().findByPage(pageIndex, pageSize, orders);
	}

	public Page<T> findByPage(int pageIndex, int pageSize, Map<String, Object> eqConditions,
			Map<String, String> likeConditions, Order[] orders) {
		return getDao().findByPage(pageIndex, pageSize, eqConditions, likeConditions, orders);
	}

	public Page<T> findByPage(QueryCondition qc, int pageIndex, int pageSize) {
		return getDao().findByPage(qc, pageIndex, pageSize);
	}

	public QueryCondition getQC() {
		return getDao().getQC();
	}

	public List<T> findAll() {
		return getDao().findAll();
	}

	public List<T> findAll(String[] joins) {
		return getDao().findAll(joins);
	}

	public List<T> findByProperty(String propName, Object propVal) {
		return getDao().findByProperty(propName, propVal);
	}

	public List<T> findByProperty(String propName, Object propVal, String[] joins) {
		return getDao().findByProperty(propName, propVal, joins);
	}

	public List<T> findByProperty(String[] propNames, Object[] propVals, String[] joins) {
		return getDao().findByProperty(propNames, propVals, joins);
	}

	public T getByProperty(String propName, Object propVal) {
		return getDao().getByProperty(propName, propVal);
	}

	public T getByProperty(String propName, Object propVal, String[] joins) {
		return getDao().getByProperty(propName, propVal, joins);
	}

	public int updatePropertiesById(Map<String, Object> updateSet, ID id) {
		return getDao().updatePropertiesById(updateSet, id);
	}

	public int updateProperties(Map<String, Object> updateSet, Map<String, Object> updateWhere) {
		return getDao().updateProperties(updateSet, updateWhere);
	}

	public List listByQC(QueryCondition qc) {
		return getDao().listByQC(qc);
	}

	public List listByQC(QueryCondition qc, int first, int max) {
		return getDao().listByQC(qc, first, max);
	}

	public Object uniqueByQC(QueryCondition qc) {
		return getDao().uniqueByQC(qc);
	}

	public Long countByQC(QueryCondition qc) {
		return getDao().countByQC(qc);
	}

	public Long countByProperties(String[] propNames, Object[] propVals) {
		return getDao().countByProperties(propNames, propVals);
	}
	
	
	public void deleteAll() {
		getDao().deleteAll();
	}

	
	@Override
	public List<T> findList(QueryCondition qc) {
		return getDao().listByQC(qc);
	}

	@Override
	public List<T> findList(QueryCondition qc, int first, int max) {
		return getDao().listByQC(qc, first, max);
	}

	@Override
	public Object findOne(QueryCondition qc) {
		return getDao().uniqueByQC(qc);
	}

	@Override
	public Long count(QueryCondition qc) {
		return getDao().countByQC(qc);
	}

	
}
