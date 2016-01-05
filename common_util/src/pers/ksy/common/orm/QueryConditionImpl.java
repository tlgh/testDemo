package pers.ksy.common.orm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import pers.ksy.common.orm.IsCondition.Type;

public class QueryConditionImpl implements QueryCondition {
	private String alias;
	private Class<?> entityClass;
	private final List<ConditionEntry> conditionEntries = new ArrayList<ConditionEntry>();
	private final List<OrderEntry> orderEntries = new ArrayList<OrderEntry>();
	private final List<Alias> aliases = new ArrayList<Alias>();

	private Integer maxResults;
	private Integer firstResult;

	private int parameterIndex = 0;

	public QueryConditionImpl(Class<?> entityClass, String alias) {
		this.entityClass = entityClass;
		this.alias = (alias == null ? "_this" : alias);
	}

	// impl QueryCondition
	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public QueryCondition add(Condition condition) {
		add(this, condition);
		return this;
	}

	@Override
	public QueryCondition addOrder(Order order) {
		addOrder(this, order);
		return null;
	}

	@Override
	public QueryCondition createAlias(String associationPath, String alias, JoinType joinType) {
		aliases.add(new Alias(associationPath, alias, JoinType.LEFT_OUTER_JOIN));
		return this;
	}

	@Override
	public QueryCondition setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	@Override
	public QueryCondition setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	// state
	public void add(QueryCondition queryCondition, Condition condition) {
		conditionEntries.add(new ConditionEntry(queryCondition, condition));
	}

	public void addOrder(QueryCondition queryCondition, Order order) {
		orderEntries.add(new OrderEntry(queryCondition, order));
	}

	public Query createQuery(EntityManager entityManager) {
		parameterIndex = 0;
		Query query = entityManager.createQuery(buildQueryString());
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query;
	}

	// EQ, NE, LIKE, GT, LT, LE, GE, BETWEEN, IN, LOGICAL
	private void setParameter(Query query, Condition condition) {
		switch (condition.getConditionType()) {
		case EQ:
		case NE:
		case LIKE:
		case GT:
		case LT:
		case LE:
		case GE:
		case IN:
			setParameter(query, condition.getValue());
			break;
		case BETWEEN:
			BetweenCondition bc = (BetweenCondition) condition;
			setParameter(query, bc.lv);
			setParameter(query, bc.hv);
			break;
		case LOGICAL:
			ConjunctionCondition cc = (ConjunctionCondition) condition;
			for (Condition c : cc.getConditions()) {
				setParameter(query, c);
			}
			break;
		default:
			throw new RuntimeException("unknown ConditionType");
		}
	}

	private void setParameter(Query query, Object value) {
		query.setParameter(parameterIndex++, value);
	}

	private String buildQueryString() {
		String qlString = "select " + getAlias() + " from " + entityClass.getSimpleName() + ' ' + getAlias() + ' ';
		for (Alias alias : aliases) {
			qlString += alias.toQueryString(this);
		}
		if (conditionEntries.size() > 0) {
			qlString += " where ";
			for (ConditionEntry ce : conditionEntries) {
				qlString += ce.condition.toQueryString(ce.queryCondition);
			}
		}
		if (orderEntries.size() > 0) {
			qlString += " order ";
			for (OrderEntry oe : orderEntries) {
				qlString += oe.order.toQueryString(oe.queryCondition);
			}
		}
		return qlString;
	}

	// getter and setter
	public Class<?> getEntityClass() {
		return entityClass;
	}

	public List<ConditionEntry> getConditionEntries() {
		return conditionEntries;
	}

	public List<OrderEntry> getOrderEntries() {
		return orderEntries;
	}

	public List<Alias> getAliases() {
		return aliases;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public static final class ConditionEntry implements Serializable {
		private final QueryCondition queryCondition;
		private final Condition condition;

		public ConditionEntry(QueryCondition queryCondition, Condition condition) {
			super();
			this.queryCondition = queryCondition;
			this.condition = condition;
		}

		public QueryCondition getQueryCondition() {
			return queryCondition;
		}

		public Condition getCondition() {
			return condition;
		}

		@Override
		public String toString() {
			return condition.toString();
		}
	}

	public static final class OrderEntry implements Serializable {
		private final QueryCondition queryCondition;
		private final Order order;

		public OrderEntry(QueryCondition queryCondition, Order order) {
			super();
			this.queryCondition = queryCondition;
			this.order = order;
		}

		public Order getOrder() {
			return order;
		}

		public QueryCondition getQueryCondition() {
			return queryCondition;
		}

		@Override
		public String toString() {
			return order.toString();
		}
	}

	@Override
	public QueryCondition eq(String propertyName, Object value) {
		return add(Conditions.eq(propertyName, value));
	}

	@Override
	public QueryCondition like(String propertyName, Object value, MatchMode matchMode) {
		return add(Conditions.like(propertyName, value, matchMode));
	}

	@Override
	public QueryCondition ne(String propertyName, Object value) {
		return add(Conditions.ne(propertyName, value));
	}

	@Override
	public QueryCondition gt(String propertyName, Object value) {
		return add(Conditions.gt(propertyName, value));
	}

	@Override
	public QueryCondition lt(String propertyName, Object value) {
		return add(Conditions.lt(propertyName, value));
	}

	@Override
	public QueryCondition ge(String propertyName, Object value) {
		return add(Conditions.ge(propertyName, value));
	}

	@Override
	public QueryCondition le(String propertyName, Object value) {
		return add(Conditions.le(propertyName, value));
	}

	@Override
	public QueryCondition between(String propertyName, Object lv, Object hv) {
		return add(Conditions.between(propertyName, lv, hv));
	}

	@Override
	public QueryCondition in(String propertyName, Object... value) {
		return add(Conditions.in(propertyName, value));
	}

	@Override
	public QueryCondition notIn(String propertyName, Object... value) {
		return add(Conditions.notIn(propertyName, value));
	}

	@Override
	public QueryCondition or(Condition leftCondition, Condition rightCondition) {
		return add(Conditions.or(leftCondition, rightCondition));
	}

	@Override
	public QueryCondition or(Condition... conditions) {
		return add(Conditions.or(conditions));
	}

	@Override
	public QueryCondition and(Condition leftCondition, Condition rightCondition) {
		return add(Conditions.and(leftCondition, rightCondition));
	}

	@Override
	public QueryCondition and(Condition... conditions) {
		return add(Conditions.and(conditions));
	}

	@Override
	public QueryCondition is(String propertyName, Type type) {
		return add(Conditions.is(propertyName, type));
	}

}
