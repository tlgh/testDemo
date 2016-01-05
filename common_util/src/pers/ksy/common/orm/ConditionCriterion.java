package pers.ksy.common.orm;

public interface ConditionCriterion {
	public QueryCondition eq(String propertyName, Object value);

	public QueryCondition like(String propertyName, Object value, MatchMode matchMode);

	public QueryCondition ne(String propertyName, Object value);

	public QueryCondition gt(String propertyName, Object value);

	public QueryCondition lt(String propertyName, Object value);

	public QueryCondition ge(String propertyName, Object value);

	public QueryCondition le(String propertyName, Object value);

	public QueryCondition between(String propertyName, Object lv, Object hv);

	public QueryCondition in(String propertyName, Object... value);

	public QueryCondition notIn(String propertyName, Object... value);

	public QueryCondition or(Condition leftCondition, Condition rightCondition);

	public QueryCondition or(Condition... conditions);

	public QueryCondition and(Condition leftCondition, Condition rightCondition);

	public QueryCondition and(Condition... conditions);

	public QueryCondition is(String propertyName, IsCondition.Type type);
}
