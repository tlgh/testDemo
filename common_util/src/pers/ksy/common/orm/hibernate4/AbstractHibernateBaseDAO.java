package pers.ksy.common.orm.hibernate4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Junction.Nature;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.util.Assert;

import pers.ksy.common.model.Page;
import pers.ksy.common.orm.Alias;
import pers.ksy.common.orm.BetweenCondition;
import pers.ksy.common.orm.Condition;
import pers.ksy.common.orm.ConjunctionCondition;
import pers.ksy.common.orm.InCondition;
import pers.ksy.common.orm.IsCondition;
import pers.ksy.common.orm.LikeCondition;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.QueryConditionImpl;
import pers.ksy.common.orm.QueryConditionImpl.ConditionEntry;
import pers.ksy.common.orm.QueryConditionImpl.OrderEntry;

/**
 * 
 * @author ksy
 *
 * @param <T>
 *            entity
 * @param <ID>
 *            primary key name
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractHibernateBaseDAO<T, ID extends Serializable> extends HibernateSimpleDAO
		implements HibernateBaseDAO<T, ID> {

	// state
	protected DetachedCriteria buildDetachedCriteria(QueryCondition queryCondition) {
		QueryConditionImpl qcImpl = (QueryConditionImpl) queryCondition;
		DetachedCriteria dc = getDC();
		qcImpl.getConditionEntries();

		for (Alias alias : qcImpl.getAliases()) {
			if (alias.getJoinType() == null) {
				dc.createAlias(alias.getAlias(), alias.getAssociationPath());
			} else {
				dc.createAlias(alias.getAlias(), alias.getAssociationPath(), translateJoinType(alias.getJoinType()));
			}
		}

		for (ConditionEntry ce : qcImpl.getConditionEntries()) {
			dc.add(translateCondition(ce.getCondition()));
		}

		for (OrderEntry oe : qcImpl.getOrderEntries()) {
			dc.addOrder(translateOrder(oe.getOrder()));
		}
		return dc;
	}
	
	private JoinType translateJoinType(pers.ksy.common.orm.JoinType joinType) {
		return null;
	}
	
	private Criterion translateCondition(Condition condition) {
		Criterion criterion = null;
		switch (condition.getConditionType()) {
		case EQ:
			criterion = Restrictions.eq(condition.getPropertyName(), condition.getValue());
			break;
		case NE:
			criterion = Restrictions.ne(condition.getPropertyName(), condition.getValue());
			break;
		case LIKE:
			LikeCondition likeCondition = (LikeCondition) condition;
			criterion = Restrictions.like(likeCondition.getPropertyName(), likeCondition.getValue().toString(),
					MatchMode.valueOf(likeCondition.getMatchMode().toString()));
			break;
		case GT:
			criterion = Restrictions.gt(condition.getPropertyName(), condition.getValue());
			break;
		case LT:
			criterion = Restrictions.lt(condition.getPropertyName(), condition.getValue());
			break;
		case LE:
			criterion = Restrictions.le(condition.getPropertyName(), condition.getValue());
			break;
		case GE:
			criterion = Restrictions.ge(condition.getPropertyName(), condition.getValue());
			break;
		case IN:
			criterion = Restrictions.in(condition.getPropertyName(), (Object[]) condition.getValue());
			break;
		case IS:
			IsCondition isCondition = (IsCondition) condition;
			switch (isCondition.geType()) {
			case NULL:
				criterion = Restrictions.isNull(condition.getPropertyName());
				break;
			case EMPTY:
				criterion = Restrictions.isEmpty(condition.getPropertyName());
				break;
			case NOT_NULL:
				criterion = Restrictions.isNotNull(condition.getPropertyName());
				break;
			case NOT_EMPTY:
				criterion = Restrictions.isNotEmpty(condition.getPropertyName());
				break;
			}
			break;
		case BETWEEN:
			BetweenCondition betweenCondition = (BetweenCondition) condition;
			criterion = Restrictions.between(condition.getPropertyName(), betweenCondition.getLv(),
					betweenCondition.getHv());
			break;
		case LOGICAL:
			ConjunctionCondition cc = (ConjunctionCondition) condition;
			Criterion[] criterions = new Criterion[cc.getConditions().size()];
			for (int i = 0; i < cc.getConditions().size(); i++) {
				criterions[i] = translateCondition(cc.getConditions().get(i));
			}
			if (cc.getNature() == pers.ksy.common.orm.Nature.OR) {
				criterion = Restrictions.disjunction(criterions);
			} else {
				criterion = Restrictions.conjunction(criterions);
			}
			break;
		default:
			throw new RuntimeException("unknown ConditionType");
		}
		return criterion;
	}
	
	private Order translateOrder(pers.ksy.common.orm.Order order) {
		return order.isAscending() ? Order.asc(order.getPropertyName()) : Order.desc(order.getPropertyName());
	}

	/**
	 * 获取当前DAO对应的entity的class类
	 * 
	 * @return
	 */
	abstract protected Class<T> getEntityClass();

	/**
	 * 获取当前DAO对应entity的Id字段名
	 * 
	 * @return
	 */
	protected String getIdName() {
		return getSessionFactory().getClassMetadata(getEntityClass()).getIdentifierPropertyName();
	}

	/**
	 * 获取当前DAO对应的entity的Id
	 * 
	 * @param entity
	 * @return
	 */
	protected Serializable getIdFromEntity(T entity) {
		return getSession().getIdentifier(entity);
	}

	protected long countByProp(String prop, Object value) {
		String hql = "select count(*) from " + getEntityClass().getSimpleName() + " e where e." + prop + " = ?";
		Query query = createQuery(hql, value);
		Long count = (Long) query.uniqueResult();
		return count;
	}

	@Override
	public T get(ID id) {
		Assert.notNull(id);
		return get(id, false);
	}

	@Override
	public T get(ID id, boolean lock) {
		Assert.notNull(id);
		T entity;
		if (lock) {
			entity = (T) getSession().get(getEntityClass(), id, LockMode.UPGRADE);
		} else {
			entity = (T) getSession().get(getEntityClass(), id);
		}
		return entity;
	}

	@Override
	public T load(ID id, boolean lock) {
		Assert.notNull(id);
		T entity = null;
		if (lock) {
			entity = (T) getSession().load(getEntityClass(), id, LockMode.UPGRADE);
		} else {
			entity = (T) getSession().load(getEntityClass(), id);
		}
		return entity;
	}

	@Override
	public T load(ID id) {
		Assert.notNull(id);
		return load(id, false);
	}

	@Override
	public T save(T t) {
		Assert.notNull(t);
		getSession().save(t);
		return t;
	}

	@Override
	public Serializable saveAny(Serializable t) {
		Assert.notNull(t);
		return getSession().save(t);
	}

	@Override
	public T update(T entity) {
		Assert.notNull(entity);
		getSession().update(entity);
		return entity;
	}

	@Override
	public T saveOrUpdate(T entity) {
		Assert.notNull(entity);
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public T delete(T entity) {
		Assert.notNull(entity);
		getSession().delete(entity);
		return entity;
	}

	@Override
	public T deleteById(ID id) {
		Assert.notNull(id);
		T entity = get(id, true);
		getSession().delete(entity);
		return entity;
	}

	@Override
	public boolean deleteByIdUseHql(ID id) {
		String hql = "delete " + getEntityClass().getSimpleName() + " e where e." + getIdName() + " = ?";
		getSession().createQuery(hql).setParameter(0, id).executeUpdate();
		return true;
	}

	/**
	 * 刷新对象
	 * 
	 * @param entity
	 */
	@Override
	public void refresh(Object entity) {
		getSession().refresh(entity);
	}

	@Override
	public Page<T> findByPage(int pageIndex, int pageSize, pers.ksy.common.orm.Order[] orders) {
		return findByPage(pageIndex, pageSize, null, null, orders, false);
	}

	/**
	 * 分页查询
	 * 
	 * @param pageIndex
	 *            当前页(0~n)
	 * @param pageSize
	 *            页面大小(每页最大记录数)
	 * @param isCacheable
	 *            是否启用缓存
	 * @param eqConditions
	 *            equals查询条件(key:hibernate entity 属性名,value 属性值)
	 * @param likeConditions
	 *            like查询条件(key:hibernate entity 属性名,value 属性值)
	 * @param orders
	 *            排序字段
	 * @return 页面查询结果封装对象
	 */
	@Override
	public Page<T> findByPage(int pageIndex, int pageSize, Map<String, Object> eqConditions,
			Map<String, String> likeConditions, pers.ksy.common.orm.Order[] orders, boolean isCacheable) {
		DetachedCriteria dc = getDC();
		if (null != eqConditions) {
			for (String key : eqConditions.keySet()) {
				dc.add(Restrictions.eq(key, eqConditions.get(key)));
			}
		}
		if (null != likeConditions) {
			for (String key : likeConditions.keySet()) {
				dc.add(Restrictions.like(key, likeConditions.get(key), MatchMode.ANYWHERE));
			}
		}
		if (null != orders) {
			for (pers.ksy.common.orm.Order order : orders) {
				dc.addOrder(translateOrder(order));
			}
		}
		return findByPage(dc, pageIndex, pageSize, isCacheable);
	}

	@Override
	public Page<T> findByPage(DetachedCriteria dc, int pageIndex, int pageSize, boolean isCacheable) {
		try {
			Long count = super.countByDC(dc);
			dc.setProjection(null);
			dc.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<T> list = listByDC(dc, pageIndex * pageSize, pageSize, isCacheable);
			return new Page<T>(pageIndex, count, pageSize, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前DAO对应entity的DetachedCriteria
	 * 
	 * @return
	 */
	@Override
	public DetachedCriteria getDC() {
		return DetachedCriteria.forClass(getEntityClass());
	}

	@Override
	public List<T> findAll() {
		return super.listByDC(getDC(), false);
	}
	
	@Override
	public List<T> findAll(String[] joins) {
		return findByProperty(new String[] {}, new Object[] {}, joins);
	}

	@Override
	public List<T> listByDC(DetachedCriteria dc) {
		return super.listByDC(dc, false);
	}

	@Override
	public List<T> listByDC(DetachedCriteria dc, String[] sels) {
		String alias = getEntityClass().getSimpleName();
		dc.createCriteria(alias);
		ProjectionList pList = Projections.projectionList();
		if (null != sels) {
			for (int i = 0; i < sels.length; i++) {
				pList.add(Projections.property(alias + "." + sels[i]).as(sels[i]));
			}
		}
		dc.setProjection(pList);
		dc.setResultTransformer(Transformers.aliasToBean(getEntityClass()));
		return listByDC(dc);
	}

	@Override
	public Long countByDC(DetachedCriteria dc) {
		return super.countByDC(dc);
	}

	@Override
	public Long countByProperties(String[] propNames, Object[] propVals) {
		DetachedCriteria dc = buildFindByPropertyDC(propNames, propVals, null);
		return super.countByDC(dc);
	}

	@Override
	public List<T> findByProperty(String propName, Object propVal) {
		return findByProperty(propName, propVal, null);
	}

	@Override
	public List<T> findByProperty(String propName, Object propVal, String[] joins) {
		return findByProperty(new String[] { propName }, new Object[] { propVal }, joins);
	}

	@Override
	public List<T> findByProperty(String[] propNames, Object[] propVals, String[] joins) {
		DetachedCriteria dc = buildFindByPropertyDC(propNames, propVals, joins);
		return listByDC(dc);
	}

	@Override
	public T getByProperty(String propName, Object propVal) {
		return getByProperty(propName, propVal, null);
	}

	@Override
	public T getByProperty(String propName, Object propVal, String[] joins) {
		DetachedCriteria dc = buildFindByPropertyDC(new String[] { propName }, new Object[] { propVal }, joins);
		return (T) uniqueByDC(dc);
	}

	protected DetachedCriteria buildFindByPropertyDC(String[] propNames, Object[] propVals, String[] joins) {
		DetachedCriteria dc = getDC();
		if (null != propNames && null != propVals && propNames.length == propVals.length) {
			for (int i = 0; i < propNames.length; i++) {
				dc.add(Restrictions.eq(propNames[i], propVals[i]));
			}
		}
		if (null != joins) {
			for (int i = 0; i < joins.length; i++) {
				dc.createAlias(joins[i], joins[i], JoinType.LEFT_OUTER_JOIN);
			}
		}
		return dc;
	}

	@Override
	public int updatePropertiesById(Map<String, Object> updateSet, ID id) {
		Map updateWhere = new HashMap();
		updateWhere.put(getIdName(), id);
		return updateProperties(updateSet, updateWhere);
	}

	@Override
	public int updateProperties(Map<String, Object> updateSet, Map<String, Object> updateWhere) {
		try {
			List<Object> list = new ArrayList();
			String entityName = getEntityClass().getName();
			String hql = "update " + entityName + " e ";
			String setHql = "set ";
			for (String setKey : updateSet.keySet()) {
				setHql += "e." + setKey + "=?,";
				list.add(updateSet.get(setKey));
			}
			setHql = setHql.substring(0, setHql.length() - 1);
			String whereHql = "";
			if (null != updateWhere) {
				whereHql = " where ";
				for (String whereKey : updateWhere.keySet()) {
					whereHql += " e." + whereKey + "=? and";
					list.add(updateWhere.get(whereKey));
				}
				whereHql = whereHql.substring(0, whereHql.length() - 3);
			}
			Query query = createQuery(hql + setHql + whereHql, list.toArray());
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Page<T> findByPage(int pageIndex, int pageSize, Map<String, Object> eqConditions,
			Map<String, String> likeConditions, pers.ksy.common.orm.Order[] orders) {
		return findByPage(pageIndex, pageSize, eqConditions, likeConditions, orders, false);
	}

	@Override
	public Page<T> findByPage(QueryCondition qc, int pageIndex, int pageSize) {
		DetachedCriteria dc = buildDetachedCriteria(qc);
		return findByPage(dc, pageIndex, pageSize, false);
	}

	@Override
	public Page<T> findByPage(QueryCondition qc, int pageIndex, int pageSize, boolean isCacheable) {
		DetachedCriteria dc = buildDetachedCriteria(qc);
		return findByPage(dc, pageIndex, pageSize, isCacheable);
	}

	@Override
	public QueryCondition getQC() {
		return new QueryConditionImpl(getEntityClass(), null);
	}

	@Override
	public List listByQC(QueryCondition qc) {
		return listByDC(buildDetachedCriteria(qc));
	}

	@Override
	public List listByQC(QueryCondition qc, int first, int max) {
		DetachedCriteria dc = buildDetachedCriteria(qc);
		return listByDC(dc, first, max, false);
	}

	@Override
	public T uniqueByQC(QueryCondition qc) {
		return (T)uniqueByDC(buildDetachedCriteria(qc));
	}

	@Override
	public Long countByQC(QueryCondition qc) {
		return countByDC(buildDetachedCriteria(qc));
	}

	@Override
	public void deleteAll() {
		String hql = "DELETE FROM " + getEntityClass().getSimpleName();
		createQuery(hql).executeUpdate();
	}

}
