package pers.ksy.common.orm.jpa;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import pers.ksy.common.model.Page;

@SuppressWarnings("rawtypes")
public abstract class JpaSimpleDAO<T> {

	// abstracts

	/**
	 * 获取当前DAO对应的entity的class类
	 * 
	 * @return
	 */
	protected abstract Class<T> getEntityClass();

	protected abstract EntityManager getEntityManager();

	// state

	protected Query createQuery(String qlString, Object... params) {
		Query query = getEntityManager()
				.createQuery(qlString, getEntityClass());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}

	protected Query createSqlQuery(String sqlString, Object... params) {
		Query query = getEntityManager().createNativeQuery(sqlString,
				getEntityClass());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}

	protected List find(String qlString, Object... values) {
		Query query = createQuery(qlString, values);
		return getList(query);
	}

	protected List findPage(String qlString, int page, int maxSize,
			Object... values) {
		Query query = createQuery(qlString, values);
		query.setFirstResult((page - 1) * maxSize);
		query.setMaxResults(maxSize);
		return getList(query);
	}

	protected List getList(Query query) {
		List list = query.getResultList();
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			it.next();
		}
		return list;
	}

	protected String buildQueryString(String alias) {
		String qlString = "select " + alias + " from "
				+ getEntityClass().getSimpleName() + " " + alias;
		return qlString;
	}

	protected List listByCQ(CriteriaQuery criteriaQuery) {
		TypedQuery<T> typedQuery = getEntityManager()
				.createQuery(criteriaQuery);
		return getList(typedQuery);
	}

	protected List listByCQ(CriteriaQuery criteriaQuery, int first, int max) {
		criteriaQuery.select(criteriaQuery.from(getEntityClass()));
		TypedQuery<T> typedQuery = getEntityManager()
				.createQuery(criteriaQuery);
		typedQuery.setFirstResult(first);
		typedQuery.setMaxResults(max);
		return getList(typedQuery);
	}

	protected Object uniqueByCQ(CriteriaQuery criteriaQuery) {
		TypedQuery<T> typedQuery = getEntityManager()
				.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();
	}

	protected Long countByCQ(CriteriaQuery criteriaQuery) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		criteriaQuery.select(builder.count(criteriaQuery.from(getEntityClass())));
		return (Long) uniqueByCQ(criteriaQuery);
	}

	protected Page<T> findByPage(CriteriaQuery criteriaQuery, int pageIndex,
			int pageSize) {
		try {
			Long count = countByCQ(criteriaQuery);
			List<T> list = listByCQ(criteriaQuery, pageIndex * pageSize,
					pageSize);
			return new Page<T>(pageIndex, count, pageSize, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
