package pers.ksy.common.orm.jpa;

public class JpaHelper {
	public static void initialize(Object proxy) {
		if (proxy == null) {
			return;
		}
		proxy.toString();
	}
}
