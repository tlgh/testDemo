package pers.ksy.common.spring4;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * spring工具类
 *
 * <p>
 * 提供spring bean factory
 * 
 * @author 孔思宇 2015年4月3日
 * @see
 * @since 1.1
 */
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> cla) {
        return applicationContext.getBean(cla);
    }

    public static <T> T getBean(String beanId, Class<T> cla) {
        return applicationContext.getBean(beanId, cla);
    }

    public static Object getBean(String beanId, Object... args) {
        return applicationContext.getBean(beanId, args);
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

}
