package pers.ksy.common.orm;

/**
 * The type of junction
 */
public enum Nature {
	/**
	 * An AND
	 */
	AND,
	/**
	 * An OR
	 */
	OR;

	/**
	 * The corresponding SQL operator
	 *
	 * @return SQL operator
	 */
	public String getOperator() {
		return name().toLowerCase();
	}
}