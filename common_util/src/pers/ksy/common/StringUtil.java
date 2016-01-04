package pers.ksy.common;

public class StringUtil {
	public static boolean isEmpty(String str) {
		return null == str || "".equals(str);
	}

	public static boolean notEmpty(String str) {
		return !isEmpty(str);
	}

	public static Integer toInt(String str) {
		if (notEmpty(str)) {
			try {
				return Integer.valueOf(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Long toLong(String str) {
		if (notEmpty(str)) {
			try {
				return Long.valueOf(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 判断两个数组中是否含有相同的元素
	 * 
	 * @数组 s1
	 * @数组 s2
	 * @return
	 */
	public static boolean isContentSameKey(String[] s1, String[] s2) {
		java.util.Arrays.sort(s1);
		for (int i = 0; i < s2.length; i++) {
			if (java.util.Arrays.binarySearch(s1, s2[i]) >= 0)
				return true;
		}
		return false;
	}

	// 将string数组转为int数组
	public static long[] changeTolong(String[] str) {
		long[] slong = new long[str.length];
		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i] != "") {
				slong[i] = Long.parseLong((str[i]));
			}
		}
		return slong;
	}

	// 将string数组转为float数组
	public static float[] changeTofloat(String[] str) {
		float[] sfloat = new float[str.length];
		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i] != "") {
				sfloat[i] = Float.parseFloat(str[i]);
			}
		}
		return sfloat;
	}

	public static int indexOf(String str, String[] strs) {
		int index = -1;
		for (int i = 0; i < strs.length; i++) {
			if (str.equals(strs[i])) {
				index = i;
				break;
			}
		}
		return index;
	}
	
}
