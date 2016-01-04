package pers.ksy.common.orm;

public class SimpleCondition extends AbstractCondition implements Condition {
	protected final Object value;

	public SimpleCondition(String propertyName, Object value,
			ConditionType restriction) {
		super(propertyName, restriction);
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String toQueryString(QueryCondition queryCondition) {
		// TODO
		return getPropertyName() + " " + getConditionType().op + " ? ";
	}

}
