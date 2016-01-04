package pers.ksy.common.orm;

public interface Condition {
	String getPropertyName();

	Object getValue();

	ConditionType getConditionType();

	String toQueryString(QueryCondition queryCondition);
}
