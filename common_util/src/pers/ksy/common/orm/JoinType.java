package pers.ksy.common.orm;

public enum JoinType {
	NONE(null), INNER_JOIN("inner join"), LEFT_OUTER_JOIN("left outer join"), RIGHT_OUTER_JOIN(
			"right outer join"), FULL_JOIN("full join");
	private String joinTypeValue;

	JoinType(String joinTypeValue) {
		this.joinTypeValue = joinTypeValue;
	}

	public String getJoinTypeValue() {
		return joinTypeValue;
	}
}