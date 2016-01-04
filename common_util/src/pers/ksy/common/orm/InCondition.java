package pers.ksy.common.orm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InCondition extends AbstractCondition implements Condition {
	protected final List<Object> values = new ArrayList<Object>();
	private final boolean not;

	public InCondition(String propertyName, boolean not, Object... values) {
		super(propertyName, ConditionType.IN);
		this.not = not;
		Collections.addAll(this.values, values);
	}

	@Override
	public Object getValue() {
		return values.toArray();
	}

	public List<Object> getValues() {
		return values;
	}

	public boolean isNot() {
		return not;
	}

	@Override
	public String toQueryString(QueryCondition queryCondition) {
		// TODO:
		return " " + getPropertyName() + " " + getConditionType().op
				+ " ? and ?  ";
	}

}
