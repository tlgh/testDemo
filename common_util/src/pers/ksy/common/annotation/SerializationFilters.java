package pers.ksy.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
public @interface SerializationFilters {
    /**
     * @return 注解集合
     */
    SerializationFilter[] filters();
}