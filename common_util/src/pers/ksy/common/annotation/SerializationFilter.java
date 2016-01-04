package pers.ksy.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import pers.ksy.common.serialization.Filter;

/**
 * 
 * 序列化注解
 *
 * <p>
 * detailed comment
 * 
 * @author boil_000 2015年4月3日
 * @see
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializationFilter {
    /**
     * @return 需要过滤的类型
     */
    Class<?> target() default Object.class;

    /**
     * @return 需要过滤的字段
     */
    String[] fields() default {};

    /**
     * @return true:排除/false:包含 default true
     */
    boolean exclusive() default true;
}