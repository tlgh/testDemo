package pers.ksy.common.orm;

public final class Conditions {

	public static Condition eq(String propertyName, Object value) {
		return new SimpleCondition(propertyName, value, ConditionType.EQ);
	}

	public static Condition like(String propertyName, Object value,
			MatchMode matchMode) {
		return new LikeCondition(propertyName, value, matchMode);
	}

	public static Condition ne(String propertyName, Object value) {
		return new SimpleCondition(propertyName, value, ConditionType.NE);
	}

	public static Condition gt(String propertyName, Object value) {
		return new SimpleCondition(propertyName, value, ConditionType.GT);
	}

	public static Condition lt(String propertyName, Object value) {
		return new SimpleCondition(propertyName, value, ConditionType.LT);
	}

	public static Condition ge(String propertyName, Object value) {
		return new SimpleCondition(propertyName, value, ConditionType.GE);
	}

	public static Condition le(String propertyName, Object value) {
		return new SimpleCondition(propertyName, value, ConditionType.LE);
	}

	public static Condition between(String propertyName, Object lv, Object hv) {
		return new BetweenCondition(propertyName, lv, hv);
	}

	public static Condition in(String propertyName, Object... value) {
		return new InCondition(propertyName, false, value);
	}

	public static Condition notIn(String propertyName, Object... value) {
		return new InCondition(propertyName, true, value);
	}

	public static Condition or(Condition leftCondition, Condition rightCondition) {
		return new LogicalCondition(leftCondition, rightCondition, Nature.OR);
	}

	public static Condition or(Condition... conditions) {
		return new ConjunctionCondition(Nature.OR, conditions);
	}

	public static Condition and(Condition leftCondition,
			Condition rightCondition) {
		return new LogicalCondition(leftCondition, rightCondition, Nature.AND);
	}

	public static Condition and(Condition... conditions) {
		return new ConjunctionCondition(Nature.AND, conditions);
	}
	
	public static Condition is(String propertyName, IsCondition.Type type) {
		return new IsCondition(propertyName, type);
	}
	

}
