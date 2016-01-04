package pers.ksy.common.orm;

import java.io.Serializable;

public class Order implements Serializable {
	private boolean ascending;
	private String propertyName;

	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}

	protected Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public String toQueryString(QueryCondition queryCondition) {
		return ' ' + queryCondition.getAlias() + '.' + propertyName + ' '
				+ (ascending ? "asc" : "desc") + ' ';
	}

	@Override
	public String toString() {
		return propertyName + ' ' + (ascending ? "asc" : "desc");
	}
}
