package pers.ksy.common.orm;

public class Alias {
	private String associationPath;
	private String alias;
	private JoinType joinType;

	public Alias(String associationPath, String alias, JoinType joinType) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
		this.joinType = joinType;
	}

	public String toQueryString(QueryCondition queryCondition) {
		return ' ' + joinType.getJoinTypeValue() + ' '
				+ queryCondition.getAlias() + '.' + associationPath + " as "
				+ alias + ' ';
	}

	public String getAssociationPath() {
		return associationPath;
	}

	public String getAlias() {
		return alias;
	}

	public JoinType getJoinType() {
		return joinType;
	}

}
