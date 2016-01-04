package pers.ksy.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * simple introduction
 *
 * <p>detailed comment
 * @author ksy 2015年8月21日
 * @see
 * @since 1.3.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Prop {
	String path();
}
