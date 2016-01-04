package pers.ksy.common.orm;

public class LogicalCondition extends ConjunctionCondition implements Condition {
	protected final Condition leftCondition;
	protected final Condition rightCondition;

	public LogicalCondition(Condition leftCondition, Condition rightCondition,
			Nature nature) {
		super(nature, leftCondition, rightCondition);
		this.leftCondition = leftCondition;
		this.rightCondition = rightCondition;
	}

	@Override
	public Object getValue() {
		return null;
	}

	public Condition getLeftCondition() {
		return leftCondition;
	}

	public Condition getRightCondition() {
		return rightCondition;
	}

}
