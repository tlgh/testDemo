package pers.ksy.common.orm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

public class ConjunctionCondition extends AbstractCondition implements
		Condition {
	private final List<Condition> conditions = new ArrayList<Condition>();
	private final Nature nature;

	public ConjunctionCondition(Nature nature, Condition... conditions) {
		super(null, ConditionType.LOGICAL);
		this.nature = nature;
		Collections.addAll(this.conditions, conditions);
	}

	public Object getValue() {
		return null;
	}

	@Override
	public String toQueryString(QueryCondition queryCondition) {
		// TODO:
		Assert.notEmpty(conditions);
		String op = nature.getOperator();
		String qlString = "";
		for (Condition condition : conditions) {
			qlString += formatQueryString(queryCondition, condition) + op;
		}
		qlString.substring(0, qlString.length() - op.length());
		return qlString;
	}

	private String formatQueryString(QueryCondition queryCondition,
			Condition condition) {
		String qlString = " " + condition.toQueryString(queryCondition) + " ";
		if (condition.getConditionType() == ConditionType.LOGICAL) {
			qlString = " (" + qlString + ") ";
		}
		return qlString;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public Nature getNature() {
		return nature;
	}

}
