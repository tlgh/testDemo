package pers.ksy.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import pers.ksy.common.annotation.Prop;
import pers.ksy.common.annotation.PropKey;

public abstract class PropertiesSupport implements Serializable {
    private List<Field> fields;
    private Properties properties = new Properties();;
    private File file;

    public File path() {
        File file = null;
        if (getClass().isAnnotationPresent(Prop.class)) {
            Prop prop = this.getClass().getAnnotation(Prop.class);
            try {
                URI uri = getClass().getResource("/").toURI();
                File root = new File(uri);
                String fileName = prop.path();
                file = new File(root, fileName);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public synchronized void load() throws IOException {
        FileInputStream in = null;
        try {
            if (!getFile().exists()) {
                getFile().createNewFile();
            }
            properties.load(new FileInputStream(getFile()));
			for (Field field : getFields()) {
				PropKey propKey = field.getAnnotation(PropKey.class);
				setValue(field, propKey, getKey(field, propKey));
			}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                in.close();
            }
        }
    }

    private List<Field> buildFields() {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PropKey.class)) {
                field.setAccessible(true);
                list.add(field);
            }
        }
        return list;
    }

	private String getKey(Field field, PropKey propKey) {
		String key = propKey.name();
		if (StringUtil.isEmpty(key)) {
			key = field.getName();
		}
		return key;
	}

    private void setValue(Field field, PropKey propKey, String key) {
        try {
            String value = properties.getProperty(key);
            Object o = convertValue(field.getType(),propKey, value);
            field.set(this, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object convertValue(Class<?> type, PropKey propKey, String value)
            throws UnSupportedTypeException, InstantiationException,
            IllegalAccessException {
        Object o = null;
        if (StringUtil.notEmpty(value)) {
            if (Integer.class == type) {
                o = Integer.valueOf(value);
            } else if (Float.class == type) {
                o = Float.valueOf(value);
            } else if (Double.class == type) {
                o = Double.valueOf(value);
            } else if (Boolean.class == type) {
                o = Boolean.valueOf(value);
            } else if (Long.class == type) {
                o = Long.valueOf(value);
            } else if (Short.class == type) {
                o = Short.valueOf(value);
            } else if (Date.class == type) {
                o = TimeUtil.parseDate(value);
                if (null == o) {
                    o = TimeUtil.parseDateTime(value);
                }
            } else if (BigDecimal.class == type) {
                o = new BigDecimal(value);
            } else if (String.class == type) {
                o = value;
            } else if (Object[].class.isAssignableFrom(type)) {
				o = convertArrayValue(type.getComponentType(), propKey, value);
            }else if (Collection.class.isAssignableFrom(type)) {
				Object[] array = (Object[]) convertArrayValue(propKey.componentType(), propKey, value);
				Collection collection = (Collection) type.newInstance();
				Collections.addAll(collection, array);
				o = collection;
            } else {
                throw new UnSupportedTypeException(type);
            }
        }
        return o;
    }
    
	private Object convertArrayValue(Class<?> type, PropKey propKey, String value)
			throws InstantiationException, IllegalAccessException,
			UnSupportedTypeException {
		String[] strs = value.split(",");
		Object[] array = (Object[]) Array.newInstance(type, strs.length);
		for (int i = 0; i < strs.length; i++) {
			Object aValue = convertValue(type, propKey, strs[i]);
			Array.set(array, i, aValue);
		}
		return array;
	}

	private String unConvertValue(Class<?> type, PropKey propKey, Object o) {
		String value = "";
		if (null != o) {
			if (Date.class.isAssignableFrom(type)) {
				String pattern = TimeUtil.DATA_PATTERN;
				if (null != propKey.pattern()) {
					pattern = propKey.pattern();
				}
				value = TimeUtil.formatDate((Date) o, pattern);
			} else if (Object[].class.isAssignableFrom(type)) {
				value = unConvertValue(type, propKey, (Object[]) o);
			} else if (Collection.class.isAssignableFrom(type)) {
				Collection collection = (Collection) o;
				value = unConvertValue(type, propKey, collection.toArray());
			} else {
				value = o.toString();
			}
		}
		return value;
	}
    
	private String unConvertValue(Class<?> type, PropKey propKey, Object[] array) {
		String value = "";
		if (array.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (Object obj : array) {
				sb.append(unConvertValue(obj.getClass(), propKey, obj))
						.append(",");
			}
			value = sb.substring(0, sb.length() - 1);
		}
		return value;
	}

	public synchronized void store() throws IOException {
		for (Field field : getFields()) {
			PropKey propKey = field.getAnnotation(PropKey.class);
			try {
				Object o = field.get(this);
				String value = unConvertValue(field.getType(), propKey, o);
				properties.setProperty(getKey(field, propKey), value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(getFile());
			properties.store(out, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.close();
			}
		}

	}

    public File getFile() {
        if (null == file) {
            file = path();
        }
        return file;
    }

    /**
     * @return the fields
     */
    public List<Field> getFields() {
        if (null == fields) {
            fields = buildFields();
        }
        return fields;
    }

    public static class UnSupportedTypeException extends Exception {
        public UnSupportedTypeException(Class<?> type) {
            super(" not support " + type + " type Filed");
        }
    }
}
