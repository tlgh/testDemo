package pers.ksy.common.orm;

public interface QueryCondition {
	public String getAlias();

	public QueryCondition add(Condition condition);

	public QueryCondition addOrder(Order order);

	public QueryCondition createAlias(String associationPath, String alias,
			JoinType joinType);

	public QueryCondition setMaxResults(int maxResults);

	public QueryCondition setFirstResult(int firstResult);
}
