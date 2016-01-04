/**
 * 
 */
package pers.ksy.common.spring4;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import pers.ksy.common.annotation.SerializationFilter;
import pers.ksy.common.annotation.SerializationFilters;
import pers.ksy.common.serialization.Filter;
import pers.ksy.common.serialization.json.BaseJsonSerializer;
import pers.ksy.common.serialization.xml.BaseXmlSerializer;

/**
 * 控制器数据序列化
 *
 * @author ksy 2015年4月3日
 * @see
 * @since 1.1
 */
public class ControllerSerializeSupport {
    /**
     * 时间格式
     */
    private String datePattern;
    
    /**
     * json 序列化器
     */
    private Class<? super BaseJsonSerializer> jsonSerializerClass;
    
    /**
     * xml 序列化器
     */
    private Class<? super BaseXmlSerializer> xmlSerializerClass;
    
    /**
     * json 序列化器(实例)
     */
    private BaseJsonSerializer jsonSerializer;
    
    /**
     * xml 序列化器(实例)
     */
    private BaseXmlSerializer xmlSerializer;
    
    public Object doAround(Object obj, Method method) {
        SerializationFilters fs = method.getAnnotation(
                SerializationFilters.class);
        SerializationFilter f = method.getAnnotation(
                SerializationFilter.class);
        Filter[] filters = null;
        if (null != fs) {
            filters = new Filter[fs.filters().length];
            for (int i = 0; i < fs.filters().length; i++) {
                filters[i] = Filter.create(fs.filters()[i]);
            }
        } else if (null != f) {
            filters = new Filter[] { Filter.create(f) };
        } else {
            // 没有json注解则直接返回
            return obj;
        }
        String json = getJsonSerializer().serialize(obj, filters);
        obj = json;
        return obj;
    }
    
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object obj = pjp.proceed();
        MethodSignature msig = (MethodSignature) pjp.getSignature();
        SerializationFilters fs = msig.getMethod().getAnnotation(
                SerializationFilters.class);
        SerializationFilter f = msig.getMethod().getAnnotation(
                SerializationFilter.class);
        Filter[] filters = null;
        if (null != fs) {
            filters = new Filter[fs.filters().length];
            for (int i = 0; i < fs.filters().length; i++) {
                filters[i] = Filter.create(fs.filters()[i]);
            }
        } else if (null != f) {
            filters = new Filter[] { Filter.create(f) };
        } else {
            // 没有json注解则直接返回
            return obj;
        }
        String json = getJsonSerializer().serialize(obj, filters);
        obj = json;
        /*ObjectMapper mapper = new ObjectMapper();
        if (null != datePattern) {
            mapper.setDateFormat(new SimpleDateFormat(datePattern));
        }
        for (int i = 0; i < filter.target().length; i++) {
            mapper.addMixInAnnotations(filter.target()[i],
                    filter.mixin()[i]);
        }
        String json = mapper.writeValueAsString(obj);
        obj = json;*/
        return obj;
    }
    
    

    /**
     * @return the jsonSerializer
     */
    public BaseJsonSerializer getJsonSerializer() {
        if (null == jsonSerializer) {
            try {
                jsonSerializer = (BaseJsonSerializer) jsonSerializerClass.newInstance();
                jsonSerializer.setDatePattern(datePattern);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonSerializer;
    }


    /**
     * @return the xmlSerializer
     */
    public BaseXmlSerializer getXmlSerializer() {
        if (null == xmlSerializer) {
            try {
                xmlSerializer = (BaseXmlSerializer) xmlSerializerClass.newInstance();
                xmlSerializer.setDatePattern(datePattern);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return xmlSerializer;
    }


    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }



    /**
     * @param jsonSerializerClass the jsonSerializerClass to set
     */
    public void setJsonSerializerClass(
            Class<? super BaseJsonSerializer> jsonSerializerClass) {
        this.jsonSerializerClass = jsonSerializerClass;
    }



    /**
     * @param xmlSerializerClass the xmlSerializerClass to set
     */
    public void setXmlSerializerClass(
            Class<? super BaseXmlSerializer> xmlSerializerClass) {
        this.xmlSerializerClass = xmlSerializerClass;
    }
    
}
