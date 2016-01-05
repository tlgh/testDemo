package pers.ksy.common.orm;

public class IsCondition extends SimpleCondition implements Condition {
	private Type type;

	public IsCondition(String propertyName, Type type) {
		super(propertyName, null, ConditionType.IS);
		this.type = type;
	}

	@Override
	public String toQueryString(QueryCondition queryCondition) {
		return ' ' + getPropertyName() + " IS " + type + ' ';
	}

	public Type geType() {
		return type;
	}

	public enum Type {
		NULL, EMPTY, NOT_NULL, NOT_EMPTY;
		public String toString() {
			String str = super.toString();
			str = str.replace('_', ' ').toUpperCase();
			return str;
		};
	}

}
