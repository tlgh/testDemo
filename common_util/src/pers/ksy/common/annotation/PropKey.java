package pers.ksy.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pers.ksy.common.TimeUtil;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropKey {
	String name() default "";

	String pattern() default TimeUtil.DATA_PATTERN;
	
	Class<?> componentType() default Object.class;
}
