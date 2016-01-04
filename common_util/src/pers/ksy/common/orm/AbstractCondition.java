package pers.ksy.common.orm;

public abstract class AbstractCondition implements Condition {
	protected final String propertyName;
	protected final ConditionType conditionType;

	public AbstractCondition(String propertyName, ConditionType conditionType) {
		super();
		this.propertyName = propertyName;
		this.conditionType = conditionType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public ConditionType getConditionType() {
		return conditionType;
	}
}
