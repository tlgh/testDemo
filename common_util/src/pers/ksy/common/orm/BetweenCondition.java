package pers.ksy.common.orm;

public class BetweenCondition extends AbstractCondition implements Condition {
	protected final Object lv;
	protected final Object hv;

	public BetweenCondition(String propertyName, Object lv, Object hv) {
		super(propertyName, ConditionType.BETWEEN);
		this.lv = lv;
		this.hv = hv;
	}

	@Override
	public Object getValue() {
		return new Object[] { lv, hv };
	}

	public Object getLv() {
		return lv;
	}

	public Object getHv() {
		return hv;
	}

	@Override
	public String toQueryString(QueryCondition queryCondition) {
		// TODO:
		return " " + getPropertyName() + " " + getConditionType().op
				+ " ? and ?  ";
	}

}
