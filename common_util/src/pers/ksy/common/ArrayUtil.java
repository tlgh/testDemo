package pers.ksy.common;

import java.lang.reflect.Array;

public class ArrayUtil {

	private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
		if (array != null) {
			final int arrayLength = Array.getLength(array);
			final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
			System.arraycopy(array, 0, newArray, 0, arrayLength);
			return newArray;
		}
		return Array.newInstance(newArrayComponentType, 1);
	}

	/**
	 * The array may contain nulls, but <TT>o</TT> must be non-null.
	 */
	public static int indexOf(Object[] array, Object o) {
		for (int i = 0, len = array.length; i < len; ++i)
			if (o.equals(array[i]))
				return i;
		return -1;
	}

	public static <T> T[] add(final T[] array, final T element) {
		Class<?> type;
		if (array != null) {
			type = array.getClass().getComponentType();
		} else if (element != null) {
			type = element.getClass();
		} else {
			throw new IllegalArgumentException("Arguments cannot both be null");
		}
		@SuppressWarnings("unchecked") // type must be T
		final T[] newArray = (T[]) copyArrayGrow1(array, type);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	public static <T> T[] addAll(final T[] array1, final T... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final Class<?> type1 = array1.getClass().getComponentType();
		@SuppressWarnings("unchecked") // OK, because array is of type T
		final T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		try {
			System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		} catch (final ArrayStoreException ase) {
			// Check if problem was due to incompatible types
			/*
			 * We do this here, rather than before the copy because: - it would
			 * be a wasted check most of the time - safer, in case check turns
			 * out to be too strict
			 */
			final Class<?> type2 = array2.getClass().getComponentType();
			if (!type1.isAssignableFrom(type2)) {
				throw new IllegalArgumentException(
						"Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
			}
			throw ase; // No, so rethrow original
		}
		return joinedArray;
	}

	public static <T> T[] toArray(final T... items) {
		return items;
	}

	public static <T> T[] clone(final T[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static long[] clone(final long[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static int[] clone(final int[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static short[] clone(final short[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static char[] clone(final char[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static byte[] clone(final byte[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static double[] clone(final double[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static float[] clone(final float[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static boolean[] clone(final boolean[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
		if (array == null) {
			return null;
		}
		if (startIndexInclusive < 0) {
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length) {
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		final Class<?> type = array.getClass().getComponentType();
		if (newSize <= 0) {
			@SuppressWarnings("unchecked") // OK, because array is of type T
			final T[] emptyArray = (T[]) Array.newInstance(type, 0);
			return emptyArray;
		}
		@SuppressWarnings("unchecked") // OK, because array is of type T
		final T[] subarray = (T[]) Array.newInstance(type, newSize);
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	public static int identityIndexOf(Object[] array, Object o) {
		for (int i = 0, len = array.length; i < len; ++i)
			if (o == array[i])
				return i;
		return -1;
	}

	public static boolean startsWith(byte[] checkMe, byte[] maybePrefix) {
		int cm_len = checkMe.length;
		int mp_len = maybePrefix.length;
		if (cm_len < mp_len)
			return false;
		for (int i = 0; i < mp_len; ++i)
			if (checkMe[i] != maybePrefix[i])
				return false;
		return true;
	}

	/**
	 * @deprecated use the various toString(T[] methods)
	 */
	public static String stringifyContents(Object[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		for (int i = 0, len = array.length; i < len; ++i) {
			if (i != 0)
				sb.append(", ");
			sb.append(array[i].toString());
		}
		sb.append(" ]");
		return sb.toString();
	}

	// these methods are obsoleted by Arrays.toString() in jdk1.5, but
	// for libs that support older VMs...
	private static String toString(String[] strings, int guessed_len) {
		StringBuffer sb = new StringBuffer(guessed_len);
		boolean first = true;
		sb.append('[');
		for (int i = 0, len = strings.length; i < len; ++i) {
			if (first)
				first = false;
			else
				sb.append(',');
			sb.append(strings[i]);
		}
		sb.append(']');
		return sb.toString();
	}

	public static String toString(boolean[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(byte[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(char[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(short[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(int[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(long[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(float[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(double[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

	public static String toString(Object[] arr) {
		String[] strings = new String[arr.length];
		int chars = 0;
		for (int i = 0, len = arr.length; i < len; ++i) {
			String str;
			Object o = arr[i];
			if (o instanceof Object[])
				str = toString((Object[]) o);
			else if (o instanceof double[])
				str = toString((double[]) o);
			else if (o instanceof float[])
				str = toString((float[]) o);
			else if (o instanceof long[])
				str = toString((long[]) o);
			else if (o instanceof int[])
				str = toString((int[]) o);
			else if (o instanceof short[])
				str = toString((short[]) o);
			else if (o instanceof char[])
				str = toString((char[]) o);
			else if (o instanceof byte[])
				str = toString((byte[]) o);
			else if (o instanceof boolean[])
				str = toString((boolean[]) o);
			else
				str = String.valueOf(arr[i]);
			chars += str.length();
			strings[i] = str;
		}
		return toString(strings, chars + arr.length + 1);
	}

}
