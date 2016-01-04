package pers.ksy.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
	
	/**
	 * 调用对象属性的Getter方法
	 * 
	 * @param obj 对象
	 * @param attr 属性名
	 * @return
	 */
	public static Object doGetter(Object obj, String attr) {
		try {
			String[] attrs = attr.split("\\.");
			Class clz = obj.getClass();
			PropertyDescriptor pd = new PropertyDescriptor(attrs[0], clz);
			Object o = pd.getReadMethod().invoke(obj);
			for (int i = 1; i < attrs.length; i++) {
				o = doGetter(o, attrs[i]);
			}
			return o;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getFieldValue(Object obj, String attr)
			throws NoSuchFieldException, IllegalArgumentException {
		Field field = null;
		Class clz = obj.getClass();
		try {
			field = getField(clz, attr, FieldType.values());
			field.setAccessible(true);
			return field.get(obj);
		} catch (NoSuchFieldException e) {
			throw e;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Field getField(Class clz, String fieldName,
			FieldType... types) throws NoSuchFieldException {
		if (null == types || types.length == 0) {
			types = new FieldType[] { FieldType.PRIVATE,
					FieldType.SUPER_PRIVATE };
		}
		Field field = null;
		for (int i = 0; i < types.length; i++) {
			try {
				field = types[i].getFiled(clz, fieldName);
				break;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				if (i == types.length - 1) {
					throw e;
				}
			}
		}
		return field;
	}

	/**
	 * Map 转 Bean
	 * 
	 * @param t 对象
	 * @param map 数据Map
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 */
	public static <T> T fromMap(T t, Map map) throws SecurityException,
			IllegalArgumentException, IllegalAccessException,
			InstantiationException {
		for (String key : ((Map<String, String>) map).keySet()) {
			if (null != key && !"".equals(key)) {
				String[] keys = key.split("\\.", 2);
				Object val = map.get(key);
				try {
					Field field = getField(t.getClass(), keys[0]);
					field.setAccessible(true);
					if (keys.length > 1) {
						Map sMap = new HashMap(1);
						sMap.put(keys[1], val);
						Object obj = field.get(t);
						if (null == obj) {
							obj = field.getType().newInstance();
						}
						val = fromMap(obj, sMap);
					}
					field.set(t, val);
				} catch (NoSuchFieldException e) {
					//e.printStackTrace();
					System.out.println("BeanUtil-->NoSuchField:"+key);
				}
			}
		}
		return t;
	}

	/**
	 * Map 转 Bean (重载：class 必须有空构造)
	 * 
	 * @param clz Bean Class
	 * @param map 数据Map
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static <T> T fromMap(Class<T> clz, Map map)
			throws InstantiationException, IllegalAccessException,
			SecurityException, NoSuchFieldException {
		T t = clz.newInstance();
		return fromMap(t, map);
	}

	public enum FieldType {
		PUBLIC, PRIVATE, SUPER_PUBLIC, SUPER_PRIVATE;

		public Field getFiled(Class clz, String fieldName)
				throws SecurityException, NoSuchFieldException {
			Field field = null;
			switch (this) {
			case PUBLIC:
				field = clz.getField(fieldName);
				break;
			case PRIVATE:
				field = clz.getDeclaredField(fieldName);
				break;
			case SUPER_PUBLIC:
				field = clz.getSuperclass().getField(fieldName);
				break;
			case SUPER_PRIVATE:
				field = clz.getSuperclass().getDeclaredField(fieldName);
				break;
			}
			return field;
		}
	}

	
}