package pers.ksy.common.orm;

public enum ConditionType {
	EQ("="), NE("<>"), LIKE("like"), GT(">"), LT("<"), LE("<="), GE(">="), BETWEEN(
			"between"), IN("in"), LOGICAL(null);
	public final String op;

	ConditionType(String op) {
		this.op = op;
	}

}