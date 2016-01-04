/**
 * 
 */
package pers.ksy.common.serialization;


/**
 * 序列化规范
 * 
 * @author 孔思宇 2015年4月3日
 * @see
 * @since 1.1
 */
public interface Serializer {
   
    /**
     * 序列化
     * @param Obj 序列化对象
     * @return 序列化结果
     */
    String serialize(Object obj);
    
    
    /**
     * 
     * @param Obj 序列化对象
     * @param filters 过滤配置
     * @return
     */
    String serialize(Object obj, Filter[] filters);
}
