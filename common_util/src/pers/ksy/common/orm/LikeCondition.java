package pers.ksy.common.orm;

public class LikeCondition extends SimpleCondition implements Condition {
	private MatchMode matchMode;

	public LikeCondition(String propertyName, Object value, MatchMode matchMode) {
		super(propertyName, value, ConditionType.LIKE);
		this.matchMode = matchMode;
	}

	@Override
	public Object getValue() {
		return matchMode.toMatchString(value.toString());
	}

	public MatchMode getMatchMode() {
		return matchMode;
	}

}
