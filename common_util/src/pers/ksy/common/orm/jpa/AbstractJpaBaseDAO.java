package pers.ksy.common.orm.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pers.ksy.common.model.Page;
import pers.ksy.common.orm.Alias;
import pers.ksy.common.orm.BetweenCondition;
import pers.ksy.common.orm.Condition;
import pers.ksy.common.orm.ConjunctionCondition;
import pers.ksy.common.orm.InCondition;
import pers.ksy.common.orm.Nature;
import pers.ksy.common.orm.Order;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.QueryConditionImpl;
import pers.ksy.common.orm.QueryConditionImpl.ConditionEntry;
import pers.ksy.common.orm.QueryConditionImpl.OrderEntry;

/**
 * 
 * @author ksy
 *
 * @param <T> entity
 * @param <ID> primary key
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractJpaBaseDAO<T, ID extends Serializable> extends
		JpaSimpleDAO<T> implements JpaBaseDAO<T, ID> {

	//state
	
	protected CriteriaQuery<T> buildCriteriaQuery(QueryCondition queryCondition) {
		QueryConditionImpl qcImpl = (QueryConditionImpl) queryCondition;
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());
		Root<T> root = criteriaQuery.from(getEntityClass());
		qcImpl.getConditionEntries();
		Map<String, String> aliasMap = new HashMap<String, String>();
		
		for (Alias alias : qcImpl.getAliases()) {
			aliasMap.put(alias.getAlias(), alias.getAssociationPath());
			// TODO
		}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (ConditionEntry ce : qcImpl.getConditionEntries()) {
			Predicate predicate = buildPredicate(criteriaBuilder, root,
					aliasMap, ce.getCondition());
			predicates.add(predicate);
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		for (OrderEntry oe : qcImpl.getOrderEntries()) {
			Path path = getPath(root, oe.getOrder().getPropertyName());
			criteriaQuery.orderBy(oe.getOrder().isAscending() ? criteriaBuilder
					.asc(path) : criteriaBuilder.desc(path));
		}
		
		return criteriaQuery;
	}
	
	private Predicate buildPredicate(CriteriaBuilder criteriaBuilder,
			Path root,Map<String,String> aliasMap, Condition condition) {
		Path path = getPath(root, condition.getPropertyName());
		Predicate predicate;
		switch (condition.getConditionType()) {
		case EQ:
			predicate = criteriaBuilder.equal(path, condition.getValue());
			break;
		case NE:
			predicate = criteriaBuilder.notEqual(path, condition.getValue());
			break;
		case LIKE:
			predicate = criteriaBuilder.like(path.as(String.class), (String)condition.getValue());
			break;
		case GT:
			predicate = criteriaBuilder.gt(path, toNumberValue(condition.getValue()));
			break;
		case LT:
			predicate = criteriaBuilder.lt(path, toNumberValue(condition.getValue()));
			break;
		case LE:
			predicate = criteriaBuilder.le(path, toNumberValue(condition.getValue()));
			break;
		case GE:
			predicate = criteriaBuilder.ge(path, toNumberValue(condition.getValue()));
			break;
		case IN:
			InCondition inCondition = (InCondition) condition;
			List<Object> values = inCondition.getValues();
			In in = criteriaBuilder.in(path);
			for(Object value:values) {
				in.value(value);
			}
			predicate = in;
			if(inCondition.isNot()){
				predicate = criteriaBuilder.not(in);
			}
			break;
		case BETWEEN:
			BetweenCondition betweenCondition = (BetweenCondition) condition;
			predicate = criteriaBuilder.and(criteriaBuilder.ge(path, toNumberValue(betweenCondition.getLv())), 
					criteriaBuilder.ge(path, toNumberValue(betweenCondition.getHv())));
			break;
		case LOGICAL:
			ConjunctionCondition cc = (ConjunctionCondition) condition;
			Predicate[] restrictions = new Predicate[cc.getConditions().size()];
			for(int i=0;i<cc.getConditions().size();i++){
				restrictions[i] = buildPredicate(criteriaBuilder, root, aliasMap, cc.getConditions().get(i));
			}
			predicate =  cc.getNature()==Nature.OR ? criteriaBuilder.or(restrictions) : criteriaBuilder.and(restrictions);
			break;
		default:
			throw new RuntimeException("unknown ConditionType");
		}
		return predicate;
	}
	
	
	private Number toNumberValue(Object value) {
		return value instanceof Date ? ((Date) value).getTime()
				: (Number) value;
	}
	
	private Path getPath(Path root, String path) {
		Path p = null;
		if (null != path) {
			int firstIndex = path.indexOf('.');
			if (firstIndex > 0) {
				p = getPath(root.get(path.substring(0, firstIndex)),
						path.substring(firstIndex + 1));
			} else {
				p = root.get(path);
			}
		}
		return p;
	}
	
	
	// impl JpaBaseDAO
	
	/**
	 * 获取当前DAO对应entity的Id字段名
	 * 
	 * @return
	 */
	protected String getIdName() {
		/*EntityType<T> entityType = getEntityManager().getMetamodel().entity(
				getEntityClass());
		return entityType.getIdClassAttributes().iterator().next().getName();
		*/
		return "id";
	}

	/**
	 * 获取当前DAO对应的entity的Id
	 * 
	 * @param entity
	 * @return
	 */
	protected Serializable getIdFromEntity(T entity) {
		try{
			String idName = getIdName();
			Field f = entity.getClass().getDeclaredField(idName);
			f.setAccessible(true);
			return (Serializable) f.get(entity);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	protected long countByProp(String prop, Object value) {
		String qlString = "select count(*) from "
				+ getEntityClass().getSimpleName() + " e where e." + prop
				+ " = ?";
		Query query = createQuery(qlString, value);
		Long count = (Long) query.getSingleResult();
		return count;
	}

	/**
	 * @see Session.get(Class,Serializable)
	 * @param id
	 * @return 持久化对象
	 */
	@Override
	public T get(ID id) {
		return getEntityManager().find(getEntityClass(), id);
	}

	@Override
	public T save(T t) {
		return (T) saveAny((Serializable) t);
	}

	/**
	 * 保存对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	@Override
	public Serializable saveAny(Serializable t) {
		getEntityManager().persist(t);
		return t;
	}

	/**
	 * 更新对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	@Override
	public T update(T entity) {
		return getEntityManager().merge(entity);
	}

	/**
	 * 保存或更新对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	@Override
	public T saveOrUpdate(T entity) {
		ID id = (ID) getIdFromEntity(entity);
		if (null != get(id)) {
			update(entity);
		} else {
			save(entity);
		}
		return entity;
	}

	/**
	 * 删除对象
	 * 
	 * @param entity  实体对象
	 */
	@Override
	public T delete(T entity) {
		getEntityManager().remove(entity);
		return entity;
	}

	/**
	 * 根据ID删除记录
	 * 
	 * @param id 记录ID
	 */
	@Override
	public  T deleteById(ID id) {
		T entity = get(id);
		delete(entity);
		return entity;
	}


	/**
	 * 刷新对象
	 * 
	 * @param entity
	 */
	@Override
	public void refresh(Object entity) {
		getEntityManager().refresh(entity);
	}

	@Override
	public Page<T> findByPage(int pageIndex, int pageSize, Order[] orders) {
		return findByPage(pageIndex, pageSize, null, null, orders);
	}

	/**
	 * 分页查询
	 * 
	 * @param pageIndex 当前页(0~n)
	 * @param pageSize 页面大小(每页最大记录数)
	 * @param eqConditions equals查询条件(key:hibernate entity 属性名,value 属性值)
	 * @param likeConditions like查询条件(key:hibernate entity 属性名,value 属性值)
	 * @param orders 排序字段
	 * @return 页面查询结果封装对象
	 */
	@Override
	public Page<T> findByPage(int pageIndex, int pageSize,
			Map<String, Object> eqConditions,
			Map<String, String> likeConditions, Order[] orders) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());
		Root<T> root = criteriaQuery.from(getEntityClass());
		if (null != eqConditions) {
			for (String key : eqConditions.keySet()) {
				criteriaQuery.where(criteriaBuilder.equal(getPath(root, key),
						eqConditions.get(key)));
			}
		}
		if (null != likeConditions) {
			for (String key : likeConditions.keySet()) {
				criteriaQuery.where(criteriaBuilder.like(getPath(root, key),
						'%' + likeConditions.get(key).toString() + '%'));
			}
		}
		if (null != orders) {
			for (Order order : orders) {
				Path path = getPath(root, order.getPropertyName());
				criteriaQuery.orderBy(order.isAscending() ? criteriaBuilder
						.asc(path) : criteriaBuilder.desc(path));
			}
		}
		return findByPage(criteriaQuery, pageIndex, pageSize);
	}

	@Override
	public Page<T> findByPage(QueryCondition qc, int pageIndex, int pageSize) {
		return findByPage(buildCriteriaQuery(qc), pageIndex, pageSize);
	}

	@Override
	public QueryCondition getQC() {
		return new QueryConditionImpl(getEntityClass(), null);
	}

	@Override
	public List<T> findAll() {
		Query query = createQuery(buildQueryString("t"));
		List<T> list = getList(query);
		return list;
	}

	@Override
	public List<T> findByProperty(String propName, Object propVal) {
		return findByProperty(new String[] { propName },
				new Object[] { propVal }, null);
	}

	@Override
	public List<T> findByProperty(String propName, Object propVal,
			String[] joins) {
		return findByProperty(new String[] { propName },
				new Object[] { propVal }, joins);
	}

	@Override
	public List<T> findByProperty(String[] propNames, Object[] propVals,
			String[] joins) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());
		Root<T> root = criteriaQuery.from(getEntityClass());
		for (int i = 0; i < propNames.length; i++) {
			criteriaQuery.where(criteriaBuilder.equal(getPath(root, propNames[i]),
					propVals[i]));
		}
		if (null != joins) {
			for (int i = 0; i < joins.length; i++) {
				root.fetch(joins[i], JoinType.LEFT);
			}
		}
		return listByCQ(criteriaQuery);
	}

	@Override
	public T getByProperty(String propName, Object propVal) {
		return getByProperty(propName, propVal, null);
	}

	@Override
	public T getByProperty(String propName, Object propVal, String[] joins) {
		List<T> list = findByProperty(new String[] { propName },
				new Object[] { propVal }, joins);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List listByQC(QueryCondition qc) {
		return listByCQ(buildCriteriaQuery(qc));
	}

	@Override
	public List listByQC(QueryCondition qc, int first, int max) {
		return listByCQ(buildCriteriaQuery(qc), first, max);
	}

	@Override
	public Object uniqueByQC(QueryCondition qc) {
		return uniqueByCQ(buildCriteriaQuery(qc));
	}

	@Override
	public Long countByQC(QueryCondition qc) {
		return countByCQ(buildCriteriaQuery(qc));
	}

	@Override
	public Long countByProperties(String[] propNames, Object[] propVals) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());
		Root<T> root = criteriaQuery.from(getEntityClass());
		for (int i = 0; i < propNames.length; i++) {
			criteriaQuery.where(criteriaBuilder.equal(
					getPath(root, propNames[i]), propVals[i]));
		}
		return countByCQ(criteriaQuery);
	}

	@Override
	public int updatePropertiesById(Map<String, Object> updateSet, ID id) {
		Map updateWhere = new HashMap();
		updateWhere.put(getIdName(), id);
		return updateProperties(updateSet, updateWhere);
	}

	@Override
	public int updateProperties(Map<String, Object> updateSet,
			Map<String, Object> updateWhere) {
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
	public void deleteAll(){
		getEntityManager().createQuery("DELETE FROM "+getEntityClass().getSimpleName()).executeUpdate();
	}

}
