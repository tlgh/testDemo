/**
 * 
 */
package pers.ksy.common.serialization;

import pers.ksy.common.annotation.SerializationFilter;

/**
 * 过滤字段配置
 *
 * <p>
 * detailed comment
 * 
 * @author 孔思宇 2015年4月3日
 * @see
 * @since 1.1
 */
public class Filter {
    /**
     * @return 需要过滤的类型
     */
    private Class<?> target;

    /**
     * @return 需要过滤的字段
     */
    private String[] fields;

    /**
     * @return true:排除/false:包含
     */
    private boolean exclusive = true;

    /**
     * 从注解转换到实体类配置
     * @param filter
     * @return
     */
    public static Filter create(SerializationFilter filter) {
        return new Filter(filter.target(), filter.exclusive(), filter.fields());
    }

    /**
     * @param target
     * @param fields
     */
    public Filter(Class<?> target, String... fields) {
        super();
        this.target = target;
        this.fields = fields;
    }

    /**
     * @param target
     * @param fields
     * @param exclusive
     */
    public Filter(Class<?> target, boolean exclusive, String... fields) {
        super();
        this.target = target;
        this.fields = fields;
        this.exclusive = exclusive;
    }

    /**
     * @return the target
     */
    public Class<?> getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Class<?> target) {
        this.target = target;
    }

    /**
     * @return the fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(String[] fields) {
        this.fields = fields;
    }

    /**
     * @return the exclusive
     */
    public boolean isExclusive() {
        return exclusive;
    }

    /**
     * @param exclusive the exclusive to set
     */
    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

}
